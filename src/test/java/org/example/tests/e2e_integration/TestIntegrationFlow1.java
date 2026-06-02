package org.example.tests.e2e_integration;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.example.base.BaseTest;
import org.example.endpoints.ApiConstansts;
import org.example.pojo.restfulbooker.ResponsePOJO.BookingResponse;
import org.example.pojo.restfulbooker.ResponsePOJO.GetBookingResponse;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class TestIntegrationFlow1 extends BaseTest {
    // TestE2EFlow_01

    //  Test E2E Scenario 1

    //  1. Create a Booking -> bookingID
    // 2. Create Token -> token
    // 3. Verify that the Create Booking is working - GET Request to bookingID
    // 4. Update the booking ( bookingID, Token) - Need to get the token, bookingID from above request
    // 5. Delete the Booking - Need to get the token, bookingID from above request


    @Test(groups = "Reg", priority = 1)
    @Owner("Mubashira")
    @Description("")
    public void testCreateBooking(ITestContext iTestContext) {
        requestSpecification.basePath(ApiConstansts.create_update_booking_url);
        response = RestAssured.given(requestSpecification).when()
                .body(payloadManager.createPayloadBookingAsString_Serialization())
                .log().all().post();
        validatableResponse = response.then().log().all().statusCode(200);
        BookingResponse bookingResponse = payloadManager.bookingResponseAsJava_Deserialization(response.asString());
        assertAction.verifyIntegerKeyNotnull(bookingResponse.getBookingid());
        assertAction.verifyStringKeyNotNull(bookingResponse.getBooking().getFirstname());

        Integer bookingID = bookingResponse.getBookingid();
        iTestContext.setAttribute("bookingid", bookingID);
    }


    @Test(groups = "Reg", priority = 2)
    public void testVerifyBookingID(ITestContext iTestContext) {
        Integer bookingID = (Integer) iTestContext.getAttribute("bookingid");

        String basePathGet = ApiConstansts.create_update_booking_url + "/" + bookingID;
        requestSpecification.basePath(basePathGet);

        response = RestAssured.given(requestSpecification).when().log().all().get();
        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

        GetBookingResponse booking = payloadManager.getBookingResponse(response.asString());
        assertAction.verifyStringKeyNotNull(booking.getLastname());

    }


    @Test(groups = "Reg", priority = 3)
    public void testUpdateBookingByID(ITestContext iTestContext) {
        Integer bookingID = (Integer) iTestContext.getAttribute("bookingid");
        String token = getToken();
        iTestContext.setAttribute("token",token);

        String basePathUpdate = ApiConstansts.create_update_booking_url + "/" + bookingID;
        requestSpecification.basePath(basePathUpdate);
        response = RestAssured.given(requestSpecification).when()
                .body(payloadManager.createPayloadBookingAsString_Serialization())
                .cookie("token",token).log().all().put();
        validatableResponse =response.then().log().all().statusCode(200);

        GetBookingResponse updateResponse= payloadManager.getBookingResponse(response.asString());

        assertAction.verifyStringKeyNotNull(updateResponse.getFirstname());
    }


    @Test(groups = "Reg", priority = 4)
    public void testDeleteBookingByID(ITestContext iTestContext) {
        Integer bookingID=(Integer) iTestContext.getAttribute("bookingid");
        String token=getToken();
        iTestContext.setAttribute("token",token);

        String  basePathDeletee= ApiConstansts.create_update_booking_url+"/"+bookingID;
        requestSpecification.basePath(basePathDeletee);
        response=RestAssured.given(requestSpecification)
                .when().cookie("token",token)
                .log().all().delete();
        validatableResponse=response.then().log().all().statusCode(201);
    }

}
