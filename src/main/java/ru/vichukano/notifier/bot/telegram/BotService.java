package ru.vichukano.notifier.bot.telegram;

public interface BotService<T> {

    void process(T t);

}
