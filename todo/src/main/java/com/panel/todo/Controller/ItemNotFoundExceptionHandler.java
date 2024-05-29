package com.panel.todo.Controller;

import com.panel.todo.Exception.ItemNotFoundException;
import com.panel.todo.Exception.Response.ItemErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
@ControllerAdvice
public class ItemNotFoundExceptionHandler {
    @ExceptionHandler
    public ResponseEntity<ItemErrorResponse> handlerForItemNotFound(ItemNotFoundException exception){
        ItemErrorResponse errorResponse=new ItemErrorResponse();

        errorResponse.setCode(HttpStatus.NOT_FOUND.value());

        errorResponse.setMessage(exception.getMessage());

        errorResponse.setTimestamp(System.currentTimeMillis());

        return new ResponseEntity<>(errorResponse,HttpStatus.NOT_FOUND);
    }
}
