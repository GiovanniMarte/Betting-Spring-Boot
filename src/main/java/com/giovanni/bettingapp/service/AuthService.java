package com.giovanni.bettingapp.service;

import com.giovanni.bettingapp.dto.auth.LoginDto;
import com.giovanni.bettingapp.dto.auth.TokenDto;
import com.giovanni.bettingapp.exception.UnauthorizedException;
import com.giovanni.bettingapp.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final JwtTokenProvider jwtTokenProvider;
    private final AuthenticationManager authenticationManager;

    public TokenDto login(LoginDto loginDto) {
        Authentication user = new UsernamePasswordAuthenticationToken(loginDto.getUsername(), loginDto.getPassword());
        try {
            Authentication auth = authenticationManager.authenticate(user);
            SecurityContextHolder.getContext().setAuthentication(auth);
            String token = jwtTokenProvider.generateToken(auth);
            return new TokenDto(token);
        } catch (BadCredentialsException e) {
            throw new UnauthorizedException(e.getMessage());
        }
    }
}
