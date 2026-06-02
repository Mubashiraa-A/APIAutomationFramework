package org.example.base;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.example.asserts.AssertAction;
import org.example.endpoints.ApiConstansts;
import org.example.modules.PayloadManager;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseTest {
    //this is common to all test cases
    public RequestSpecification requestSpecification;
    public Response response;
    public ValidatableResponse validatableResponse;

    public AssertAction assertAction;
    public PayloadManager payloadManager;
    public JsonPath jsonPath;
    protected static final Logger logger = LogManager.getLogger(BaseTest.class);

    @BeforeTest
    public void setup() {
        logger.info("Start the test");
        assertAction = new AssertAction();
        payloadManager = new PayloadManager();

        //requestSpecification= RestAssured.given();
        //requestSpecification.baseUri(ApiConstansts.base_url);
        //requestSpecification.contentType(ContentType.JSON).log().all();
        //OR
        requestSpecification = new RequestSpecBuilder()
                .setBaseUri(ApiConstansts.base_url)
                .addHeader("Content-Type", "application/json")
                .build().log().all();
    }

    @AfterTest
    public void teardown() {
        logger.info("Finished the test");
    }

    public String getToken() {
        requestSpecification = RestAssured.given();
        requestSpecification.baseUri(ApiConstansts.base_url).basePath(ApiConstansts.auth_url);
        requestSpecification.contentType(ContentType.JSON);
        requestSpecification.body(payloadManager.setTokenPayload());

        response = requestSpecification.when().log().all().post();
        validatableResponse = response.then().log().all().statusCode(200);

        String token = payloadManager.getTokenResponse(response.asString()).getToken();
        return token;
    }
}
