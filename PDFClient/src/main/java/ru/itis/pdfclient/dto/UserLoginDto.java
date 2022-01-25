package ru.itis.pdfclient.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import ru.itis.pdfclient.model.User;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserLoginDto {
    private String username;
    private String password;

    public static UserLoginDto from(User user) {
        return UserLoginDto.builder()
                .username(user.getUsername())
                .password(user.getPassword())
                .build();
    }
}
