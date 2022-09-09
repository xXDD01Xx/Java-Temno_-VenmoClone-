package com.techelevator.tenmo.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.security.Principal;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class SameAccountException extends RuntimeException
{
    public SameAccountException( String username)
    {
        super("INVALID TRANSACTION: " + username + "cannot initiate transfer within one account, please include secondary account.");
    }
}
