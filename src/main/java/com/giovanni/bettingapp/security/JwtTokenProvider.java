package com.giovanni.bettingapp.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Component
public class JwtTokenProvider {
    private final int expiration;
    private final Algorithm algorithm;

    public JwtTokenProvider(@Value("${jwt.expiration}") int expiration, @Value("${jwt.secret}") String secret) {
        this.expiration = expiration;
        this.algorithm = Algorithm.HMAC256(secret.getBytes());
    }

    public String generateToken(Authentication auth) {
        return JWT.create()
                .withSubject(auth.getName())
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
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
