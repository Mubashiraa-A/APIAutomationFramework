package org.example.tests.individual;

import groovyjarjarantlr4.v4.codegen.model.SrcOp;
import io.restassured.RestAssured;
import org.example.base.BaseTest;
import org.example.endpoints.ApiConstansts;
import org.example.modules.PayloadManager;
import org.example.pojo.restfulbooker.ResponsePOJO.TokenResponse;
import org.testng.annotations.Test;

public class TestCreateToken extends BaseTest {

    @Test
    public void createToken_Positive() {
        requestSpecification.basePath(ApiConstansts.auth_url);
        response = RestAssured.given(requestSpecification).when()
                .body(payloadManager.setTokenPayload())
                .log().all().post();

        // Part 2 - Extraction or Deserialization
        TokenResponse tokenResponse = payloadManager.getTokenResponse(response.asString());
        validatableResponse = response.then().log().all().statusCode(200);

        //Part 3 - validation and verification via TestNG, AssertJ
        assertAction.verifyStringKeyNotNull(tokenResponse.getToken());
    }

    @Test
    public void invalidToken_Negative(){
        requestSpecification.basePath(ApiConstansts.auth_url);
        response=RestAssured.given(requestSpecification)
                .when().body("{}").log().all().post();
        String invalid_reason= payloadManager.getInvalidReason(response.asString());
        validatableResponse=response.then().log().all().statusCode(200);

        assertAction.verifyStringKey("Bad credentials",invalid_reason);
    }
}
