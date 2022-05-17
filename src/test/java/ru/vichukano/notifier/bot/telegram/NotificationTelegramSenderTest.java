package ru.vichukano.notifier.bot.telegram;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@SpringBootTest(classes = NotificationTelegramSender.class)
class NotificationTelegramSenderTest {
    @MockBean
    private TelegramLongPollingBot botMock;
    @Autowired
    private NotificationTelegramSender testTarget;

    @Test
    void shouldExecute() throws TelegramApiException {
        final var message = new SendMessage();

        testTarget.sendNotification(message);

        Mockito.verify(botMock, Mockito.times(1)).execute(message);
    }
}