package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.AccountDAO;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.math.BigDecimal;
import java.security.Principal;

@RestController
@PreAuthorize("isAuthenticated()")
public class AccountController
{
    private AccountDAO accountDAO;

    public AccountController(AccountDAO accountDAO)
    {
        this.accountDAO = accountDAO;
    }

    @RequestMapping(path = "/account/balance", method = RequestMethod.GET)
    public BigDecimal getBalanceByUsername( Principal principal)
    {
        return accountDAO.getAccountBalanceByUsername(principal.getName());
    }


}
