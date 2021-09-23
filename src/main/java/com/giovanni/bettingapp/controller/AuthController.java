package com.giovanni.bettingapp.controller;

import com.giovanni.bettingapp.dto.auth.LoginDto;
import com.giovanni.bettingapp.dto.auth.TokenDto;
import com.giovanni.bettingapp.exception.UnauthorizedException;
import com.giovanni.bettingapp.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@Valid @RequestBody LoginDto loginDto) {
        Authentication user = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        try {
            Authentication authenticationToken = authenticationManager.authenticate(user);

            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

            String token = jwtTokenProvider.generateToken(authenticationToken);

            return new ResponseEntity<>(new TokenDto(token), HttpStatus.OK);
        } catch (BadCredentialsException e) {
            throw new UnauthorizedException(e.getMessage());
        }
    }
}
