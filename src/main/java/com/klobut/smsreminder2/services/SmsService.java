package com.klobut.smsreminder2.services;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SmsService {

    @Value("${twilio.account_sid}")
    private String accountSid;

    @Value("${twilio.auth_token}")
    private String authToken;

    @Value("${twilio.phone_number}")
    private String fromNumber;

    public void sendSms(String toPhoneNumber, String messageText) {
        Twilio.init(accountSid, authToken);
        if (toPhoneNumber != null) {
            Message.creator(new PhoneNumber(toPhoneNumber), new PhoneNumber(fromNumber), messageText).create();
        }
    }
}

