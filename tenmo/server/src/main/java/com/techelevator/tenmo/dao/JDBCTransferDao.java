package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.Exceptions.InvalidTransferAmountException;
import com.techelevator.tenmo.Exceptions.SameAccountException;
import com.techelevator.tenmo.Exceptions.TransferNotPossibleWithoutAccountLogin;
import com.techelevator.tenmo.model.Transfer;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JDBCTransferDao implements TransferDAO
{
    private JdbcTemplate jdbcTemplate;
    private JDBCAccountDAO jdbcAccountDAO;
    private List<Transfer> transfers = new ArrayList<>();

    public JDBCTransferDao(JdbcTemplate jdbcTemplate, JDBCAccountDAO jdbcAccountDAO)
    {
        this.jdbcAccountDAO = jdbcAccountDAO;
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public Transfer getTransferByUserName(String userName)
    {
        Transfer transfer = null;
        String sql = "SELECT * FROM transfer JOIN account " +
                "ON account.account_id = transfer.from_account " +
                "JOIN tenmo_user ON tenmo_user.user_id = account.user_id  WHERE username = ?;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, userName);

        if (results.next())
        {
            transfer = mapRowToTransfer(results);
        }

        return transfer;
    }

    @Override
    public List<Transfer> getAllTransfersByUsername(String username)
    {
        List<Transfer> allTransferForAccount = new ArrayList<>();
        String sql = "SELECT * FROM transfer JOIN account ON account.account_id = transfer.from_account " +
                "JOIN tenmo_user ON tenmo_user.user_id = account.user_id WHERE username = ?";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, username);
        while (results.next())
        {
            Transfer transfer = mapRowToTransfer(results);
            allTransferForAccount.add(transfer);
        }
        return allTransferForAccount;
    }

    @Override
    public Transfer getTransferByTransferId(int transferId)
    {
        Transfer transfer = null;
        String sql = "SELECT * FROM transfer WHERE transfer_id = ?;";

        SqlRowSet results = jdbcTemplate.queryForRowSet(sql, transferId);

        if (results.next())
        {
            transfer = mapRowToTransfer(results);
        }
        return transfer;
    }

    @Override
    public boolean createTransfer(Transfer transfer, String username1)      //create separate class TODO
    {
        boolean transferSuccessful = false;
        BigDecimal fromAccount;
        fromAccount = jdbcAccountDAO.getAccountBalanceByUsername(username1);
        String sql = "INSERT INTO transfer (transfer_status, from_account, to_account, amount) VALUES (?,?,?,?)";

//add comments TODO
            if (transfer.getFromAccount() == transfer.getToAccount())
            {
                throw new SameAccountException(username1);
            }
            else if (jdbcAccountDAO.getAccountIdByUsername(username1) != transfer.getFromAccount()){
                throw new TransferNotPossibleWithoutAccountLogin();
            }
            else if (transfer.getAmount().compareTo(fromAccount) == 1 || transfer.getAmount().compareTo(new BigDecimal(0)) <= 0)
            {
                throw new InvalidTransferAmountException(fromAccount);
            }
                else
                {
                    jdbcTemplate.update(sql, transfer.getTransferStatus(), transfer.getFromAccount(),
                            transfer.getToAccount(), transfer.getAmount());
                    updateTransfer(transfer);
                    transferSuccessful = true;
                }

        return transferSuccessful;
    }

    public boolean updateTransfer(Transfer transfer)
    {
        String sql = "UPDATE account SET balance = balance - " + transfer.getAmount()
                + "WHERE account_id = " + transfer.getFromAccount();
        jdbcTemplate.update(sql);

        String sql2 = "UPDATE account SET balance = balance + " + transfer.getAmount()
                + "WHERE account_id = " + transfer.getToAccount();
        jdbcTemplate.update(sql2);

        return true;
    }


    public Transfer mapRowToTransfer(SqlRowSet results)
    {
        Transfer transfer = new Transfer();
        transfer.setTransferId(results.getInt("transfer_id"));
        transfer.setTransferStatusId(results.getString("transfer_status"));
        transfer.setFromAccount(results.getInt("from_account"));
        transfer.setToAccount(results.getInt("to_account"));
        transfer.setAmount(results.getBigDecimal("amount"));
        return transfer;
    }
}
