package org.example.model;

import lombok.Data;
import org.example.enums.BotState;

import java.util.Locale;

@Data
public class UserSession {
    private long chatId;
    private BotState state = BotState.NONE;
    private Locale locale = Locale.forLanguageTag("ru");

    public UserSession(long chatId) {
        this.chatId = chatId;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }
}