package com.lexsav.dao;

import com.lexsav.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserDAO {

    private final List<User> users = new ArrayList<>();

    public int getSize() {
        return users.size();
    }

    public String getIdByUsername(String username) {
        String id = null;

        for (User user : users) {
            if (user.getUsername().equals(username)){
                id = user.getId();
                break;
            }
        }

        return id;
    }

    public String getUsernameById(String id) {
        String username = null;

        for (User user : users) {
            if (user.getId().equals(id)){
                username = user.getUsername();
                break;
            }
        }

        return username;
    }

    public boolean add(final User user) {
        return users.add(user);
    }

    public boolean checkUserById(final String id) {

        for (User user : users) {
            if (user.getId().equals(id)) {
                return true;
            }
        }

        return false;
    }

    public boolean checkUserByUsername(final String username) {

        for (User user : users) {
            if (user.getUsername().equals(username)) {
                return true;
            }
        }
        return false;
    }

    public boolean checkUserByUsernameAndPassword(final String username, final String password) {

        for (User user : users) {
            if (user.getUsername().equals(username) &&
                    user.getPassword().equals(password))
            {
                return true;
            }
        }
        return false;
    }
}