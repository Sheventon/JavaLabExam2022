package ru.itis.pdfclient.security.redis.service;

import ru.itis.pdfclient.model.User;

public interface RedisUsersService {
    void addAccessTokenToUser(User user, String accessToken);

    void addAllTokensToBlackList(User user);
}
