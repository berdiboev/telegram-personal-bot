package org.example.service;

import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class LocalizationService {
    private final MessageSource messageSource;

    public String getMessage(String key, Locale locale) {
        return messageSource.getMessage(key, null, locale);
    }

    public Locale toLocale(String langCode) {
        if (langCode == null) return Locale.forLanguageTag("ru");
        switch (langCode) {
            case "uz":
                return new Locale("uz");
            case "en":
                return Locale.ENGLISH;
            default:
                return Locale.forLanguageTag("ru");
        }
    }
}