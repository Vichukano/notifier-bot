package ru.vichukano.notifier.bot.dao;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Component
public class UserInfoDao implements Dao<UserInfo> {
    private final Map<String, UserInfo> chatToUserInfo;

    public UserInfoDao(@Qualifier("chatToUserInfo") Map<String, UserInfo> chatToUserInfo) {
        this.chatToUserInfo = chatToUserInfo;
    }

    @Override
    public UserInfo find(String id) {
        log.trace("Start to find user info by id: {}", id);
        final UserInfo userInfo = Optional.ofNullable(chatToUserInfo.get(id))
            .orElseThrow(() -> new DaoException("Failed to find userInfo by id: " + id));
        log.trace("Found user info: {}", userInfo);
        return userInfo;
    }

    @Override
    public void add(UserInfo userInfo) {
        log.trace("Start to add userInfo: {}", userInfo);
        chatToUserInfo.put(userInfo.uuid(), userInfo);
        log.trace("Successfully add userInfo: {}", userInfo);
    }
}
