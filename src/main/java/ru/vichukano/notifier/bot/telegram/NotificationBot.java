package ru.vichukano.notifier.bot.telegram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.vichukano.notifier.bot.dao.Dao;
import ru.vichukano.notifier.bot.dao.UserInfo;
import javax.annotation.PostConstruct;

@Slf4j
@Component
public class NotificationBot extends TelegramLongPollingBot {
    private final Dao<UserInfo> userInfoDao;
    private final String botName;
    private final String botToken;

    public NotificationBot(Dao<UserInfo> userInfoDao,
                           @Value("${app.bot.name}") String botName,
                           @Value("${app.bot.token}") String botToken) {
        this.userInfoDao = userInfoDao;
        this.botName = botName;
        this.botToken = botToken;
    }

    @PostConstruct
    public void init() {
        log.info("Bot [{}] successfully started", this.getBotUsername());
    }

    @Override
    public String getBotUsername() {
        return botName;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        log.debug("Receive update: {}", update);
        if (update.hasMessage()) {
            final Message message = update.getMessage();
            final User from = message.getFrom();
            final String chatId = String.valueOf(message.getChatId());
            UserInfo userInfo = new UserInfo(
                String.valueOf(from.getId()),
                chatId,
                from.getFirstName()
            );
            userInfoDao.add(userInfo);
            final var answer = new SendMessage();
            answer.setText("Successfully register user. Now you will receive notifications in this chat.");
            answer.setChatId(chatId);
            try {
                execute(answer);
            } catch (TelegramApiException e) {
                log.error("Failed to send answer: {}, cause: ", answer, e);
            }
        }
        log.debug("Finish to process update: {}", update);
    }
}
