package com.techelevator.tenmo.model;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import java.math.BigDecimal;

public class Transfer
{
    private int transferId;
    private String transferStatus;
    //@JsonProperty("from_account")
    @NotBlank(message = "Transfers must have a sender!")
    private int fromAccount;
    //@JsonProperty("to_account")
    @NotBlank(message = "Transfers must have a recipient!")
    private int toAccount;
    @DecimalMin(value ="0.01", message = "Transfers minimum is $0.01")
    private BigDecimal amount;

    public Transfer()
    {
    }


    public Transfer(int transferId, String transferStatus, int fromAccount, int toAccount, BigDecimal amount)
    {
        this.transferId = transferId;
        this.transferStatus = transferStatus;
        this.fromAccount = fromAccount;
        this.toAccount = toAccount;
        this.amount = amount;
    }


    public String getTransferStatus()
    {
        return transferStatus;
    }

    public void setTransferStatusId(String transferStatus)
    {
        this.transferStatus = transferStatus;
    }

    public int getFromAccount()
    {
        return fromAccount;
    }

    public void setFromAccount(int fromAccount)
    {
        this.fromAccount = fromAccount;
    }

    public int getToAccount()
    {
        return toAccount;
    }

    public void setToAccount(int toAccount)
    {
        this.toAccount = toAccount;
    }

    public BigDecimal getAmount()
    {
        return amount;
    }

    public void setAmount(BigDecimal amount)
    {
        this.amount = amount;
    }

    public int getTransferId() {
        return transferId;
    }

    public void setTransferId(int transferId) {
        this.transferId = transferId;
    }
}
