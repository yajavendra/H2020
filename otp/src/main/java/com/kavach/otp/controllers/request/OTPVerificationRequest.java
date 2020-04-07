package com.kavach.otp.controllers.request;

import javax.validation.constraints.NotNull;

public class OTPVerificationRequest {
    @NotNull
    private Integer token;
    @NotNull
    private String otp;

    public OTPVerificationRequest() {
    }

    public OTPVerificationRequest(Integer token, String otp) {
        this.token = token;
        this.otp = otp;
    }

    public Integer getToken() {
        return token;
    }

    public void setToken(Integer token) {
        this.token = token;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }
}
