package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.util.SocialsKeyboard;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;

import java.util.Locale;

@Service
@RequiredArgsConstructor
public class SocialsService {
    private final LocalizationService localizationService;
    private final SessionService sessionService;

    public SendMessage getSocialsMenu(long chatId) {
        Locale locale = sessionService.getLocale(chatId);
        return SendMessage.builder().chatId(chatId).text(localizationService.getMessage("bot.choose_social", locale)).replyMarkup(SocialsKeyboard.socialsMenu()).build();
    }

    public SendMessage handleCallback(String callback, long chatId) {
        Locale locale = sessionService.getLocale(chatId);
        String text;
        switch (callback) {
            case "SOCIAL_LINKEDIN":
                text = "https://www.linkedin.com/in/komiljon-berdiboev-910233389/";
                break;
            case "SOCIAL_EMAIL":
                text = "berdiboevkomiljon@gmail.com";
                break;
            default:
                text = localizationService.getMessage("bot.unknown_command", locale);
        }
        return SendMessage.builder().chatId(chatId).text(text).build();
    }
}