package com.nixsolutions.ponarin.service;

import java.util.List;
import java.util.Map;

import com.nixsolutions.ponarin.entity.User;

public interface UserService {
    void create(User user);

    void create(Map<String, String> userForm);

    void update(User user);

    void update(Map<String, String> userForm);

    void remove(User user);

    void remove(String login);

    List<User> findAll();

    User findByLogin(String login);

    User findByEmail(String email);
}
