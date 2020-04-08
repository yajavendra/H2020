package com.kavach.otp.service;

import com.kavach.otp.model.OTP;
import com.sendgrid.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class EmailService {

    //TODO: throw this to YML
    public static final String KAVACH_MAIL_ID = "yajavendra.patel@gmail.com";
    public static final String SUBJECT = "Kavach";
    public static final String SENDGRID_APIKEY = "SENDGRID_APIKEY";

    public void sendEmail(OTP otp) throws IOException {
        Content content = new Content("text/plain", "Your Kavach OTP is :" + otp.getOtp() + " It would be valid till " + OTP.TIME_TO_LIVE + " seconds");
        Mail mail = new Mail(new Email(KAVACH_MAIL_ID), //from
                             SUBJECT,
                             new Email(otp.getEmail()), //from
                             content);

        SendGrid sg = new SendGrid(SENDGRID_APIKEY);
        Request request = new Request();
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            Response response = sg.api(request);
            System.out.println(response.getStatusCode());
            System.out.println(response.getBody());
            System.out.println(response.getHeaders());

    }
}
