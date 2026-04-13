//package com.klobut.smsreminder2.configuration;
//
//import com.google.api.client.auth.oauth2.Credential;
//import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
//import com.google.api.client.http.javanet.NetHttpTransport;
//import com.google.api.client.json.JsonFactory;
//import com.google.api.client.json.gson.GsonFactory;
//import com.google.api.services.calendar.Calendar;
//import com.google.api.services.calendar.CalendarScopes;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.io.FileInputStream;
//import java.io.IOException;
//import java.security.GeneralSecurityException;
//import java.util.Collections;
//
//@Configuration
//public class GoogleCalendarConfig {
//    private static final String APPLICATION_NAME = "SMS Reminder App";
//    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
//
//    @Bean
//    public Credential googleCredential() throws IOException {
//        return GoogleCredential.fromStream(new FileInputStream("src/main/resources/credentials.json"))
//                .createScoped(Collections.singleton(CalendarScopes.CALENDAR));
//    }
//
//    @Bean
//    public Calendar googleCalendar(Credential credential) throws GeneralSecurityException, IOException {
//        return new Calendar.Builder(
//                new NetHttpTransport(),
//                JSON_FACTORY,
//                credential
//        ).setApplicationName(APPLICATION_NAME).build();
//    }
//}
