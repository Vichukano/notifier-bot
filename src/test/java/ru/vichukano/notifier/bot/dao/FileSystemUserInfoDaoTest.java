package ru.vichukano.notifier.bot.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.File;
import java.util.UUID;

@Slf4j
class FileSystemUserInfoDaoTest {
    @TempDir
    private File temp;
    private FileSystemUserInfoDao testTarget;

    @BeforeEach
    void setUp() {
        testTarget = new FileSystemUserInfoDao(
            temp.getPath(),
            new ObjectMapper()
        );
    }

    @Test
    void shouldSaveAndFind() {
        final var uuid = UUID.randomUUID().toString();
        final var userInfo = new UserInfo(uuid, "1", "test");

        testTarget.add(userInfo);
        final UserInfo result = testTarget.find(uuid);

        Assertions.assertThat(result).isEqualTo(userInfo);
    }

    @Test
    void shouldNotThrowIfAddTwice() {
        final var uuid = UUID.randomUUID().toString();
        final var userInfo = new UserInfo(uuid, "1", "test");

        testTarget.add(userInfo);
        testTarget.add(userInfo);
        Assertions.assertThatNoException().isThrownBy(() -> testTarget.add(userInfo));
        final UserInfo result = testTarget.find(uuid);

        Assertions.assertThat(result).isEqualTo(userInfo);
    }

    @Test
    void shouldThrowIfNotFound() {
        Assertions.assertThatThrownBy(() -> testTarget.find("")).isInstanceOf(DaoException.class);
    }
}