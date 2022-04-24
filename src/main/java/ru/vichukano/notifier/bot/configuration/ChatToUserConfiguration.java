package ru.vichukano.notifier.bot.configuration;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.vichukano.notifier.bot.dao.UserInfo;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class ChatToUserConfiguration {

    @Bean(name = "chatToUserInfo")
    public Map<String, UserInfo> chatToUser() {
        return new ConcurrentHashMap<>();
    }

}
