package org.example.service;

import lombok.RequiredArgsConstructor;
import org.example.enums.BotState;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class FeedbackService {
    private final LocalizationService localizationService;
    private final SessionService sessionService;

    @Value("${OWNER_ID}")
    private long ownerChatId;

    public SendMessage askForFeedback(long chatId) {
        sessionService.setState(chatId, BotState.WAITING_FEEDBACK);
        Locale locale = sessionService.getLocale(chatId);

        return SendMessage.builder().chatId(chatId).text(localizationService.getMessage("bot.start_feedback", locale)).build();
    }

    public List<BotApiMethod<?>> handleFeedback(Update update) {
        long chatId = update.getMessage().getChatId();
        String text = update.getMessage().getText();
        Locale locale = sessionService.getLocale(chatId);

        if (!update.getMessage().hasText()) {
            return List.of(SendMessage.builder().chatId(chatId).text(localizationService.getMessage("bot.only_text_feedback", locale)).build());
        }

        String username = update.getMessage().getFrom().getUserName();
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy " +
                "HH:mm:ss"));

        String report = localizationService.getMessage("feedback.new", locale) + "\n\n" +
                localizationService.getMessage("feedback.from", locale) + " @" +
                (username != null ? username : localizationService.getMessage("feedback.unknown", locale)) + "\n" +
                localizationService.getMessage("feedback.time", locale) + " " + timestamp + "\n\n" +
                localizationService.getMessage("feedback.message", locale) + "\n" + text;

        SendMessage toOwner = SendMessage.builder()
                .chatId(String.valueOf(ownerChatId))
                .text(report)
                .build();

        SendMessage toUser = SendMessage.builder()
                .chatId(String.valueOf(chatId))
                .text(localizationService.getMessage("bot.feedback_thanks", locale))
                .build();

        sessionService.setState(chatId, BotState.NONE);

        return List.of(toOwner, toUser);
    }
}