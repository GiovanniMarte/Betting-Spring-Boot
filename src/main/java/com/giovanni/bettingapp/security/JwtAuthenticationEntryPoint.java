package com.giovanni.bettingapp.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.giovanni.bettingapp.dto.ExceptionDto;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpStatus.UNAUTHORIZED;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException e) throws IOException, ServletException {
        response.setStatus(401);
        response.setHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        MAPPER.writeValue(response.getOutputStream(), new ExceptionDto(e.getMessage(), UNAUTHORIZED.value()));
    }
}
