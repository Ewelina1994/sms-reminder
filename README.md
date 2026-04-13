# SMS Reminder Application

Aplikacja do wysyłania przypomnień SMS na podstawie wydarzeń z Google Calendar.

## Technologie
- Java
- Spring Boot
- Google Calendar API
- Twilio SMS API

## Konfiguracja

### 1. Google Calendar API
1. Umieść pliki `credentials.json` i `credential-key.json` w folderze `src/main/resources/`
2. Te pliki są ignorowane przez git i nie będą wysłane do repozytorium

### 2. Twilio API
1. Skopiuj plik `src/main/resources/application.properties.example` do `src/main/resources/application.properties`
2. Uzupełnij swoje dane Twilio:
   ```properties
   spring.application.name=sms-reminder-2
   twilio.account_sid=TWÓJ_ACCOUNT_SID
   twilio.auth_token=TWÓJ_AUTH_TOKEN
   twilio.phone_number=TWÓJ_NUMER_TELEFONU
   ```

### 3. Uruchomienie
```bash
mvn spring-boot:run
```

## Uwaga
Pliki z wrażliwymi danymi (credentials, tokeny) są dodane do `.gitignore` i nie są wysyłane do repozytorium.
