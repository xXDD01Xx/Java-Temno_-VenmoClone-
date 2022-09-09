package com.techelevator.tenmo.Exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class TransferNotPossibleWithoutAccountLogin extends RuntimeException{

    public TransferNotPossibleWithoutAccountLogin()
    {
        super("INVALID TRANSACTION: Please login to valid account to process a transfer.");
    }
}
