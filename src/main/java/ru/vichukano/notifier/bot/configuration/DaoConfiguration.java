package ru.vichukano.notifier.bot.configuration;


import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.vichukano.notifier.bot.dao.Dao;
import ru.vichukano.notifier.bot.dao.FileSystemUserInfoDao;
import ru.vichukano.notifier.bot.dao.InMemoryUserInfoDao;
import ru.vichukano.notifier.bot.dao.UserInfo;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Configuration
public class DaoConfiguration {

    @Bean(name = "chatToUserInfo")
    @ConditionalOnProperty(prefix = "app.dao", name = "type", havingValue = "memory")
    public Map<String, UserInfo> chatToUserInfo() {
        return new ConcurrentHashMap<>();
    }

    @Bean
    @ConditionalOnProperty(prefix = "app.dao", name = "type", havingValue = "memory")
    public Dao<UserInfo> inMemoryDao(Map<String, UserInfo> chatToUserInfo) {
        log.info("Construct in memory dao");
        return new InMemoryUserInfoDao(chatToUserInfo);
    }

    @Bean
    @ConditionalOnProperty(prefix = "app.dao", name = "type", havingValue = "file")
    public Dao<UserInfo> fileSystemDao(@Value("${app.dao.dir}") String rootDir,
                                       ObjectMapper mapper) {
        log.info("Construct file system dao");
        return new FileSystemUserInfoDao(rootDir, mapper);
    }

}
