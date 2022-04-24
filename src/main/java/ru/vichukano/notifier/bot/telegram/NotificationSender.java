package ru.vichukano.notifier.bot.telegram;

public interface NotificationSender<T> {

    void sendNotification(T nodification);

}
