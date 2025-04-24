package com.examly.springapp.exceptions;

public class DuplicateOrderException extends RuntimeException{
    public DuplicateOrderException(String message){
        super(message);
    }

}
