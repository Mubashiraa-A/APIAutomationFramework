package org.example.base;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import org.example.asserts.AssertAction;
import org.example.endpoints.ApiConstansts;
import org.example.modules.PayloadManager;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;

public class BaseTest {
    //this is common to all test cases
    public RequestSpecification requestSpecification;
    public Response response;
    public ValidatableResponse validatableResponse;

    public AssertAction assertAction;
    public PayloadManager payloadManager;
    public JsonPath jsonPath;

    @BeforeTest
    public void setup() {
        System.out.println("Start the test");
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
        System.out.println("Finished the test");
    }

    public String getToken() {
        return null;
    }
}
