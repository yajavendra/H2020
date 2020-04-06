package com.kavach.otp.controllers;

import com.kavach.otp.model.OTP;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class OTPSystemRestcontroller {
    public static final String ACCOUNT_SID = "ACc523d8121f226a6901e1f01ee9d55a78";
    public static final String AUTH_TOKEN = "f1f0925e42c827177969e66f8c58f26b";
    public static final String SENDER_PHONE_NUMBER = "+12156072185";

    static {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    private Map<String, OTP> OTPs = new HashMap<>();

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public String ping() {
        return "pong";
    }

    @RequestMapping(value = "mobileNumber/{mobileNumber}/otp", method = RequestMethod.GET)
    public ResponseEntity<Object> sendOTP(@PathVariable("mobileNumber") String mobileNumber) {

        OTP otp = new OTP();
        otp.setMobileNumber(mobileNumber);
        otp.setOtp(generateOtp());
        otp.setExpiryTime(System.currentTimeMillis() + 200000);
        OTPs.put(mobileNumber, otp);

        sendMessage(otp);

        return new ResponseEntity<>("OTP is sent successfully to: " + otp.getMobileNumber(), HttpStatus.OK);
    }

    @RequestMapping(value = "mobileNumber/{mobileNumber}/verify", method = RequestMethod.POST)
    public ResponseEntity<Object> verifyOTP(@PathVariable("mobileNumber") String mobileNumber, @RequestBody OTP reqOtp) {

        if (!isValidMobile(mobileNumber)) {
            return new ResponseEntity<>("Mobile Number is not valid ", HttpStatus.BAD_REQUEST);
        }

        OTP otp = OTPs.get(mobileNumber);

        if (isExpiredOTP(otp)) {
            OTPs.remove(mobileNumber);
            return new ResponseEntity<>("OTP has been expired! ", HttpStatus.NOT_FOUND);
        }

        if (reqOtp.getOtp().equals(otp.getOtp())) {
            OTPs.remove(mobileNumber);
            return new ResponseEntity<>("OTP is successfully verified!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid OTP!! ", HttpStatus.BAD_REQUEST);
        }
    }

    private boolean isExpiredOTP(OTP otp) {
        return otp.getExpiryTime() < System.currentTimeMillis();
    }

    private boolean isValidMobile(@PathVariable("mobileNumber") String mobileNumber) {
        return OTPs.containsKey(mobileNumber);
    }

    private void sendMessage(OTP otp) {
        Message message = Message
                .creator(new PhoneNumber(otp.getMobileNumber()), // to
                        new PhoneNumber(SENDER_PHONE_NUMBER), // from
                        "Your OTP is :" + otp.getOtp() + " Valid till 20 seconds")
                .create();
    }

    private String generateOtp() {
        return String.valueOf(((int) (Math.random() * (10000 - 1000))) + 1000);
    }


}
