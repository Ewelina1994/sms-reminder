package com.klobut.smsreminder2.services;

import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.model.Events;
import com.google.auth.http.HttpCredentialsAdapter;
import com.google.auth.oauth2.GoogleCredentials;
import com.google.auth.oauth2.ServiceAccountCredentials;
import com.klobut.smsreminder2.dto.Appointment;
import org.springframework.stereotype.Service;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

@Service
public class GoogleCalendarService3 {
    private static final String APPLICATION_NAME = "sms-reminder";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String SERVICE_ACCOUNT_FILE = "src/main/resources/credential-key.json"; // 🔹 Zmień na właściwą ścieżkę

    public Calendar getCalendarService() throws IOException, GeneralSecurityException {
        GoogleCredentials credentials = ServiceAccountCredentials
                .fromStream(new FileInputStream(SERVICE_ACCOUNT_FILE))
                .createScoped(Collections.singleton("https://www.googleapis.com/auth/calendar.readonly"));

        return new Calendar.Builder(
                GoogleNetHttpTransport.newTrustedTransport(),
                JSON_FACTORY,
                new HttpCredentialsAdapter(credentials))
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    public List<Appointment> getEvents() throws GeneralSecurityException, IOException {
        Calendar service = getCalendarService();
        LocalDateTime tomorrowAtOneAM = LocalDate.now().plusDays(1).atTime(1, 0);
        LocalDateTime tomorrowAtEnd = LocalDate.now().plusDays(1).atTime(23, 59);
        ZonedDateTime zonedDateTimeStart = tomorrowAtOneAM.atZone(ZoneId.systemDefault());
        ZonedDateTime zonedDateTimeEnd = tomorrowAtEnd.atZone(ZoneId.systemDefault());

        // Konwersja do Google API DateTime
        DateTime googleDateTimeStart = new DateTime(zonedDateTimeStart.toInstant().toEpochMilli());
        DateTime googleDateTimeEnd = new DateTime(zonedDateTimeEnd.toInstant().toEpochMilli());
        try {
            Events events = service.events()
                    .list("ewelinakobut@gmail.com")  // 🔹 Możesz tu podać ID konkretnego kalendarza
                    .setTimeMin(googleDateTimeStart) // Teraz
                    .setTimeMax(googleDateTimeEnd) // Jutro
                    .setOrderBy("startTime")
                    .setSingleEvents(true)
                    .execute();
            return events.getItems().stream().map(event ->
                            new Appointment(event.getSummary(), formatDate(event.getStart().getDateTime()), extractPhoneNumber(event.getDescription())))
                    .collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException("Błąd pobierania wizyt", e);
        }
    }

    private String formatDate(DateTime dateTime) {
        if (dateTime == null) return null;

        // Konwersja DateTime na LocalDateTime
        LocalDateTime localDateTime = LocalDateTime.ofInstant(
                Instant.ofEpochMilli(dateTime.getValue()),
                ZoneId.systemDefault() // Możesz zmienić na ZoneId.of("Europe/Warsaw")
        );
        // Formatowanie: "dd-MM-yyyy HH:mm"
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
        return localDateTime.format(formatter);
    }


    private String extractPhoneNumber(String text) {
        if (text == null) return null;

        Pattern pattern = Pattern.compile("\\+?\\d{1,3}[-\\s]?\\d{2,3}[-\\s]?\\d{2,3}[-\\s]?\\d{2,3}");
        Matcher matcher = pattern.matcher(text);
        if (matcher.find()) {
            String phoneNumber = matcher.group().replaceAll("[-\\s]", ""); // Usuń spacje i myślniki

            // Jeśli numer nie zaczyna się od "+", dodaj "+48"
            if (!phoneNumber.startsWith("+")) {
                phoneNumber = "+48" + phoneNumber;
            }
            return phoneNumber;
        }
        return null;
    }
}

