package org.example.util;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.util.Arrays;
import java.util.Collections;

public class SocialsKeyboard {
    public static InlineKeyboardMarkup socialsMenu() {
        InlineKeyboardButton LinkedIn = InlineKeyboardButton.builder().text("LinkedIn").callbackData(
                "SOCIAL_LINKEDIN").build();

        InlineKeyboardButton email = InlineKeyboardButton.builder().text("Email").callbackData(
                "SOCIAL_EMAIL").build();

        return InlineKeyboardMarkup.builder().keyboard(Arrays.asList(Collections.singletonList(LinkedIn),
                Collections.singletonList(email))).build();
    }
}