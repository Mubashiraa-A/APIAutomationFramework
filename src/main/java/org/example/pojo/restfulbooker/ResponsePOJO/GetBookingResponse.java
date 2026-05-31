package org.example.pojo.restfulbooker.ResponsePOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetBookingResponse {

    @SerializedName("firstname")
    @Expose
    private String firstname;

    @SerializedName("lastname")
    @Expose
    private String lastname;

    @SerializedName("totalprice")
    @Expose
    private int totalprice;

    @SerializedName("depositpaid")
    @Expose
    private boolean depositpaid;

    @SerializedName("bookingdates")
    @Expose
    private Bookingdates bookingdates;

    @SerializedName("additionalneeds")
    @Expose
    private String additionalneeds;

    // Getters and Setters

    public String getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public int getTotalprice() {
        return totalprice;
    }

    public void setTotalprice(int totalprice) {
        this.totalprice = totalprice;
    }

    public boolean isDepositpaid() {
        return depositpaid;
    }

    public void setDepositpaid(boolean depositpaid) {
        this.depositpaid = depositpaid;
    }

    public Bookingdates getBookingdates() {
        return bookingdates;
    }

    public void setBookingdates(Bookingdates bookingdates) {
        this.bookingdates = bookingdates;
    }

    public String getAdditionalneeds() {
        return additionalneeds;
    }

    public void setAdditionalneeds(String additionalneeds) {
        this.additionalneeds = additionalneeds;
    }


    // Inner Class

    public static class Bookingdates {

        @SerializedName("checkin")
        @Expose
        private String checkin;

        @SerializedName("checkout")
        @Expose
        private String checkout;

        public String getCheckin() {
            return checkin;
        }

        public void setCheckin(String checkin) {
            this.checkin = checkin;
        }

        public String getCheckout() {
            return checkout;
        }

        public void setCheckout(String checkout) {
            this.checkout = checkout;
        }
    }
}
