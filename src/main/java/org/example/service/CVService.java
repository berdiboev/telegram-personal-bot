package org.example.service;

import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class CVService {
    private final LocalizationService localizationService;
    private final SessionService sessionService;

    public SendDocument sendCV(Update update) {
        long chatId = update.getMessage().getChatId();
        Locale locale = sessionService.getLocale(chatId);

        InputStream cvStream = null;
        try {
            cvStream = new ClassPathResource("cv/Komiljon_CV.pdf").getInputStream();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        SendDocument doc = new SendDocument();
        doc.setChatId(chatId);
        doc.setDocument(new InputFile(cvStream, "Komiljon_CV.pdf"));
        doc.setCaption(localizationService.getMessage("bot.cv_caption", locale));

        return doc;
    }
}