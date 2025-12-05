package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.bot.BotCommands;
import org.example.enums.BotState;
import org.example.service.*;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Update;

import java.util.Locale;

@Component
@RequiredArgsConstructor
public class UpdateController {
    private final MenuService menuService;
    private final CVService cvService;
    private final SocialsService socialsService;
    private final PortfolioService portfolioService;
    private final SkillsService skillsService;
    private final FAQService faqService;
    private final FeedbackService feedbackService;
    private final SessionService sessionService;
    private final LocalizationService localizationService;

    public Object handle(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            long chatId = update.getMessage().getChatId();
            String text = update.getMessage().getText();
            Locale locale = sessionService.getLocale(chatId);

            BotState state = sessionService.getState(chatId);
            if (state == BotState.WAITING_FEEDBACK) {
                return feedbackService.handleFeedback(update);
            }

            // Команды слэша — всегда обрабатываем
            if (text.equals(BotCommands.START)) return menuService.handleStartCommand(update);
            if (text.equals(BotCommands.CV_CMD)) return cvService.sendCV(update);
            if (text.equals(BotCommands.SOCIALS_CMD)) return socialsService.getSocialsMenu(chatId);
            if (text.equals(BotCommands.PORTFOLIO_CMD))
                return portfolioService.sendPortfolio(update);
            if (text.equals(BotCommands.SKILLS_CMD)) return skillsService.sendSkills(update);
            if (text.equals(BotCommands.FAQ_CMD)) return faqService.sendFAQ(update);
            if (text.equals(BotCommands.FEEDBACK_CMD))
                return feedbackService.askForFeedback(chatId);
            if (text.equals(BotCommands.LANG_CMD)) return menuService.changeLanguage(chatId);

            // Теперь проверяем локализованные тексты кнопок
            if (text.equals(localizationService.getMessage("btn.cv", locale)))
                return cvService.sendCV(update);
            if (text.equals(localizationService.getMessage("btn.skills", locale)))
                return skillsService.sendSkills(update);
            if (text.equals(localizationService.getMessage("btn.portfolio", locale)))
                return portfolioService.sendPortfolio(update);
            if (text.equals(localizationService.getMessage("btn.socials", locale)))
                return socialsService.getSocialsMenu(chatId);
            if (text.equals(localizationService.getMessage("btn.faq", locale)))
                return faqService.sendFAQ(update);
            if (text.equals(localizationService.getMessage("btn.feedback", locale)))
                return feedbackService.askForFeedback(chatId);
            if (text.equals(localizationService.getMessage("btn.lang", locale)))
                return menuService.changeLanguage(chatId);

            return menuService.unknownCommand(update);
        }

        if (update.hasCallbackQuery()) {
            return socialsService.handleCallback(update.getCallbackQuery().getData(),
                    update.getCallbackQuery().getMessage().getChatId());
        }
        return null;
    }
}
