package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.util.KeyboardFactory;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class MenuService {
    private final LocalizationService localizationService;
    private final SessionService sessionService;

    public SendMessage handleStartCommand(Update update) {
        long chatId = update.getMessage().getChatId();
        Locale locale = sessionService.getLocale(chatId);
        String text = localizationService.getMessage("bot.greeting", locale);

        return SendMessage.builder().chatId(String.valueOf(chatId)).text(text).replyMarkup(KeyboardFactory.createMainMenu(localizationService, locale)).build();
    }

    public SendMessage changeLanguage(long chatId) {
        Locale current = sessionService.getLocale(chatId);

        Locale next;
        if (current.getLanguage().equals("ru")) next = new Locale("uz");
        else if (current.getLanguage().equals("uz")) next = Locale.ENGLISH;
        else next = new Locale("ru");

        sessionService.setLocale(chatId, next);

        String msg = localizationService.getMessage("bot.language_changed", next);
        return SendMessage.builder().chatId(chatId).text(msg).replyMarkup(KeyboardFactory.createMainMenu(localizationService, next)).build();
    }

    public SendMessage unknownCommand(Update update) {
        long chatId = update.getMessage().getChatId();
        Locale locale = sessionService.getLocale(chatId);
        return SendMessage.builder().chatId(chatId).text(localizationService.getMessage("bot" +
                ".unknown_command", locale)).replyMarkup(KeyboardFactory.createMainMenu(localizationService, locale)).build();
    }
}