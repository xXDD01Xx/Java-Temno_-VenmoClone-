package com.techelevator.tenmo.dao;

import com.techelevator.tenmo.Exceptions.UserDoesNotExistException;
import com.techelevator.tenmo.model.Account;
import com.techelevator.tenmo.model.User;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Component
public class JdbcUserDao implements UserDao
{

    private JdbcTemplate jdbcTemplate;

    public JdbcUserDao(JdbcTemplate jdbcTemplate)
    {
        this.jdbcTemplate = jdbcTemplate;
    }

    private final BigDecimal INITIAL_BALANCE = new BigDecimal("1000");

    @Override
    public int findUserIdByUsername(String username)
    {
        String sql = "SELECT user_id FROM tenmo_user WHERE username ILIKE ?;";

        try
        {
            Integer results = jdbcTemplate.queryForObject(sql, Integer.class, username);
            return results;
        } catch (RuntimeException e)
        {
            throw new UserDoesNotExistException();
        }
    }

    @Override
    public String getUsernameByAccountId(int accountId)
    {
        String sql = "SELECT username FROM tenmo_user JOIN account ON account.user_id = tenmo_user.user_id WHERE account_id = ?;";
        try
        {
            String results = jdbcTemplate.queryForObject(sql, String.class, accountId);
            if (!results.isEmpty())
            {
                return results;
            }
        } catch (Exception e)
        {
            return "Username for provided Account Id not found...";
        }
        return null;
    }

    @Override
    public int getUserIdByAccountId(int accountId)
    {
        String sql = "SELECT tenmo_user.user_id FROM tenmo_user JOIN account ON account.user_id = tenmo_user.user_id WHERE account_id = ?;";

        try
        {
            Integer results = jdbcTemplate.queryForObject(sql, Integer.class, accountId);
            return results;
        } catch (RuntimeException e)
        {
            throw new UserDoesNotExistException();
        }
    }

    @Override
    public List<User> findAllUsersByUsername()
    {
        List<User> users = new ArrayList<>();
        String sql = "SELECT user_id, username, password_hash FROM tenmo_user;";
        SqlRowSet results = jdbcTemplate.queryForRowSet(sql);
        while (results.next())
        {
            User user = mapRowToUser(results);
            users.add(user);
        }
        return users;
    }

    @Override
    public User findByUsername(String username) throws UsernameNotFoundException
    {
        String sql = "SELECT user_id, username, password_hash FROM tenmo_user WHERE username ILIKE ?;";
        SqlRowSet rowSet = jdbcTemplate.queryForRowSet(sql, username);
        if (rowSet.next())
        {
            return mapRowToUser(rowSet);
        }
        throw new UsernameNotFoundException("User " + username + " was not found.");
    }

    @Override
    public boolean createUser(String username, String password)
    {

        // create user
        String sql = "INSERT INTO tenmo_user (username, password_hash) VALUES (?, ?) RETURNING user_id";
        String sql2 = "INSERT INTO account (user_Id, balance) VALUES (?,?)";
        String password_hash = new BCryptPasswordEncoder().encode(password);
        Integer newUserId;
        try
        {
            newUserId = jdbcTemplate.queryForObject(sql, Integer.class, username, password_hash);
            jdbcTemplate.update(sql2, newUserId, INITIAL_BALANCE);
        } catch (DataAccessException e)
        {
            return false;
        }
        // TODO: Create the account record with initial balance
        return true;
    }

    private User mapRowToUser(SqlRowSet rs)
    {
        User user = new User();
        user.setId(rs.getInt("user_id"));
        user.setUsername(rs.getString("username"));
        user.setPassword(rs.getString("password_hash"));
        user.setActivated(true);
        user.setAuthorities("USER");
        return user;
    }
}
