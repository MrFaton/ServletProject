package com.nixsolutions.ponarin.validator;

import java.util.Calendar;
import java.util.Map;

import com.nixsolutions.ponarin.dao.UserDao;
import com.nixsolutions.ponarin.dao.impl.JdbcUserDao;

public class UserFormValidator {
    private UserDao userDao = new JdbcUserDao();

    public void validate(Map<String, String> userForm) {
        String password = userForm.get("password");
        String cinfirmPassword = userForm.get("confirm_password");

        if (password == null || password.length() == 0) {
            throw new IllegalArgumentException("Password is blank");
        }

        if (cinfirmPassword == null || cinfirmPassword.length() == 0) {
            throw new IllegalArgumentException("Cinfirm password is blank");
        }

        if (!password.equals(cinfirmPassword)) {
            throw new IllegalArgumentException(
                    "Password don't match confirm password");
        }

        String login = userForm.get("login");
        if (login == null || login.length() == 0) {
            throw new IllegalArgumentException("Login is blank");
        }
        if (userDao.findByLogin(login) != null) {
            throw new IllegalArgumentException(
                    "User with login '" + login + "' already exists");
        }

        String email = userForm.get("email");
        if (email == null || email.length() == 0) {
            throw new IllegalArgumentException("Email is blank");
        }
        if (userDao.findByEmail(email) != null) {
            throw new IllegalArgumentException(
                    "User with email '" + email + "' already exists");
        }

        String firstName = userForm.get("first_name");
        if (firstName == null || firstName.length() == 0) {
            throw new IllegalArgumentException("First name is blank");
        }

        String lastName = userForm.get("last_name");
        if (lastName == null || lastName.length() == 0) {
            throw new IllegalArgumentException("Last name is blank");
        }

        String birthDayStr = userForm.get("birth_day");
        if (birthDayStr == null || birthDayStr.length() == 0) {
            throw new IllegalArgumentException("Birthday is blank");
        }
        String[] birthdayTokens = birthDayStr.split("-");
        if (birthdayTokens.length != 3) {
            throw new IllegalArgumentException(
                    "Birthday date is incorrect. You shoud use pattern like: dd-MM-yyyy");
        }
        Calendar calendar = Calendar.getInstance();
        try {
            if (Integer.valueOf(birthdayTokens[0]) > 31) {
                throw new IllegalArgumentException(
                        "Birthday day is more than 31");
            }
            if (Integer.valueOf(birthdayTokens[1]) > 12) {
                throw new IllegalArgumentException(
                        "Birthday month is more than 12");
            }
            if (Integer.valueOf(birthdayTokens[2]) > calendar
                    .get(Calendar.YEAR)) {
                throw new IllegalArgumentException(
                        "You can't use birthday year from futute");
            }
        } catch (NumberFormatException badNumber) {
            throw new IllegalArgumentException(
                    "Birthday date is incorrect. You shoud use pattern like: dd-MM-yyyy");
        }

    }
}
