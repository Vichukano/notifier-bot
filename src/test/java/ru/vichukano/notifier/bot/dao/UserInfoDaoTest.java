package ru.vichukano.notifier.bot.dao;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

class UserInfoDaoTest {
    private UserInfoDao testTarget;

    @BeforeEach
    void init() {
        Map<String, UserInfo> store = new HashMap<>();
        store.put("1", new UserInfo("1", UUID.randomUUID().toString(), UUID.randomUUID().toString()));
        store.put("2", new UserInfo("2", UUID.randomUUID().toString(), UUID.randomUUID().toString()));
        store.put("3", new UserInfo("3", UUID.randomUUID().toString(), UUID.randomUUID().toString()));
        testTarget = new UserInfoDao(store);
    }

    @Test
    void shouldFind() {
        final UserInfo userInfo = testTarget.find("2");

        Assertions.assertThat(userInfo).isNotNull();
        Assertions.assertThat(userInfo.uuid()).isEqualTo("2");
    }

    @Test
    void shouldAdd() {
        final var userInfo = new UserInfo("4", UUID.randomUUID().toString(), UUID.randomUUID().toString());

        testTarget.add(userInfo);
        final UserInfo found = testTarget.find("4");

        Assertions.assertThat(found).isNotNull();
        Assertions.assertThat(found.uuid()).isEqualTo("4");
    }

    @Test
    void shouldThrowExceptionIfNotFound() {
        Assertions.assertThatThrownBy(() -> testTarget.find("100500"))
            .isInstanceOf(DaoException.class);
    }
}