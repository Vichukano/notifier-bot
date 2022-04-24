package ru.vichukano.notifier.bot.dto;

import lombok.extern.jackson.Jacksonized;
import java.util.List;

@Jacksonized
public record NotificationRequest(String uuid,
                                  String sourceName,
                                  String requestDateTime,
                                  List<Notification> notifications) {
    @Jacksonized
    public record Notification(String notifiedUserId,
                               String notificationText) {
    }
}
