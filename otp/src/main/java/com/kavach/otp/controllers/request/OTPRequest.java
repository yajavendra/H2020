package com.kavach.otp.controllers.request;

import javax.validation.constraints.NotNull;

public class OTPRequest {

    @NotNull
    private String mobileNumber;
    @NotNull
    private String email;

    public OTPRequest() {
    }

    public OTPRequest(String mobileNumber, String email) {
        this.mobileNumber = mobileNumber;
        this.email = email;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
