package org.example.service;

import org.example.enums.BotState;
import org.example.model.UserSession;
import org.springframework.stereotype.Service;

import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class SessionService {
    private final Map<Long, UserSession> sessions = new ConcurrentHashMap<>();

    public UserSession getSession(long chatId) {
        return sessions.computeIfAbsent(chatId, UserSession::new);
    }

    public BotState getState(long chatId) {
        return getSession(chatId).getState();
    }

    public void setState(long chatId, BotState state) {
        getSession(chatId).setState(state);
    }

    public void setLocale(long chatId, Locale locale) {
        getSession(chatId).setLocale(locale);
    }

    public Locale getLocale(long chatId) {
        return getSession(chatId).getLocale();
    }
}