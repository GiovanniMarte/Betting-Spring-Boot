package com.giovanni.bettingapp.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.giovanni.bettingapp.dto.ExceptionDto;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.springframework.http.HttpHeaders.CONTENT_TYPE;
import static org.springframework.http.HttpStatus.FORBIDDEN;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException e) throws IOException, ServletException {
        response.setStatus(403);
        response.setHeader(CONTENT_TYPE, APPLICATION_JSON_VALUE);
        MAPPER.writeValue(response.getOutputStream(), new ExceptionDto(e.getMessage(), FORBIDDEN.value()));
    }
}
