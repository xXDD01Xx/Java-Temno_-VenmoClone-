package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;

import java.math.BigDecimal;

public interface AccountDAO
{
    BigDecimal getAccountBalanceByUsername(String username);
    int getAccountIdByUsername(String username);

//     For Testing---
//     BigDecimal getAccountBalanceByUserId(int userId);
}
