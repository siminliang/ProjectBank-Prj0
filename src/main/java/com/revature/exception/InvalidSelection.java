package com.revature.exception;

public class InvalidSelection extends RuntimeException{
    public InvalidSelection(String message){
        super(message);
    }
}
