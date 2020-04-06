package com.kavach.verify_quickstart.services;

// Install the Java helper library from twilio.com/docs/java/install

import com.twilio.Twilio;
import com.twilio.rest.verify.v2.service.VerificationCheck;

public class Example {
    // Find your Account Sid and Token at twilio.com/console
    // DANGER! This is insecure. See http://twil.io/secure
    public static final String ACCOUNT_SID = "ACc523d8121f226a6901e1f01ee9d55a78";
    public static final String AUTH_TOKEN = "f1f0925e42c827177969e66f8c58f26b";

    public static void main(String[] args) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
        VerificationCheck verificationCheck = VerificationCheck.creator(
                "SIDVAa8715b98e6a8abe83ead7f82c9efac60",
                "1234")
                .setTo("+15017122661").create();

        System.out.println(verificationCheck.getStatus());
    }
}
