package com.giovanni.bettingapp.controller;

import com.giovanni.bettingapp.dto.UserDto;
import com.giovanni.bettingapp.dto.auth.LoginDto;
import com.giovanni.bettingapp.dto.auth.TokenDto;
import com.giovanni.bettingapp.model.User;
import com.giovanni.bettingapp.service.AuthService;
import com.giovanni.bettingapp.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;
    private final UserService userService;


    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@Valid @RequestBody LoginDto loginDto) {
        TokenDto tokenDto = authService.login(loginDto);
        return ResponseEntity.ok().body(tokenDto);
    }

    @PostMapping("/register")
    public ResponseEntity<UserDto> register(@Valid @RequestBody User user) {
        UserDto newUser = userService.saveUser(user);
        return ResponseEntity.ok().body(newUser);
    }
}
