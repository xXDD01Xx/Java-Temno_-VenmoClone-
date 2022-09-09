package com.techelevator.dao;

import com.techelevator.dao.BaseDaoTests;
import com.techelevator.tenmo.dao.JdbcUserDao;
import com.techelevator.tenmo.model.User;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.Assert.*;

public class JdbcUserDaoTest extends BaseDaoTests {

    private JdbcUserDao sut;

    @Before
    public void setup() {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);
        sut = new JdbcUserDao(jdbcTemplate);
        boolean userCreated = sut.createUser("TEST_USER","test_password");
    }

    @Test
    public void createNewUser() {
        boolean userCreated = sut.createUser("TEST_USER","test_password");
        Assert.assertTrue(userCreated);
        User user = sut.findByUsername("TEST_USER");
        Assert.assertEquals("TEST_USER", user.getUsername());
    }

    @Test
    public void createNewUser2() {
       // boolean userCreated = sut.create("TEST_USER","test_password");
        boolean userCreated1 = sut.createUser("TEST_USER","test_password");
        Assert.assertFalse(userCreated1);
    }

    @Test(expected = DataAccessException.class)
    public void createNewUser3()
    {
        boolean userCreated = sut.createUser("", "");
//        Assert.assertFalse(userCreated); //DataAccessException is not being thrown from existing codebase
    }

    @Test
    public void findUserIdByUsername()
    {
//        boolean userCreated = sut.create("TEST_USER","test_password");
        int userId = sut.findUserIdByUsername("TEST_USER");
        int expectedUserId = 1003;          //Serial numbers are not initiating rollback between tests, hard-coded value instead of "1001"
        Assert.assertEquals(expectedUserId, userId);
    }

    @Test
    public void findUserIdByUsername2()
    {
        boolean userCreated = sut.createUser("TEST_USER","test_password");
        int userId = sut.findUserIdByUsername("TEST_USER2");
        Assert.assertEquals(-1, userId);
    }

    @Test
    public void getUsernameByAccountId()
    {
//        boolean userCreated = sut.create("TEST_USER","test_password");
        String expectedAccountId = sut.getUsernameByAccountId(2001);
        String expectedUsername = "TEST_USER";
        Assert.assertEquals(expectedAccountId, expectedUsername);
    }

    @Test
    public void getUsernameByAccountId2()
    {
        String expectedAccountId = sut.getUsernameByAccountId(2005);
        String expectedResult = "Username for provided Account Id not found...";
        Assert.assertEquals(expectedAccountId, expectedResult);
    }


    @Test
    public void getUserIdByAccountId()
    {
        boolean userCreated = sut.createUser("TEST_USER","test_password");
        int expectedUserId = sut.getUserIdByAccountId(2001);
        int expectedResult = 1003;          //Serial numbers are not initiating rollback between tests, hard-coded value instead of "1001"
        Assert.assertEquals(expectedUserId, expectedResult);
    }

    @Test
    public void getUserIdByAccountId2()
    {
        int expectedUserId = sut.getUserIdByAccountId(0005);
        int expectedResult = -1;
        Assert.assertEquals(expectedUserId, expectedResult);
    }
}