package com.nixsolutions.ponarin.validator;

import com.nixsolutions.ponarin.entity.User;

public class UserValidator {
    public static void validate(final User user) {
        if (user.getLogin().length() == 0) {
            throw new IllegalArgumentException("Login is empty");
        }

        if (user.getPassword().length() == 0) {
            throw new IllegalArgumentException("Password is empty");
        }

        if (user.getEmail().length() == 0) {
            throw new IllegalArgumentException("Email is empty");
        }

        RoleValidator.validate(user.getRole());
    }
}
