package ru.vichukano.notifier.bot.telegram;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.vichukano.notifier.bot.dao.Dao;
import ru.vichukano.notifier.bot.dao.UserInfo;
import ru.vichukano.notifier.bot.dto.NotificationRequest;

@SpringBootTest(classes = NotificationRequestToTelegramMessageTransformer.class)
class NotificationRequestToTelegramMessageTransformerTest {
    @MockBean
    private Dao<UserInfo> userInfoDao;
    @Autowired
    private NotificationRequestToTelegramMessageTransformer testTarget;


    @Test
    @DisplayName("should successfully transform")
    void shouldTransform() {
        final var id = "12345";
        final var userInfo = new UserInfo("111", "123", "name");
        final var notification = new NotificationRequest.Notification(
            id,
            "Hello, world!!!"
        );
        Mockito.when(userInfoDao.find(id)).thenReturn(userInfo);

        final SendMessage result = testTarget.transform(notification);

        Assertions.assertThat(result.getChatId()).isEqualTo(userInfo.chatId());
        Assertions.assertThat(result.getText()).isEqualTo(notification.notificationText());
    }
}
