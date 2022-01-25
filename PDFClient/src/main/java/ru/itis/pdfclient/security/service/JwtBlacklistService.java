package ru.itis.pdfclient.security.service;

public interface JwtBlacklistService {
    void add(String token);

    boolean exists(String token);
}
