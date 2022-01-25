package ru.itis.pdfclient.security.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.itis.pdfclient.security.redis.repository.BlacklistRepository;

@Component
@RequiredArgsConstructor
public class JwtBlacklistServiceImpl implements JwtBlacklistService {

    private final BlacklistRepository blacklistRepository;

    @Override
    public void add(String token) {
        blacklistRepository.save(token);
    }

    @Override
    public boolean exists(String token) {
        return blacklistRepository.exists(token);
    }
}
