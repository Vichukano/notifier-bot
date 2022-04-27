package ru.vichukano.notifier.bot;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.generics.BotSession;
import ru.vichukano.notifier.bot.dao.UserInfo;
import ru.vichukano.notifier.bot.dto.NotificationRequest;
import ru.vichukano.notifier.bot.telegram.NotificationSender;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class NotificationControllerTest {
    private static final String LHOST = "http://localhost:";
    @MockBean
    private NotificationSender<SendMessage> mockSender;
    @MockBean
    private BotSession mockBotSession;
    @MockBean
    private Map<String, UserInfo> chatToUserInfo;
    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        Mockito.when(chatToUserInfo.get("12345"))
            .thenReturn(new UserInfo("12345", "123", "test"));
    }

    @Test
    @DisplayName("when receive new request then send SendMessage")
    void shouldNotify() {
        final var notification = new NotificationRequest.Notification(
            "12345",
            "Hello, world!!!"
        );
        final var notificationRequest = new NotificationRequest(
            UUID.randomUUID().toString(),
            "test",
            LocalDateTime.now().toString(),
            List.of(notification)
        );

        final ResponseEntity<Object> responseEntity = restTemplate.postForEntity(
            LHOST + port + "/api/v1/notificate",
            notificationRequest,
            Object.class
        );

        Mockito.verify(mockSender, Mockito.times(1))
            .sendNotification(ArgumentMatchers.any(SendMessage.class));
        Assertions.assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
    }
}