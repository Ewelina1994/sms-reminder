package com.klobut.smsreminder2;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class SmsReminder2Application {

    public static void main(String[] args) {
        SpringApplication.run(SmsReminder2Application.class, args);
    }

}
