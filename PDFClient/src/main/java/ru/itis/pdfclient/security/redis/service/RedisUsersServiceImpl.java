package ru.itis.pdfclient.security.redis.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.itis.pdfclient.model.User;
import ru.itis.pdfclient.repository.UserRepository;
import ru.itis.pdfclient.security.redis.model.RedisUser;
import ru.itis.pdfclient.security.redis.repository.RedisUserRepository;
import ru.itis.pdfclient.security.service.JwtBlacklistService;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
public class RedisUsersServiceImpl implements RedisUsersService {

    private final UserRepository userRepository;
    private final JwtBlacklistService jwtBlacklistService;
    private final RedisUserRepository redisUserRepository;

    @Override
    public void addAccessTokenToUser(User user, String accessToken) {
        String redisId = user.getRedisId();
        RedisUser redisUser;
        if (redisId != null) {
            redisUser = redisUserRepository.findById(redisId)
                    .orElseThrow(() -> new IllegalStateException("Redis user not found"));
            if (redisUser.getTokens() == null) {
                redisUser.setTokens(new ArrayList<>());
            }
            redisUser.getTokens().add(accessToken);
        } else {
            redisUser = RedisUser.builder()
                    .userId(user.getId())
                    .tokens(List.of(accessToken))
                    .build();
        }
        redisUserRepository.save(redisUser);
        user.setRedisId(redisUser.getId());
        userRepository.save(user);
    }

    @Override
    public void addAllTokensToBlackList(User user) {
        if (user.getRedisId() != null) {
            RedisUser redisUser = redisUserRepository.findById(user.getRedisId())
                    .orElseThrow(IllegalArgumentException::new);
            List<String> tokens = redisUser.getTokens();
            for (String token : tokens) {
                jwtBlacklistService.add(token);
            }
            redisUser.getTokens().clear();
            redisUserRepository.save(redisUser);
        }
    }
}
