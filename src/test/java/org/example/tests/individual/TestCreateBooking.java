package org.example.tests.individual;

import io.qameta.allure.Owner;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import jdk.jfr.Description;
import org.example.base.BaseTest;
import org.example.endpoints.ApiConstansts;
import org.example.pojo.restfulbooker.RequestPOJO.Bookingdates;
import org.example.pojo.restfulbooker.ResponsePOJO.BookingResponse;
import org.testng.annotations.Test;

import javax.naming.spi.ResolveResult;

public class TestCreateBooking extends BaseTest {
    @Test(groups = "reg", priority = 1)
    @Owner("Mubashira")
    @Description("TC#1- Verify the booking can be created")
    public void testCreateBookingPost_Positive() {
        // Part 1 - make the request
        requestSpecification.basePath(ApiConstansts.create_update_booking_url);
        response = RestAssured.given(requestSpecification)
                .body(payloadManager.createPayloadBookingAsString_Serialization())
                .when().log().all().post();
        // Part 2 - Extraction or Deserialization
        BookingResponse bookingResponse = payloadManager.bookingResponseAsJava_Deserialization(response.asString());

        validatableResponse = response.then().log().all().statusCode(200);

        //Part 3 - validation and verification via TestNG, AssertJ
        assertAction.verifyIntegerKeyNotnull(bookingResponse.getBookingid());
        assertAction.verifyStringKey(bookingResponse.getBooking().getFirstname(), "Afhaam");
    }


    @Test(groups = "reg", priority = 2)
    @Owner("Mubashira")
    @Description("TC#2- Verify the booking can't be created , when payload is null")
    public void testCreateBookingPost_Negative() {
        requestSpecification.basePath(ApiConstansts.create_update_booking_url);
        response = RestAssured.given(requestSpecification).when().body("[]")
                .log().all().post();
        validatableResponse = response.then().log().all();
        validatableResponse.statusCode(500);

    }


    @Test(groups = "reg", priority = 3)
    @Owner("Mubashira")
    @Description("TC#3- Verify the booking can't be created ,")
    public void testCreateBookingPost_Positive_Chinese() {
        requestSpecification.basePath(ApiConstansts.create_update_booking_url);
        response = RestAssured.given(requestSpecification).when()
                .body(payloadManager.createPayloadBookingAsStringWrongBody())
                .log().all().post();
        validatableResponse = response.then().log().all().statusCode(200);

        BookingResponse bookingResponse = payloadManager.bookingResponseAsJava_Deserialization(response.asString());
        assertAction.verifyIntegerKeyNotnull(bookingResponse.getBookingid());
        assertAction.verifyStringKeyNotNull(bookingResponse.getBooking().getFirstname());
    }


    @Test(groups = "reg", priority = 1)
    @Owner("Mubashira")
    @Description("TC#4- Verify the booking can be created, using faker")
    public void testCreateBookingPost_Positive_Faker() {
        requestSpecification.basePath(ApiConstansts.create_update_booking_url);
        response = RestAssured.given(requestSpecification).when()
                .body(payloadManager.createPayloadBookingFakerJS())
                .log().all().post();

        BookingResponse bookingResponse = payloadManager.bookingResponseAsJava_Deserialization(response.asString());

        validatableResponse = response.then().log().all().statusCode(200);

        assertAction.verifyStringKeyNotNull(bookingResponse.getBooking().getFirstname());
        assertAction.verifyIntegerKeyNotnull(bookingResponse.getBooking().getTotalprice());
    }


    @Test(groups = "reg",priority = 1)
    @Owner("Mubashira")
    @Description("TC#5- Verify the booking can be created, Base URL is wrong")
    public void testCreateBookingPost_Negative_Wrong_Base_Url(){
       requestSpecification = RestAssured.given();
       requestSpecification.baseUri(ApiConstansts.APP_VWO_URL);
       requestSpecification.basePath(ApiConstansts.create_update_booking_url);
       requestSpecification.contentType(ContentType.HTML);
       requestSpecification.body(payloadManager.createPayloadBookingAsString_Serialization());

       response= requestSpecification.given().log().all().post();

       validatableResponse= response.then().log().all();
       validatableResponse.statusCode(404);


    }
}
