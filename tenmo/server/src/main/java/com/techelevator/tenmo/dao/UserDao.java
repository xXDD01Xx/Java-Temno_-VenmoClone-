package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.model.User;

import java.util.List;

public interface UserDao {

    List<User> findAllUsersByUsername();

    User findByUsername(String username);

    int findUserIdByUsername(String username);
    String getUsernameByAccountId(int accountId);
    int getUserIdByAccountId(int accountId);
    boolean createUser(String username, String password);

}
