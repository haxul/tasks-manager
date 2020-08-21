package com.haxul.manager.configurations;

import com.google.gson.Gson;
import com.haxul.manager.users.errors.AccessForbiddenException;
import com.haxul.manager.users.errors.ErrorMessage;
import com.haxul.manager.users.errors.LoginFailedException;
import com.haxul.manager.users.errors.UsernameExistException;
import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ExceptionController extends ResponseEntityExceptionHandler {

    private final Gson gson;

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatus status, WebRequest request) {
        ErrorMessage message = new ErrorMessage("Error", ex.getMessage());
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(gson.toJson(message));
    }

    @ExceptionHandler(value = {UsernameExistException.class})
    public ResponseEntity<Object> handleUsernameExistException(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage("Error", "Username exists");
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(gson.toJson(message));
    }

    @ExceptionHandler(value = {LoginFailedException.class})
    public ResponseEntity<Object> handleLoginFailedException(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage("Error", "Login is failed");
        return ResponseEntity.badRequest().contentType(MediaType.APPLICATION_JSON).body(gson.toJson(message));
    }

    @ExceptionHandler(value = {AccessForbiddenException.class})
    public ResponseEntity<Object> handleAccessForbidden(Exception ex, WebRequest request) {
        ErrorMessage message = new ErrorMessage("Error", "Access forbidden");
        return ResponseEntity.status(HttpStatus.FORBIDDEN).contentType(MediaType.APPLICATION_JSON).body(gson.toJson(message));
    }
}
