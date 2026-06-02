package org.example.tests.e2e_integration;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.example.base.BaseTest;
import org.example.endpoints.ApiConstansts;
import org.example.pojo.restfulbooker.ResponsePOJO.BookingResponse;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class TestIntegrationFlow3 extends BaseTest {
    // Test E2E Scenario 3
    // 1. Create a Booking -> bookingID
    // 2. Delete the Booking immediately - Need to get the token, bookingID from above request
    // 3. Verify that the booking has been successfully deleted

    @Test(groups = "Reg", priority = 1)
    @Owner("Mubashira")
    @Description("Test Case 1: Create a booking and store the booking ID")
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
    @Description("Test Case 2: Delete the booking immediately")
    public void testDeleteBookingByID(ITestContext iTestContext) {
        Integer bookingID = (Integer) iTestContext.getAttribute("bookingid");
        String token = getToken();
        iTestContext.setAttribute("token", token);

        String basePathDelete = ApiConstansts.create_update_booking_url + "/" + bookingID;
        requestSpecification.basePath(basePathDelete);
        response = RestAssured.given(requestSpecification)
                .when().cookie("token", token)
                .log().all().delete();
        validatableResponse = response.then().log().all().statusCode(201);
    }

    @Test(groups = "Reg", priority = 3)
    @Description("Test Case 3: Verify that the booking has been successfully deleted")
    public void testVerifyBookingIsDeleted(ITestContext iTestContext) {
        Integer bookingID = (Integer) iTestContext.getAttribute("bookingid");

        String basePathGet = ApiConstansts.create_update_booking_url + "/" + bookingID;
        requestSpecification.basePath(basePathGet);

        response = RestAssured.given(requestSpecification).when().log().all().get();
        validatableResponse = response.then().log().all().statusCode(404);
    }
}
