package ru.itis.pdfclient.security.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.SignatureVerificationException;
import com.auth0.jwt.exceptions.TokenExpiredException;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.itis.pdfclient.dto.UserDto;
import ru.itis.pdfclient.model.User;
import ru.itis.pdfclient.repository.UserRepository;

import java.util.Date;

@Component
@RequiredArgsConstructor
public class TokensServiceImpl implements TokensService {

    private final UserRepository usersRepository;

    @Value("${jwt.key}")
    private String key;

    @Override
    public String generateAccessToken(User user) {
        return JWT.create()
                .withSubject(user.getId().toString())
                .withClaim("username", user.getUsername())
                .withIssuedAt(new Date())
                .withExpiresAt(new Date(System.currentTimeMillis() + 1000 * 60 * 1440 * 30L))
                .sign(Algorithm.HMAC256(key));
    }

    @Override
    public DecodedJWT decodeAccessToken(String token) {
        return JWT.require(Algorithm.HMAC256(key))
                .build()
                .verify(token);
    }

    @Override
    public Boolean verifyAccessToken(String token) {
        try {
            DecodedJWT decodedJWT = JWT.require(Algorithm.HMAC256(key))
                    .build()
                    .verify(token);
            return decodedJWT.getExpiresAt().after(new Date());
        } catch (TokenExpiredException | SignatureVerificationException e) {
            return false;
        }
    }

    @Override
    public UserDto returnsDtoWithAccessToken(User user) {
        String accessToken = generateAccessToken(user);
        return UserDto.builder()
                .username(user.getUsername())
                .accessToken(accessToken)
                .build();
    }

    @Override
    public UserDto refreshAccessToken(UserDto userDto) {
        User user = usersRepository.findByUsername(userDto.getUsername())
                .orElseThrow(() -> new IllegalStateException("User not found"));
        return UserDto.builder()
                .username(user.getUsername())
                .accessToken(generateAccessToken(user))
                .build();
    }
}
