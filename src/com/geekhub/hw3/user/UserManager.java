package com.geekhub.hw3.user;

import com.geekhub.hw3.user.exceptions.AuthException;
import com.geekhub.hw3.user.exceptions.UserNotFoundException;
import com.geekhub.hw3.user.exceptions.WrongCredentialsException;
import com.geekhub.hw3.user.exceptions.WrongPasswrodException;

import java.util.ArrayList;
import java.util.List;

public class UserManager {

    private List<User> users = new ArrayList<>();

    public User auth(String login, String password) throws AuthException {
        if ((login == null) || (password == null) || login.equals("") || password.equals("")) {
            throw new WrongCredentialsException("Login and password can't be empty!");
        }

        User authUser = new User(login, password);

        for (User user: users) {
            if (login.equals(user.getLogin())) {
                if (password.equals(user.getPassword())) {
                    return authUser;
                } else {
                    throw new WrongPasswrodException("Wrong password!");
                }
            }
        }
        throw new UserNotFoundException("User '" + login + "' not found!");
    }

    public void addUser(String login, String password) throws WrongCredentialsException{
        System.out.println(login);
        if ((login == null) || (password == null) || login.equals("") || password.equals("")) {
            throw new WrongCredentialsException("Login or password is empty!");
        }
        users.add(new User(login, password));
    }

    public static void testUserManager() {
        System.out.println("Testing user authentication ...");
        UserManager userManager = new UserManager();
        try {
            userManager.addUser("john", "doe");
            userManager.addUser("nick", "martin");
        } catch (WrongCredentialsException e) {
            e.getMessage();
        }
        try {
            System.out.println(userManager.auth("john", "doe").toString()); // OK
            System.out.println(userManager.auth(null, null).toString());    // AuthException
            System.out.println(userManager.auth("john", "123").toString()); // WrongPasswordException
            System.out.println(userManager.auth("j", "doe").toString());    // UserNotFoundException
            System.out.println(userManager.auth("", "").toString());        // WrongCredentialsException
        } catch (AuthException ae) {
            ae.getMessage();
        }
    }
}
