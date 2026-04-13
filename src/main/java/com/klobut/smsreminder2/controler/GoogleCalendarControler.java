package com.klobut.smsreminder2.controler;

import com.klobut.smsreminder2.dto.Appointment;
import com.klobut.smsreminder2.services.GoogleCalendarService2;
import com.klobut.smsreminder2.services.GoogleCalendarService3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import com.google.api.services.calendar.model.Events;

import java.util.List;

@RestController
public class GoogleCalendarControler {

    private final GoogleCalendarService3 calendarService;

    public GoogleCalendarControler(GoogleCalendarService3 calendarService) {
        this.calendarService = calendarService;
    }


    @GetMapping("/events")
    public List<Appointment> getEventsForNextDay() throws Exception {
        return calendarService.getEvents();
    }
}

