package com.haxul.manager.users.errors;

import com.google.gson.Gson;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {

    private final Gson gson;

    public ExceptionController(Gson gson) {
        this.gson = gson;
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorMessage message = new ErrorMessage("Error", ex.getMessage());
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(gson.toJson(message));
    }

    @ExceptionHandler(value = {UsernameExistException.class})
    public ResponseEntity<Object> handleAnyException(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage("Error", "Username exists");
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(gson.toJson(message));
    }
}
