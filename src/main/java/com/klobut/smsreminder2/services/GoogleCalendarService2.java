package com.klobut.smsreminder2.services;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.DateTime;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.calendar.Calendar;
import com.google.api.services.calendar.CalendarScopes;
import com.google.api.services.calendar.model.Events;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Collections;

@Service
public class GoogleCalendarService2 {

    private static final String APPLICATION_NAME = "sms-reminder";
    private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
    private static final String CREDENTIALS_FILE_PATH = "src/main/resources/credentials.json"; // Ścieżka do pliku JSON z poświadczeniami
    private static final String TOKENS_DIRECTORY_PATH = "tokens"; // Ścieżka do folderu z tokenami

    /**
     * Tworzy i zwraca usługę Google Calendar.
     */
    public Calendar getCalendarService() throws GeneralSecurityException, IOException {
        Credential credential = authorize();
        return new Calendar.Builder(GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, credential)
                .setApplicationName(APPLICATION_NAME)
                .build();
    }

    /**
     * Autoryzuje użytkownika do korzystania z Google Calendar API.
     */
    private Credential authorize() throws IOException, GeneralSecurityException {
        // Załaduj poświadczenia OAuth2 z pliku JSON
        File credentialsFile = new File(CREDENTIALS_FILE_PATH);
        if (!credentialsFile.exists()) {
            throw new IllegalArgumentException("Credentials file not found at " + CREDENTIALS_FILE_PATH);
        }
        GoogleClientSecrets clientSecrets = null;
        try (FileReader reader = new FileReader(CREDENTIALS_FILE_PATH)) {
            clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, reader);
        } catch (IOException e) {
            e.printStackTrace();
        }//        GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new FileReader(CREDENTIALS_FILE_PATH));

        // Budowa obiektu GoogleAuthorizationCodeFlow
        GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(
                GoogleNetHttpTransport.newTrustedTransport(), JSON_FACTORY, clientSecrets,
                Collections.singleton(CalendarScopes.CALENDAR)) // Zakres dostępu
                .setDataStoreFactory(new FileDataStoreFactory(new File(TOKENS_DIRECTORY_PATH))) // Folder przechowywania tokenów
                .build();

        // Sprawdzenie, czy użytkownik ma już zapisany token (jeśli nie, będzie musiał go autoryzować)
        return flow.loadCredential("user");
    }

    /**
     * Pobiera wydarzenia na następny dzień z kalendarza użytkownika.
     */
    public Events getNextDayEvents() throws GeneralSecurityException, IOException {
        Calendar service = getCalendarService();
        DateTime now = new DateTime(System.currentTimeMillis());
        DateTime tomorrow = new DateTime(System.currentTimeMillis() + 86400000); // Jutrzejsza data

        // Pobranie wydarzeń z Google Calendar
        Events events = service.events().list("ewelinakobut@gmail.com") // "primary" to domyślny kalendarz
                .setTimeMin(now)
                .setTimeMax(tomorrow)
                .setOrderBy("startTime")
                .setSingleEvents(true)
                .execute();

        return events;
    }
}
