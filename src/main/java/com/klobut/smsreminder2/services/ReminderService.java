package com.klobut.smsreminder2.services;

import com.klobut.smsreminder2.dto.Appointment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Service
public class ReminderService {

    private final GoogleCalendarService3 calendarService;
    private final SmsService smsService;

    public ReminderService(GoogleCalendarService3 calendarService, SmsService smsService) {
        this.calendarService = calendarService;
        this.smsService = smsService;
    }

    @Scheduled(cron = "0 0 13 2 * ?") // Codziennie o 11:00
    public void sendReminders() throws GeneralSecurityException, IOException {
        List<Appointment> appointments = calendarService.getEvents();
        for (Appointment appt : appointments) {
            String message = "Przypomnienie: Masz wizytę w Zabiegowej Strefie Agata Kłobut jutro o " + appt.getStartDate() + " jeśli nie możesz przybyć, daj mi znać :)";
            smsService.sendSms(appt.getPhone(), message);
        }
    }
}
