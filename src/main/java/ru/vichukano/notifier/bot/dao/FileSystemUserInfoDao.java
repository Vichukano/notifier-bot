package ru.vichukano.notifier.bot.dao;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

@Slf4j
public class FileSystemUserInfoDao implements Dao<UserInfo> {
    private static final String SFX = ".ser";
    private final Path rootPath;
    private final ObjectMapper objectMapper;

    public FileSystemUserInfoDao(String path, ObjectMapper objectMapper) {
        this.rootPath = Path.of(path);
        this.objectMapper = objectMapper;
    }

    @Override
    public UserInfo find(String id) {
        log.trace("Start to find user info by id: {}", id);
        try (InputStream is = Files.newInputStream(rootPath.resolve(Path.of(id + SFX)))) {
            final UserInfo userInfo = objectMapper.readValue(is, UserInfo.class);
            log.trace("Found user info: {}", userInfo);
            return userInfo;
        } catch (Exception e) {
            throw new DaoException(
                "Failed to find userInfo by id: "
                    + id
                    + " cause: "
                    + e.getMessage()
            );
        }
    }

    @Override
    public void add(UserInfo userInfo) {
        try {
            log.trace("Start to add userInfo: {}", userInfo);
            final Path path = rootPath.resolve(Path.of(userInfo.uuid() + SFX));
            final String content = objectMapper.writeValueAsString(userInfo);
            Files.writeString(path, content);
            log.trace("Successfully add userInfo: {}", userInfo);
        } catch (IOException e) {
            throw new DaoException(e.getMessage());
        }
    }
}
