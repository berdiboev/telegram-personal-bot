package org.example.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.springframework.beans.factory.annotation.Value;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class CVService {
    private final LocalizationService localizationService;
    private final SessionService sessionService;

    @Value("${CV_FILE_ID}")
    private String cvFileId;

    public SendDocument sendCV(Update update) {
        long chatId = update.getMessage().getChatId();
        Locale locale = sessionService.getLocale(chatId);

        InputFile cvFile = new InputFile(cvFileId);

        SendDocument doc = new SendDocument();
        doc.setChatId(chatId);
        doc.setDocument(cvFile);
        doc.setCaption(localizationService.getMessage("bot.cv_caption", locale));

        return doc;
    }
}