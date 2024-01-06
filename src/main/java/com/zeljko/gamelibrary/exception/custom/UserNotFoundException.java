package com.zeljko.gamelibrary.exception.custom;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(String message){
        super(message);
    }
}
