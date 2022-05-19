package ru.vichukano.notifier.bot.dao;

import lombok.extern.jackson.Jacksonized;

@Jacksonized
public record UserInfo(String uuid, String chatId, String userName) {
}
