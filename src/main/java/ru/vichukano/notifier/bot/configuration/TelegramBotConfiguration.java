package ru.vichukano.notifier.bot.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.generics.BotSession;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import ru.vichukano.notifier.bot.telegram.NotificationBot;

@Configuration
public class TelegramBotConfiguration {

    @Bean
    public BotSession notifierBot(NotificationBot bot) throws TelegramApiException {
        return new TelegramBotsApi(DefaultBotSession.class).registerBot(bot);
    }

}
