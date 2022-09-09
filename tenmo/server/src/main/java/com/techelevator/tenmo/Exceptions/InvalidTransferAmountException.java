package com.techelevator.tenmo.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.math.BigDecimal;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidTransferAmountException extends RuntimeException
{
    public InvalidTransferAmountException(BigDecimal fromAccount)
    {
        super("INVALID TRANSACTION: Transfer amount not valid.");
    }
}
