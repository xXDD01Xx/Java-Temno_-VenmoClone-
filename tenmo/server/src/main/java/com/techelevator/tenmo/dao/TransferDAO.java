package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Transfer;

import java.math.BigDecimal;
import java.util.List;

public interface TransferDAO
{
    Transfer getTransferByUserName(String userName);
    boolean createTransfer(Transfer transfer,String username);
    boolean updateTransfer(Transfer transfer);
    List<Transfer> getAllTransfersByUsername(String username);
    Transfer getTransferByTransferId(int transferId);
}
