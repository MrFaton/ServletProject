package com.nixsolutions.ponarin.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
import java.util.Map;

import com.nixsolutions.ponarin.entity.Role;
import com.nixsolutions.ponarin.entity.User;

public class UserUtils {
    public User getUserByForm(Map<String, String> userForm, Integer roleId) {
        User user = new User();

        user.setLogin(userForm.get("login"));
        user.setPassword(userForm.get("password"));
        user.setEmail(userForm.get("email"));
        user.setFirstName(userForm.get("first_name"));
        user.setLastName(userForm.get("last_name"));

        String birthDayStr = userForm.get("birth_day");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        try {
            user.setBirthDay(dateFormat.parse(birthDayStr));
        } catch (ParseException e) {
            throw new IllegalArgumentException(
                    "Birthday date is incorrect. You shoud use pattern like: dd-MM-yyyy");
        }

        String roleName = userForm.get("role").toString();
        Role role = new Role();
        role.setName(roleName);
        role.setId(roleId);

        user.setRole(role);

        return user;
    }

    public Map<String, String> getUserForm(User user) {
        Map<String, String> userForm = new HashMap<>();

        userForm.put("login", user.getLogin());
        userForm.put("password", user.getPassword());
        userForm.put("confirm_password", user.getPassword());
        userForm.put("email", user.getEmail());
        userForm.put("first_name", user.getFirstName());
        userForm.put("last_name", user.getLastName());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        userForm.put("birth_day", dateFormat.format(user.getBirthDay()));

        userForm.put("role", user.getRole().getName());

        return userForm;
    }
}
