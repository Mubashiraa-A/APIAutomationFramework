package org.example.modules;

import com.github.javafaker.Faker;
import com.google.gson.Gson;
import org.example.pojo.restfulbooker.RequestPOJO.Booking;
import org.example.pojo.restfulbooker.RequestPOJO.Bookingdates;
import org.example.pojo.restfulbooker.ResponsePOJO.BookingResponse;

public class PayloadManager {
    Gson gson;
    Faker faker;

    // 1st payload manager -
    public String createPayloadBookingAsString_Serialization() {
        Booking booking = new Booking();
        booking.setFirstname("Afhaam");
        booking.setLastname("mubaaa");
        booking.setTotalprice(2500);
        booking.setDepositpaid(true);

        Bookingdates bookingdates = new Bookingdates();
        bookingdates.setCheckin("13-12-2000");
        bookingdates.setCheckout("13-12-2000");

        booking.setBookingdates(bookingdates);
        booking.setAdditionalneeds("Brunch");

        System.out.println(booking);

        gson = new Gson();
        return gson.toJson(booking);
    }


    // 2nd payload manager - Wrong Body
    public String createPayloadBookingAsStringWrongBody() {
        Booking booking = new Booking();
        booking.setFirstname("会意; 會意");
        booking.setLastname("会意; 會意");
        booking.setTotalprice(112);
        booking.setDepositpaid(false);

        Bookingdates bookingdates = new Bookingdates();
        bookingdates.setCheckin("5025-02-01");
        bookingdates.setCheckout("5025-02-01");
        booking.setBookingdates(bookingdates);
        booking.setAdditionalneeds("会意; 會意");

        System.out.println(booking);

        // Java Object -> JSON
        gson = new Gson();
        return gson.toJson(booking);

    }

// 3th- payload manger - Faker
    public String createPayloadBookingFakerJS(){
        //  This option is you dynamically generate the first name,
        //  last name and other variables.
        faker = new Faker();
        Booking booking = new Booking();
        booking.setFirstname(faker.name().firstName());
        booking.setLastname(faker.name().lastName());
        booking.setTotalprice(faker.random().nextInt(1, 1000));
        booking.setDepositpaid(faker.random().nextBoolean());

        Bookingdates bookingdates = new Bookingdates();
        bookingdates.setCheckin("2024-02-01");
        bookingdates.setCheckout("2024-02-01");
        booking.setBookingdates(bookingdates);
        booking.setAdditionalneeds("Breakfast");

        System.out.println(booking);

        // Java Object -> JSON
        gson = new Gson();
        String jsonStringBooking = gson.toJson(booking);
        return jsonStringBooking;

        // method with the dynamic data we use,
        // we will fetch the data from excel file.
        // Apache POI
        // String the value, firstName, lastName, and everything, and then we will verify from the response.


    }


 // Json to java Deserialization
    public BookingResponse bookingResponseAsJava_Deserialization(String responseString) {
        gson = new Gson();
        return gson.fromJson(responseString, BookingResponse.class);
    }





}