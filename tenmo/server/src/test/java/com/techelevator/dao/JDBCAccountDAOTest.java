package com.techelevator.dao;

import com.techelevator.tenmo.dao.JDBCAccountDAO;
import com.techelevator.tenmo.model.User;
import com.techelevator.tenmo.dao.JdbcUserDao;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;

import static org.junit.Assert.*;

public class JDBCAccountDAOTest extends BaseDaoTests
{
    private JDBCAccountDAO accountSut;
    private JdbcUserDao userSut;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        accountSut = new JDBCAccountDAO(jdbcTemplate);
        userSut = new JdbcUserDao(jdbcTemplate);
    }

    @Test
    public void getAccountBalanceByUsername()
    {
        boolean userCreated = userSut.createUser("TEST_USER","test_password");
        BigDecimal expectedBalance = new BigDecimal("1000.00");
        BigDecimal userBalance =  accountSut.getAccountBalanceByUsername("TEST_USER");
        Assert.assertEquals(expectedBalance, userBalance);
    }

    @Test
    public void getAccountBalanceByUsername2()
    {
        BigDecimal expectedBalance = new BigDecimal("0.00");
        BigDecimal userBalance =  accountSut.getAccountBalanceByUsername("INVALID_USER_NOT_REGISTERED");
        Assert.assertEquals(expectedBalance, userBalance);
    }
}