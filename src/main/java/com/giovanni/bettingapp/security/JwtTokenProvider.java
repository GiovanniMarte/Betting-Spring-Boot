package com.giovanni.bettingapp.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {
    private static final int EXPIRATION = 1000 * 60 * 60;
    private static final String SECRET = "mY%sECurE_sEcRet@keY";
    private final Algorithm algorithm;

    public JwtTokenProvider() {
        algorithm = Algorithm.HMAC256(SECRET.getBytes());
    }

    public String generateToken(Authentication auth) {
        return JWT.create()
                .withSubject(auth.getName())
                .withExpiresAt(new Date(System.currentTimeMillis() + EXPIRATION))
                .withClaim("authorities",
                        auth.getAuthorities().stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()))
                .sign(algorithm);
    }

    public DecodedJWT verifyToken(String token) throws JWTVerificationException {
        return JWT.require(algorithm).build().verify(token);
    }
}
