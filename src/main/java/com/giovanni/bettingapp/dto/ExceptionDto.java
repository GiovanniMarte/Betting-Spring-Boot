package com.giovanni.bettingapp.dto;

import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.ArrayList;

@Data
@Builder
public class ExceptionDto {
    private String message;
    private HttpStatus status;
    private ArrayList<String> errors;
    private ZonedDateTime timestamp;
}
