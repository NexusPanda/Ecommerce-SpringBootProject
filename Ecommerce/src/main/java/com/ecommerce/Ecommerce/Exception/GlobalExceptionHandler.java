package com.ecommerce.Ecommerce.Exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;

@RestControllerAdvice
public class GlobalExceptionHandler {

//    @ExceptionHandler
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> MethodArgumentNotValidExceptionMethod(MethodArgumentNotValidException e){
        Map<String, String> exception = new HashMap<>();
        e.getBindingResult().getAllErrors().forEach(err -> {
            String getName = ((FieldError)err).getField();
            String msg = err.getDefaultMessage();
            exception.put(getName,msg);
        });
        return new ResponseEntity<Map<String, String>>(exception, HttpStatus.BAD_GATEWAY);
    }

    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<String> ResourceNotFoundExceptionMethod(ResourceNotFoundException e){
        String message = e.getMessage();
        return new ResponseEntity<>(message,HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(APIException.class)
    public ResponseEntity<String> APIExceptionMethod(APIException e){
        String message = e.getMessage();
        return new ResponseEntity<>(message,HttpStatus.BAD_REQUEST);
    }

}
