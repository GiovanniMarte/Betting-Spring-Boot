package com.giovanni.bettingapp.exception.handler;

import com.giovanni.bettingapp.dto.ExceptionDto;
import com.giovanni.bettingapp.exception.BadRequestException;
import com.giovanni.bettingapp.exception.ConflictException;
import com.giovanni.bettingapp.exception.ResourceNotFoundException;
import com.giovanni.bettingapp.exception.UnauthorizedException;
import com.giovanni.bettingapp.util.ConstantUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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
        return new ResponseEntity<>(exceptionDto, BAD_REQUEST);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ExceptionDto> handleException(ResourceNotFoundException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(ex.getMessage(), NOT_FOUND);
        return new ResponseEntity<>(exceptionDto, NOT_FOUND);
    }

    @ExceptionHandler(ConflictException.class)
    public ResponseEntity<ExceptionDto> handleException(ConflictException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(ex.getMessage(), CONFLICT);
        return new ResponseEntity<>(exceptionDto, CONFLICT);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ExceptionDto> handleException(Errors errors) {
        ArrayList<String> errorMessages = new ArrayList<>();
        errors.getAllErrors().forEach(error -> errorMessages.add(error.getDefaultMessage()));

        ExceptionDto exceptionDto = new ExceptionDto(ConstantUtil.VALIDATION_ERROR, BAD_REQUEST, errorMessages);
        return new ResponseEntity<>(exceptionDto, BAD_REQUEST);
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException.class)
    public ResponseEntity<ExceptionDto> handleException(HttpMediaTypeNotSupportedException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(ex.getMessage(), UNSUPPORTED_MEDIA_TYPE);
        return new ResponseEntity<>(exceptionDto, UNSUPPORTED_MEDIA_TYPE);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionDto> handleException(HttpMessageNotReadableException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(ex.getMessage(), BAD_REQUEST);
        return new ResponseEntity<>(exceptionDto, BAD_REQUEST);
    }

    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<ExceptionDto> handleException(HttpRequestMethodNotSupportedException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(ex.getMessage(), METHOD_NOT_ALLOWED);
        return new ResponseEntity<>(exceptionDto, METHOD_NOT_ALLOWED);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public ResponseEntity<ExceptionDto> handleException(UnauthorizedException ex) {
        ExceptionDto exceptionDto = new ExceptionDto(ex.getMessage(), UNAUTHORIZED);
        return new ResponseEntity<>(exceptionDto, UNAUTHORIZED);
    }

    @ExceptionHandler(NoHandlerFoundException.class)
    public ResponseEntity<ExceptionDto> handleException() {
        ExceptionDto exceptionDto = new ExceptionDto(ConstantUtil.URL_NOT_FOUND, NOT_FOUND);
        return new ResponseEntity<>(exceptionDto, NOT_FOUND);
    }
}