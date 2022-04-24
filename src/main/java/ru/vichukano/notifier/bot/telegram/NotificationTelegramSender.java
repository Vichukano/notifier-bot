package ru.vichukano.notifier.bot.telegram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Slf4j
@Component
public class NotificationTelegramSender implements NotificationSender<SendMessage> {
    private final TelegramLongPollingBot bot;

    public NotificationTelegramSender(TelegramLongPollingBot bot) {
        this.bot = bot;
    }

    @Override
    public void sendNotification(SendMessage notification) {
        try {
            log.debug("Start to send notification to chat id: {}", notification.getChatId());
            log.trace("Start to send notification: {}", notification);
            bot.execute(notification);
        } catch (TelegramApiException exception) {
            log.error("Failed to send notification: {}, cause: ", notification, exception);
        }
    }
}
