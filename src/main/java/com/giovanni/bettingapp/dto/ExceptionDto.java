package com.giovanni.bettingapp.dto;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class ExceptionDto {
    private String message;
    private HttpStatus status;
    private List<String> errors;
    private String timestamp;

    public ExceptionDto(String message, HttpStatus status) {
        this.message = message;
        this.status = status;
        errors = new ArrayList<>();
        timestamp = LocalDateTime.now().toString();
    }

    public ExceptionDto(String message, HttpStatus status, List<String> errors) {
        this.message = message;
        this.status = status;
        this.errors = errors;
        timestamp = LocalDateTime.now().toString();
    }
}
