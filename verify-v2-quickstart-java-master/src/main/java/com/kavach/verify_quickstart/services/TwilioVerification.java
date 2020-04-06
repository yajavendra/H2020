package com.kavach.verify_quickstart.services;

import com.twilio.Twilio;
import com.twilio.exception.ApiException;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;

public class TwilioVerification implements VerificationService {

    private static final String ACCOUNT_SID = "ACc523d8121f226a6901e1f01ee9d55a78"; //System.getenv("TWILIO_ACCOUNT_SID");
    private static final String AUTH_TOKEN = "f1f0925e42c827177969e66f8c58f26b"; //System.getenv("TWILIO_AUTH_TOKEN");
    private static final String VERIFICATION_SID = "VAa8715b98e6a8abe83ead7f82c9efac60"; //System.getenv("VERIFICATION_SID");

    public TwilioVerification() {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public VerificationResult startVerification(String phone, String channel) {
        try {
            Verification verification = Verification.creator(VERIFICATION_SID, phone, channel).create();
            return new VerificationResult(verification.getSid());
        } catch (ApiException exception) {
            return new VerificationResult(new String[] {exception.getMessage()});
        }
    }

    public VerificationResult checkVerification(String phone, String code) {
        try {
            VerificationCheck verification = VerificationCheck.creator(VERIFICATION_SID, code).setTo(phone).create();
            if("approved".equals(verification.getStatus())) {
                return new VerificationResult(verification.getSid());
            }
            return new VerificationResult(new String[]{"Invalid code."});
        } catch (ApiException exception) {
            return new VerificationResult(new String[]{exception.getMessage()});
        }
    }
}
