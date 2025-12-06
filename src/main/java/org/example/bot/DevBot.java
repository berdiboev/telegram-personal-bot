package org.example.bot;

import io.github.cdimascio.dotenv.Dotenv;
import lombok.RequiredArgsConstructor;
import org.example.controller.UpdateController;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.BotApiMethod;
import org.telegram.telegrambots.meta.api.methods.send.SendDocument;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.List;
import java.util.logging.Logger;

@Component
@RequiredArgsConstructor
public class DevBot extends TelegramLongPollingBot {
    private static final Logger log = Logger.getLogger(DevBot.class.getName());
    private final BotCommands botCommands = new BotCommands();
    private final UpdateController updateController;
//    private final Dotenv dotenv;

    @Override
    public void onUpdateReceived(Update update) {
        Object response = updateController.handle(update);
        try {
            if (response instanceof BotApiMethod<?> apiMethod) {
                execute(apiMethod);
            } else if (response instanceof SendDocument document) {
                execute(document);
            } else if (response instanceof SendPhoto photo) {
                execute(photo);
            } else if (response instanceof List<?> list) {
                for (Object obj : list) {
                    if (obj instanceof BotApiMethod<?> method) {
                        execute(method);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

//    @Override
//    public String getBotUsername() {
//        return dotenv.get("BOT_USERNAME");
//    }

    @Override
    public String getBotUsername() {
        return System.getenv("BOT_USERNAME");
    }


//    @Override
//    public String getBotToken() {
//        return dotenv.get("BOT_TOKEN");
//    }

    @Override
    public String getBotToken() {
        return System.getenv("BOT_TOKEN");
    }
}