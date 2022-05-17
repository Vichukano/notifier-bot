package ru.vichukano.notifier.bot.telegram;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import ru.vichukano.notifier.bot.dto.NotificationRequest;
import java.util.Collection;
import java.util.List;
import java.util.stream.Stream;

@Slf4j
@Component
public class NotificationBotService implements BotService<NotificationRequest> {
    private final Transformer<NotificationRequest.Notification, SendMessage> transformer;
    private final NotificationSender<SendMessage> sender;

    public NotificationBotService(Transformer<NotificationRequest.Notification, SendMessage> transformer,
                                  NotificationSender<SendMessage> sender) {
        this.transformer = transformer;
        this.sender = sender;
    }

    @Override
    public void process(NotificationRequest request) {
        log.debug("Start to process request: {}", request);
        final List<SendMessage> messages = Stream.ofNullable(request.notifications())
            .flatMap(Collection::stream)
            .map(transformer::transform)
            .toList();
        messages.forEach(sender::sendNotification);
        log.debug("Finish processing for request: {}", request);
    }
}
