package com.ecommerce.Ecommerce.Exception;

public class APIException extends RuntimeException{
    private String message;

    public APIException(String message) {
        super(message);
    }
}
