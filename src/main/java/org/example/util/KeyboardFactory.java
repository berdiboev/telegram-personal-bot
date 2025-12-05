package org.example.util;

import org.example.service.LocalizationService;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardButton;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class KeyboardFactory {

    public static ReplyKeyboardMarkup createMainMenu(LocalizationService localizationService,
                                                     Locale locale) {
        ReplyKeyboardMarkup keyboard = new ReplyKeyboardMarkup();
        keyboard.setResizeKeyboard(true);
        keyboard.setOneTimeKeyboard(false);
        keyboard.setSelective(true);

        List<KeyboardRow> rows = new ArrayList<>();

        KeyboardRow row1 = new KeyboardRow();
        row1.add(new KeyboardButton(localizationService.getMessage("btn.cv", locale)));
        row1.add(new KeyboardButton(localizationService.getMessage("btn.skills", locale)));

        KeyboardRow row2 = new KeyboardRow();
        row2.add(new KeyboardButton(localizationService.getMessage("btn.portfolio", locale)));
        row2.add(new KeyboardButton(localizationService.getMessage("btn.socials", locale)));

        KeyboardRow row3 = new KeyboardRow();
        row3.add(new KeyboardButton(localizationService.getMessage("btn.faq", locale)));
        row3.add(new KeyboardButton(localizationService.getMessage("btn.feedback", locale)));

        KeyboardRow row4 = new KeyboardRow();
        row4.add(new KeyboardButton(localizationService.getMessage("btn.lang", locale)));

        rows.add(row1);
        rows.add(row2);
        rows.add(row3);
        rows.add(row4);

        keyboard.setKeyboard(rows);
        return keyboard;
    }
}