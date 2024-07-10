package com.revature.exception;

public class InvalidNewUserCredentials extends RuntimeException{
    public InvalidNewUserCredentials(String message){
        super(message);
    }
}
