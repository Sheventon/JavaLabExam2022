package ru.itis.pdfclient.security.jwt;

import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Component;
import ru.itis.pdfclient.model.User;
import ru.itis.pdfclient.security.details.UserDetailsImpl;
import ru.itis.pdfclient.security.service.TokensService;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final TokensService tokensService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String token = authentication.getName();
        DecodedJWT decodedJWT = tokensService.decodeAccessToken(token);
        JwtAuthentication jwtAuthentication = (JwtAuthentication) authentication;

        if (decodedJWT != null) {
            User user = User.builder()
                    .id(Long.valueOf(decodedJWT.getSubject()))
                    .username(String.valueOf(decodedJWT.getClaim("username")))
                    .build();
            jwtAuthentication.setAuthenticated(true);
            jwtAuthentication.setUserDetails(new UserDetailsImpl(user));
        } else {
            jwtAuthentication.setAuthenticated(false);
        }
        return jwtAuthentication;

    }

    @Override
    public boolean supports(Class<?> authentication) {
        return JwtAuthentication.class.equals(authentication);
    }
}
