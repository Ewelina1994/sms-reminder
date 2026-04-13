//package com.klobut.smsreminder2.services;
//
//import com.google.api.client.util.DateTime;
//import com.google.api.services.calendar.Calendar;
//import com.google.api.services.calendar.model.Events;
//import com.klobut.smsreminder2.dto.Appointment;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//import java.time.Instant;
//import java.time.LocalDateTime;
//import java.time.ZoneId;
//import java.util.Date;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class GoogleCalendarService {
//
//    private final Calendar calendar;
//
//    public GoogleCalendarService(Calendar calendar) {
//        this.calendar = calendar;
//    }
//
//    public List<Appointment> getAppointmentsForTomorrow() {
//        try {
//            LocalDateTime tomorrowStart = LocalDateTime.now()
//                    .plusDays(1)
//                    .withHour(0)
//                    .withMinute(0)
//                    .withSecond(0)
//                    .withNano(0);
//            LocalDateTime tomorrowEnd = LocalDateTime.now()
//                    .plusDays(1)
//                    .withHour(23)
//                    .withMinute(59)
//                    .withSecond(0)
//                    .withNano(0);
//
//            Instant instantStart = tomorrowStart.atZone(ZoneId.systemDefault()).toInstant();
//            Instant instantEnd = tomorrowEnd.atZone(ZoneId.systemDefault()).toInstant();
//            DateTime tomorrowDateTimeStart = new DateTime(Date.from(instantStart));
//            DateTime tomorrowDateTimeEnd = new DateTime(Date.from(instantEnd));
//            Events events = calendar.events().list("primary")
//                    .setTimeMin(tomorrowDateTimeStart)
//                    .setTimeMax(tomorrowDateTimeEnd)
//                    .execute();
//
//            return events.getItems().stream().map(event ->
//                            new Appointment(event.getSummary(), event.getStart().getDateTime(), "+48123456789"))
//                    .collect(Collectors.toList());
//
//        } catch (IOException e) {
//            throw new RuntimeException("Błąd pobierania wizyt", e);
//        }
//    }
//}
//
