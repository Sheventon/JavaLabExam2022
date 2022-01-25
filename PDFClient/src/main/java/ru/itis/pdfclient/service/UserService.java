package ru.itis.pdfclient.service;

import ru.itis.pdfclient.dto.UserDto;
import ru.itis.pdfclient.dto.UserLoginDto;
import ru.itis.pdfclient.model.User;

public interface UserService {

    UserLoginDto signUpAndSave(UserLoginDto userLoginDto);

    UserDto login(UserLoginDto userLoginDto);

    void blockUser(Long id);

    User getByUsername(String username);
}
