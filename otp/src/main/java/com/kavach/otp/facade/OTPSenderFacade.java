package com.kavach.otp.facade;

import com.kavach.otp.model.OTP;
import com.kavach.otp.service.EmailService;
import com.kavach.otp.service.SMSService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class OTPSenderFacade {

    @Autowired
    private EmailService emailService;

    @Autowired
    private SMSService smsService;

    public void sendMessage(OTP otp) throws IOException {
        emailService.sendEmail(otp);
        smsService.sendSms(otp);
    }
}
