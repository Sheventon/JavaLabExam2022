package ru.itis.pdfclient.security.redis.repository;

public interface BlacklistRepository {
    void save(String token);

    boolean exists(String token);
}
