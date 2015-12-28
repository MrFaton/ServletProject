package com.nixsolutions.ponarin.validator;

import com.nixsolutions.ponarin.entity.Role;

public class RoleValidator {
    public void validate(final Role role) {
        if (role.getName().length() == 0) {
            throw new IllegalArgumentException("Role's name is empty");
        }
    }
}
