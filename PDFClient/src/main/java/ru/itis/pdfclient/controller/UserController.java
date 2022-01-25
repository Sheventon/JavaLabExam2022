package ru.itis.pdfclient.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import ru.itis.pdfclient.dto.PdfDataDto;
import ru.itis.pdfclient.dto.UserDto;
import ru.itis.pdfclient.dto.UserLoginDto;
import ru.itis.pdfclient.service.UserService;

import javax.annotation.security.PermitAll;

@RestController
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @PermitAll
    @PostMapping("/signUp")
    public UserLoginDto signUpUser(@RequestBody UserLoginDto userLoginDto) {
        return userService.signUpAndSave(userLoginDto);
    }

    @PermitAll
    @PostMapping("/login")
    public UserDto signInUser(@RequestBody UserLoginDto userLoginDto) {
        return userService.login(userLoginDto);
    }
}
