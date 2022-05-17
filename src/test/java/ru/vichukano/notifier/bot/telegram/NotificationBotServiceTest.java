package ru.vichukano.notifier.bot.telegram;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.vichukano.notifier.bot.dto.NotificationRequest;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@SpringBootTest(classes = NotificationBotService.class)
class NotificationBotServiceTest {
    @MockBean
    private Transformer<NotificationRequest.Notification, SendMessage> transformer;
    @MockBean
    private NotificationSender<SendMessage> sender;
    @Autowired
    private NotificationBotService testTarget;

    @Test
    void shouldProcess() {
        Mockito.when(transformer.transform(ArgumentMatchers.any())).thenReturn(new SendMessage());
        final var notification = new NotificationRequest.Notification(
            "12345",
            "Hello, world!!!"
        );
        final var notificationRequest = new NotificationRequest(
            UUID.randomUUID().toString(),
            "test",
            LocalDateTime.now().toString(),
            List.of(
                new NotificationRequest.Notification("12345", "Hello, world!!!"),
                new NotificationRequest.Notification("22222", "test")
            )
        );

        testTarget.process(notificationRequest);

        Mockito.verify(sender, Mockito.times(2))
            .sendNotification(ArgumentMatchers.any(SendMessage.class));
    }
}