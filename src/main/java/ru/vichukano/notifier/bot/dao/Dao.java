package ru.vichukano.notifier.bot.dao;

public interface Dao<T> {

    T find(String id);

    void add(T t);

}
