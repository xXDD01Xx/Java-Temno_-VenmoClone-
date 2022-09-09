package com.techelevator.tenmo.controller;

import com.techelevator.tenmo.dao.UserDao;
import com.techelevator.tenmo.model.User;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;

@RestController
@PreAuthorize("isAuthenticated()")
public class UserController
{
    private UserDao userDao;

    public UserController(UserDao userDao)
    {
        this.userDao = userDao;
    }

    @RequestMapping(path = "/userId_by_username", method = RequestMethod.GET)
    public int findUserIdByUsername(Principal principal) {
        return userDao.findUserIdByUsername(principal.getName());
    }

    @RequestMapping(path = "/users", method = RequestMethod.GET)
    public List<User> listAllUsers()
    {
        return userDao.findAllUsersByUsername();
    }

    @RequestMapping(path = "/user/{username}", method = RequestMethod.GET)
    public User viewIndividualUser(@PathVariable String username)
    {
        return userDao.findByUsername(username);
    }

    @RequestMapping(path = "/username_by_accountId/{accountId}", method = RequestMethod.GET)
    public String getUsernameByAccountId(@PathVariable int accountId)
    {
        return userDao.getUsernameByAccountId(accountId);
    }

    @RequestMapping(path = "/userId_by_accountId/{accountId}", method = RequestMethod.GET)
    public int getUserIdByAccountId(@PathVariable int accountId)
    {
        return userDao.getUserIdByAccountId(accountId);
    }

}
