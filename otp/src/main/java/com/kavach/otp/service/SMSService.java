package com.kavach.otp.service;

import com.kavach.otp.model.OTP;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.stereotype.Service;

@Service
public class SMSService {

    //TODO: throw this to YML
    public static final String ACCOUNT_SID = "ACc523d8121f226a6901e1f01ee9d55a78";
    public static final String AUTH_TOKEN = "f1f0925e42c827177969e66f8c58f26b";
    public static final String SENDER_PHONE_NUMBER = "+12156072185";

    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public void sendSms(OTP otp) {
        Message message = Message
                .creator(new PhoneNumber(otp.getMobileNumber()), // to
                        new PhoneNumber(SENDER_PHONE_NUMBER), // from
                        "Your Kavach OTP is :" + otp.getOtp() + " It would be valid till " + OTP.TIME_TO_LIVE + " seconds") // TODO: Make Generic YML :)
                .create();
    }
}
