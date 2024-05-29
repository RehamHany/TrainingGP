package com.panel.user.Controller;

import com.panel.user.Exception.Response.UserErrorResponse;
import com.panel.user.Exception.UserNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class UserNotFoundExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<UserErrorResponse> handlerForItemNotFound(UserNotFoundException exception){
        UserErrorResponse errorResponse=new UserErrorResponse();

        errorResponse.setCode(HttpStatus.NOT_FOUND.value());

        errorResponse.setMessage(exception.getMessage());

        errorResponse.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }
}
