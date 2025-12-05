package org.example.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class FAQService {
    private final LocalizationService localizationService;
    private final SessionService sessionService;

    public SendMessage sendFAQ(Update update) {
        long chatId = update.getMessage().getChatId();
        Locale locale = sessionService.getLocale(chatId);
        SendMessage message = new SendMessage();
        message.setChatId(String.valueOf(chatId));
        message.setText(localizationService.getMessage("bot.faq_text", locale));
        return message;
    }
}