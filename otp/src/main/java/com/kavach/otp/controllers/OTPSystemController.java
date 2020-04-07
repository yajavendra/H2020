package com.kavach.otp.controllers;

import com.kavach.otp.controllers.request.OTPRequest;
import com.kavach.otp.controllers.request.OTPVerificationRequest;
import com.kavach.otp.facade.OTPSenderFacade;
import com.kavach.otp.model.OTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@RestController
public class OTPSystemController {

    @Autowired
    OTPSenderFacade otpSender;

    private Map<Integer, OTP> OTPs = new HashMap<>();

    @RequestMapping(value = "/ping", method = RequestMethod.GET)
    public String ping() {
        return "pong";
    }

    @RequestMapping(value = "otp", method = RequestMethod.POST)
    public ResponseEntity<Object> sendOTP(@RequestBody OTPRequest otpRequest) {

        OTP otp = new OTP();
        otp.setMobileNumber(otpRequest.getMobileNumber());
        otp.setEmail(otpRequest.getEmail());
        otp.setOtp(generateOtp());
        otp.setExpiryTime(System.currentTimeMillis() + OTP.TIME_TO_LIVE);
        OTPs.put(otpRequest.getMobileNumber().hashCode(), otp);

        try {
            otpSender.sendMessage(otp);
        } catch (IOException e) {
            e.printStackTrace();
            //TODO: Proper Ex. handling
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<>(otpRequest.getMobileNumber().hashCode(), HttpStatus.OK);
    }

    @RequestMapping(value = "otp/verify", method = RequestMethod.POST)
    public ResponseEntity<Object> verifyOTP(@RequestBody OTPVerificationRequest otpVerificationRequest) {

        if (!isValidToken(otpVerificationRequest.getToken())) {
            return new ResponseEntity<>("Token is not valid ", HttpStatus.BAD_REQUEST);
        }

        OTP otp = OTPs.get(otpVerificationRequest.getToken());

        if (isExpiredOTP(otp)) {
            OTPs.remove(otpVerificationRequest.getToken());
            return new ResponseEntity<>("OTP has been expired! ", HttpStatus.NOT_FOUND);
        }

        if (otpVerificationRequest.getOtp().equals(otp.getOtp())) {
            OTPs.remove(otpVerificationRequest.getToken());
            return new ResponseEntity<>("OTP is successfully verified!", HttpStatus.OK);
        } else {
            return new ResponseEntity<>("Invalid OTP!! ", HttpStatus.BAD_REQUEST);
        }
    }

    private boolean isExpiredOTP(OTP otp) {
        return otp.getExpiryTime() < System.currentTimeMillis();
    }

    private boolean isValidToken(Integer token) {
        return OTPs.containsKey(token);
    }


    private String generateOtp() {
        return String.valueOf(((int) (Math.random() * (10000 - 1000))) + 1000);
    }
}
