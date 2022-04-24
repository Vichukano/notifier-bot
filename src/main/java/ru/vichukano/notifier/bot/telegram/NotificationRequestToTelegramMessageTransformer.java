package ru.vichukano.notifier.bot.telegram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.vichukano.notifier.bot.dao.Dao;
import ru.vichukano.notifier.bot.dao.UserInfo;
import ru.vichukano.notifier.bot.dto.NotificationRequest;

@Slf4j
@Component
public class NotificationRequestToTelegramMessageTransformer
    implements Transformer<NotificationRequest.Notification, SendMessage> {
    private final Dao<UserInfo> userInfoDao;

    public NotificationRequestToTelegramMessageTransformer(Dao<UserInfo> userInfoDao) {
        this.userInfoDao = userInfoDao;
    }

    @Override
    public SendMessage transform(NotificationRequest.Notification request) {
        log.debug("Start to map, source in: {}", request);
        final String id = request.notifiedUserId();
        final UserInfo userInfo = userInfoDao.find(id);
        final var sendMessage = new SendMessage();
        sendMessage.setChatId(userInfo.chatId());
        sendMessage.setText(request.notificationText());
        log.debug("Result of transformation: {}", sendMessage);
        return sendMessage;
    }
}
