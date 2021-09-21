package com.giovanni.bettingapp.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@Builder
public class ExceptionDto {
    private String message;
    private HttpStatus status;
    private List<String> errors;
    private ZonedDateTime timestamp;
}
