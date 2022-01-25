package ru.itis.pdfclient.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ru.itis.pdfclient.dto.UserDto;
import ru.itis.pdfclient.dto.UserLoginDto;
import ru.itis.pdfclient.model.User;
import ru.itis.pdfclient.repository.UserRepository;
import ru.itis.pdfclient.security.redis.service.RedisUsersService;
import ru.itis.pdfclient.security.service.TokensService;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final TokensService tokensService;
    private final PasswordEncoder passwordEncoder;
    private final RedisUsersService redisUsersService;


    @Override
    public UserLoginDto signUpAndSave(UserLoginDto userLoginDto) {
        if (userRepository.findByUsername(userLoginDto.getUsername()).isEmpty()) {
            User user = User.builder()
                    .username(userLoginDto.getUsername())
                    .password(passwordEncoder.encode(userLoginDto.getPassword()))
                    .build();
            return UserLoginDto.from(userRepository.save(user));
        } else {
            throw new IllegalStateException("User already exists");
        }
    }

    @Override
    public UserDto login(UserLoginDto userLoginDto) {
        User user = userRepository.findByUsername(userLoginDto.getUsername())
                .orElseThrow(() -> new IllegalStateException("User not found"));

        if (passwordEncoder.matches(userLoginDto.getPassword(), user.getPassword())) {
            UserDto newTokensDto = tokensService.returnsDtoWithAccessToken(user);
            redisUsersService.addAccessTokenToUser(user, newTokensDto.getAccessToken());
            return UserDto.builder()
                    .username(user.getUsername())
                    .accessToken(newTokensDto.getAccessToken())
                    .build();
        } else {
            throw new UsernameNotFoundException("Invalid username or password");
        }
    }

    @Override
    public void blockUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        redisUsersService.addAllTokensToBlackList(user);
    }

    @Override
    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalStateException("User with username " + username + " not found"));
    }
}
