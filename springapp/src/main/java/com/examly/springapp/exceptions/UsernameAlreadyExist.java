package com.examly.springapp.exceptions;

public class UsernameAlreadyExist extends RuntimeException{
    public UsernameAlreadyExist(String message){
        super(message);
    }
}
