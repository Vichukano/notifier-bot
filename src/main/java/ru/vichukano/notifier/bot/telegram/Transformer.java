package ru.vichukano.notifier.bot.telegram;

public interface Transformer<T, V> {

    V transform(T source);

}
