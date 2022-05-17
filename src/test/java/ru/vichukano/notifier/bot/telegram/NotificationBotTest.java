package ru.vichukano.notifier.bot.telegram;

import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Chat;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import ru.vichukano.notifier.bot.dao.Dao;
import ru.vichukano.notifier.bot.dao.UserInfo;
import ru.vichukano.notifier.bot.dao.UserInfoDao;
import java.util.HashMap;

@SpringBootTest(classes = {NotificationBot.class})
class NotificationBotTest {
    private final Dao<UserInfo> userInfoDao = new UserInfoDao(new HashMap<>());
    @MockBean
    private NotificationBot mock;
    private final NotificationBot testTarget = new NotificationBot(userInfoDao, "test", "test") {
        @Override
        TelegramLongPollingBot self() {
            return mock;
        }
    };

    @Test
    void shouldExecuteBotCommand() throws TelegramApiException {
        var user = new User();
        user.setFirstName("my name");
        user.setId(1L);
        var message = new Message();
        message.setFrom(user);
        var chat = new Chat();
        chat.setId(100500L);
        message.setChat(chat);
        var update = new Update();
        update.setMessage(message);

        testTarget.onUpdateReceived(update);

        Mockito.verify(mock, Mockito.times(1)).execute(ArgumentMatchers.any(SendMessage.class));
    }

    @Test
    void shouldNotExecuteIfEmptyUpdate() throws TelegramApiException {
        testTarget.onUpdateReceived(new Update());

        Mockito.verify(mock, Mockito.times(0)).execute(ArgumentMatchers.any(SendMessage.class));
    }
}