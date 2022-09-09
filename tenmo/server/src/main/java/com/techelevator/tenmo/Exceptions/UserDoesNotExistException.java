package com.techelevator.tenmo.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class UserDoesNotExistException extends RuntimeException{

    public UserDoesNotExistException(){
        super("INVALID LOGIN: The credentials for the user input do not exist. Please try again.");
    }
}
