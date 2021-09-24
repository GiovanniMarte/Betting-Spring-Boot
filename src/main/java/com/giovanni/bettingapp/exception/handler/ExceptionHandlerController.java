package com.giovanni.bettingapp.exception.handler;

import com.giovanni.bettingapp.dto.ExceptionDto;
import com.giovanni.bettingapp.exception.*;
import com.giovanni.bettingapp.util.ConstantUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.validation.Errors;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.NoHandlerFoundException;

import java.util.ArrayList;

import static org.springframework.http.HttpStatus.*;

@ControllerAdvice
public class ExceptionHandlerController {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ExceptionDto> handleException(BadRequestException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(ex.getMessage(), BAD_REQUEST);
        return ResponseEntity.status(BAD_REQUEST).body(exceptionDto);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleException(ResourceNotFoundException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(ex.getMessage(), NOT_FOUND);
        return ResponseEntity.status(NOT_FOUND).body(exceptionDto);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ExceptionDto> handleException(ConflictException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(ex.getMessage(), CONFLICT);
        return ResponseEntity.status(CONFLICT).body(exceptionDto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDto> handleException(Errors errors) {
        ArrayList<String> errorMessages = new ArrayList<>();
        errors.getAllErrors().forEach(error -> errorMessages.add(error.getDefaultMessage()));

        ExceptionDto exceptionDto = new ExceptionDto(ConstantUtil.VALIDATION_ERROR, BAD_REQUEST, errorMessages);
        return ResponseEntity.status(BAD_REQUEST).body(exceptionDto);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ExceptionDto> handleException(HttpMediaTypeNotSupportedException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(ex.getMessage(), UNSUPPORTED_MEDIA_TYPE);
        return ResponseEntity.status(UNSUPPORTED_MEDIA_TYPE).body(exceptionDto);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionDto> handleException(HttpMessageNotReadableException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(ex.getMessage(), BAD_REQUEST);
        return ResponseEntity.status(BAD_REQUEST).body(exceptionDto);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ExceptionDto> handleException(HttpRequestMethodNotSupportedException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(ex.getMessage(), METHOD_NOT_ALLOWED);
        return ResponseEntity.status(METHOD_NOT_ALLOWED).body(exceptionDto);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ExceptionDto> handleException(UnauthorizedException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(ex.getMessage(), UNAUTHORIZED);
        return ResponseEntity.status(UNAUTHORIZED).body(exceptionDto);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ExceptionDto> handleException(AccessDeniedException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(ex.getMessage(), FORBIDDEN);
        return ResponseEntity.status(FORBIDDEN).body(exceptionDto);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ExceptionDto> handleException() {
        ExceptionDto exceptionDto = new ExceptionDto(ConstantUtil.URL_NOT_FOUND, NOT_FOUND);
        return ResponseEntity.status(NOT_FOUND).body(exceptionDto);
    }
}