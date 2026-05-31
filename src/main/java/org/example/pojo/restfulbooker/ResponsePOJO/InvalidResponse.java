package org.example.pojo.restfulbooker.ResponsePOJO;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InvalidResponse {
        @SerializedName("reason")
        @Expose
        private String reason;


        public String getReason() {
            return reason;
        }

        public void setReason(String token) {
            this.reason = token;
        }

}
