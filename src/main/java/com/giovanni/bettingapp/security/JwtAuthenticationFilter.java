package com.giovanni.bettingapp.security;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.giovanni.bettingapp.dto.ExceptionDto;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpHeaders.*;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final JwtTokenProvider jwtTokenProvider;
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = getTokenFromHeader(request);
        if (token != null) {
            try {
                DecodedJWT decodedJWT = jwtTokenProvider.verifyToken(token);
                Authentication auth = getAuthentication(decodedJWT);
                SecurityContextHolder.getContext().setAuthentication(auth);
            } catch (JWTVerificationException e) {
                SecurityContextHolder.clearContext();
                response.setStatus(401);
                response.setHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE);
                MAPPER.writeValue(response.getOutputStream(), new ExceptionDto(e.getMessage(), UNAUTHORIZED.value()));
            }
        }
        filterChain.doFilter(request, response);
    }

    private String getTokenFromHeader(HttpServletRequest request) {
        String header = request.getHeader(AUTHORIZATION);
        if (header != null && header.startsWith("Bearer")) {
            return header.replace("Bearer ", "");
        }
        return null;
    }

    private Authentication getAuthentication(DecodedJWT decodedJWT) {
        String username = decodedJWT.getSubject();
        List<String> roles = decodedJWT.getClaim("authorities").asList(String.class);
        Collection<SimpleGrantedAuthority> authorities = roles.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new UsernamePasswordAuthenticationToken(username, null, authorities);
    }
}
