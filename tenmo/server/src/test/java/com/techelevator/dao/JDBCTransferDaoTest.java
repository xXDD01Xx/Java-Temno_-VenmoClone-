package com.techelevator.dao;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.techelevator.tenmo.Exceptions.SameAccountException;
import com.techelevator.tenmo.dao.JDBCAccountDAO;
import com.techelevator.tenmo.dao.JDBCTransferDao;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.Transfer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

public class JDBCTransferDaoTest extends BaseDaoTests {
    private JDBCTransferDao transferSut;
    private JDBCAccountDAO jdbcAccountDAO;
    private JdbcUserDao userSut;


    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        jdbcAccountDAO = new JDBCAccountDAO(jdbcTemplate);
        transferSut = new JDBCTransferDao(jdbcTemplate, jdbcAccountDAO);
        userSut = new JdbcUserDao(jdbcTemplate);
        boolean userCreated = userSut.createUser("TEST_USER", "test_password");
        boolean userCreatedTransferTo = userSut.createUser("TEST_USER_TRANSFER_TO", "test_password1");
    }



    @Test
    public void getTransferByUserName() {
      //  boolean userCreated = userSut.create("TEST_USER", "test_password");
    //    boolean userCreatedTransferTo = userSut.create("TEST_USER_TRANSFER_TO", "test_password1");
        Transfer transfer = new Transfer();
        transfer.setTransferId(3001);
        transfer.setTransferStatusId("ACCEPTED");
        transfer.setAmount(new BigDecimal("500.00"));
        transfer.setFromAccount(2001);
        transfer.setToAccount(2002);
        transferSut.createTransfer(transfer, "TEST_USER");
        // transferSut.getTransferByUserName("TEST_USER");
        Assert.assertEquals(transfer.getTransferId(), transferSut.getTransferByUserName("TEST_USER").getTransferId());
        Assert.assertEquals(transfer.getAmount(), transferSut.getTransferByUserName("TEST_USER").getAmount());
        Assert.assertEquals(transfer.getFromAccount(), transferSut.getTransferByUserName("TEST_USER").getFromAccount());
        Assert.assertEquals(transfer.getToAccount(), transferSut.getTransferByUserName("TEST_USER").getToAccount());
        Assert.assertEquals(transfer.getTransferStatus(), transferSut.getTransferByUserName("TEST_USER").getTransferStatus());
    }

    @Test(expected = SameAccountException.class)
    public void noTransferPossibleToSameAccountException() {
//        boolean userCreated = userSut.create("TEST_USER", "test_password");
//        boolean userCreatedTransferTo = userSut.create("TEST_USER_TRANSFER_TO", "test_password1");
        Transfer transfer = new Transfer();
        transfer.setTransferId(3001);
        transfer.setTransferStatusId("ACCEPTED");
        transfer.setAmount(new BigDecimal("500.00"));
        transfer.setFromAccount(2001);
        transfer.setToAccount(2001);
        transferSut.createTransfer(transfer, "TEST_USER");
    }

    @Test
    public void getAllTransfersByUsername() {
        //Making transfers so we can test this method, l create 3 transfer
//        boolean userCreated = userSut.create("TEST_USER", "test_password");
//        boolean userCreatedTransferTo = userSut.create("TEST_USER_TRANSFER_TO", "test_password1");
//        //creating the transfer object, so we can store the details of our transfer
        Transfer transfer = new Transfer();
        // transfer.setTransferId(3001);
        transfer.setTransferId(3001);
        transfer.setTransferStatusId("ACCEPTED");
        transfer.setAmount(new BigDecimal("500.00"));
        transfer.setFromAccount(2001);
        transfer.setToAccount(2002);
        //firstTransfer
        transferSut.createTransfer(transfer, "TEST_USER");
        transfer.setTransferId(3002);
        transfer.setAmount(new BigDecimal("100"));
        //SecondTransfer
        transferSut.createTransfer(transfer, "TEST_USER");
        transfer.setTransferId(3003);
        transfer.setAmount(new BigDecimal("200"));
        //ThirdTransfer
        transferSut.createTransfer(transfer, "TEST_USER");
        //Here im comparing the size of the list of our getAllTransferByUsername method, we made 3 transfers
        // so the size is going to be 3.
        Assert.assertEquals(3, transferSut.getAllTransfersByUsername("TEST_USER").size());
    }

    @Test
    public void noTransferMadeByUser() {
        //In this test im making sure that the user doesn't have a transfer
//        boolean userCreated = userSut.create("TEST_USER", "test_password");
//        boolean userCreatedTransferTo = userSut.create("TEST_USER_TRANSFER_TO", "test_password1");

        assertEquals(0, transferSut.getAllTransfersByUsername("TEST_USER").size());
    }

    @Test
    public void getTransferByTransferId() {
//        boolean userCreated = userSut.create("TEST_USER", "test_password");
//        boolean userCreatedTransferTo = userSut.create("TEST_USER_TRANSFER_TO", "test_password1");
        Transfer transfer = new Transfer();
        transfer.setTransferId(3001);
        transfer.setTransferStatusId("ACCEPTED");
        transfer.setAmount(new BigDecimal("500.00"));
        transfer.setFromAccount(2001);
        transfer.setToAccount(2002);
        transferSut.createTransfer(transfer, "TEST_USER");

        Assert.assertEquals(transfer.getTransferId(), transferSut.getTransferByUserName("TEST_USER").getTransferId());
    }

    @Test
    public void noTransferFoundThrowNull() {
//        boolean userCreated = userSut.create("TEST_USER", "test_password");
//        boolean userCreatedTransferTo = userSut.create("TEST_USER_TRANSFER_TO", "test_password1");

       assertNull(transferSut.getTransferByTransferId(3001));
    }

    @Test
    public void createTransfer() {
//
    }
}
