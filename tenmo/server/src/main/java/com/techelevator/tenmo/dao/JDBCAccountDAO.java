package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.Account;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.security.Principal;

@Component
public class JDBCAccountDAO implements AccountDAO
{
    private JdbcTemplate jdbcTemplate;

    public JDBCAccountDAO(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public BigDecimal getAccountBalanceByUsername(String username)
    {
        BigDecimal balance = new BigDecimal("0.00");
       String sql = "SELECT * FROM account JOIN tenmo_user ON tenmo_user.user_id = account.user_id WHERE username = ?";
       try
       {
           SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
           Account accountBalance = null;
           if (results.next())
           {
               accountBalance = mapRowToAccount(results);
               balance = accountBalance.getBalance();
           }
       }
       catch (Exception e)
       {
          return balance;
       }
       return balance;
    }


    @Override
    public int getAccountIdByUsername(String username){

        String sql = "SELECT account_id FROM account JOIN tenmo_user " +
                "ON tenmo_user.user_id = account.user_id " +
                "WHERE username = ?;";
        Integer accountId = jdbcTemplate.queryForObject(sql, Integer.class,username);

        return accountId;
    }

    public Account mapRowToAccount(SqlRowSet results)
    {
        Account account = new Account();
        account.setAccountId(results.getInt("account_id"));
        account.setBalance(results.getBigDecimal("balance"));
        account.setUserId(results.getInt("user_id"));
        return account;
    }
}
