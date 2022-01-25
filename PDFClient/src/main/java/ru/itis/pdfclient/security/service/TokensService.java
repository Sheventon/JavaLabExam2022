package ru.itis.pdfclient.security.service;

import com.auth0.jwt.interfaces.DecodedJWT;
import ru.itis.pdfclient.dto.UserDto;
import ru.itis.pdfclient.model.User;

public interface TokensService {
    String generateAccessToken(User user);

    DecodedJWT decodeAccessToken(String token);

    Boolean verifyAccessToken(String token);

    UserDto returnsDtoWithAccessToken(User user);

    UserDto refreshAccessToken(UserDto userDto);
}
