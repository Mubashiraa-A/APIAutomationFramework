package org.example.tests.e2e_integration;

import io.qameta.allure.Description;
import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import org.example.base.BaseTest;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.endpoints.ApiConstansts;
import org.example.pojo.restfulbooker.ResponsePOJO.BookingResponse;
import org.example.pojo.restfulbooker.ResponsePOJO.GetBookingResponse;
import org.testng.ITestContext;
import org.testng.annotations.Test;

public class TestIntegrationFlow2 extends BaseTest {
    // Test E2E Scenario 2
    // 1. Create a Booking -> bookingID
    // 2. Verify the Create Booking - GET Request to bookingID
    // 3. Make Partial Update ( bookingID, Token) - Update only lastname to "sarah"
    // 4. Verify the partial update is successful - GET Request to bookingID
    // 5. Delete the Booking - Need to get the token, bookingID from above request

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
    @Description("Test Case 2: Verify the created booking by ID")
    public void testVerifyBookingID(ITestContext iTestContext) {
        Integer bookingID = (Integer) iTestContext.getAttribute("bookingid");

        String basePathGet = ApiConstansts.create_update_booking_url + "/" + bookingID;
        Logger logger = LogManager.getLogger(TestIntegrationFlow2.class);
        logger.info("url >>>>>>>>>>> {}", basePathGet);
        requestSpecification.basePath(basePathGet);

        response = RestAssured.given(requestSpecification).when().log().all().get();
        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(200);

        GetBookingResponse booking = payloadManager.getBookingResponse(response.asString());
        assertAction.verifyStringKeyNotNull(booking.getLastname());
    }

    @Test(groups = "Reg", priority = 3)
    @Description("Test Case 3: Make a partial update to the booking (only lastname)")
    public void testPartialUpdateBookingByID(ITestContext iTestContext) {
        Integer bookingID = (Integer) iTestContext.getAttribute("bookingid");
        String token = getToken();
        iTestContext.setAttribute("token", token);

        String basePathUpdate = ApiConstansts.create_update_booking_url + "/" + bookingID;
        requestSpecification.basePath(basePathUpdate);
        response = RestAssured.given(requestSpecification).when()
                .body(payloadManager.createPartialPayloadBookingLastName())
                .cookie("token", token).log().all().patch();
        validatableResponse = response.then().log().all().statusCode(200);

        GetBookingResponse updateResponse = payloadManager.getBookingResponse(response.asString());
        assertAction.verifyStringKeyNotNull(updateResponse.getLastname());
    }

    @Test(groups = "Reg", priority = 4)
    @Description("Test Case 4: Verify the partial update is successful")
    public void testVerifyPartialUpdate(ITestContext iTestContext) {
        Integer bookingID = (Integer) iTestContext.getAttribute("bookingid");

        String basePathGet = ApiConstansts.create_update_booking_url + "/" + bookingID;
        requestSpecification.basePath(basePathGet);

        response = RestAssured.given(requestSpecification).when().log().all().get();
        validatableResponse = response.then().log().all().statusCode(200);

        GetBookingResponse booking = payloadManager.getBookingResponse(response.asString());
        assertAction.verifyStringKey("sarah", booking.getLastname());
    }

    @Test(groups = "Reg", priority = 5)
    @Description("Test Case 5: Delete the booking")
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
}
