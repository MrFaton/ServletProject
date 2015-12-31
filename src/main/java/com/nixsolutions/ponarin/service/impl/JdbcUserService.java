package com.nixsolutions.ponarin.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

import com.nixsolutions.ponarin.dao.RoleDao;
import com.nixsolutions.ponarin.dao.UserDao;
import com.nixsolutions.ponarin.dao.impl.JdbcRoleDao;
import com.nixsolutions.ponarin.dao.impl.JdbcUserDao;
import com.nixsolutions.ponarin.entity.Role;
import com.nixsolutions.ponarin.entity.User;
import com.nixsolutions.ponarin.service.UserService;
import com.nixsolutions.ponarin.validator.UserFormValidator;

public class JdbcUserService implements UserService{
    private UserDao userDao = new JdbcUserDao();
    private RoleDao roleDao = new JdbcRoleDao();
    private UserFormValidator userFormValidator = new UserFormValidator();

    
    @Override
    public void create(User user) {
        userDao.create(user);
    }

    @Override
    public void create(Map<String, String> userForm) {
        userFormValidator.validate(userForm);
        User user = getUserByForm(userForm);
        userDao.create(user);
    }

    @Override
    public void update(User user) {
        userDao.update(user);
    }

    @Override
    public void update(Map<String, String> userForm) {
        userFormValidator.validate(userForm);
        User user = getUserByForm(userForm);
        userDao.update(user);
    }

    @Override
    public void remove(User user) {
        userDao.remove(user);
    }

    @Override
    public void remove(String login) {
        if (login == null || login.length() == 0) {
            throw new IllegalArgumentException("Login is blank");
        }
        User user = userDao.findByLogin(login);
        if (user != null) {
            userDao.remove(user);
        }
    }

    @Override
    public List<User> findAll() {
        return userDao.findAll();
    }

    @Override
    public User findByLogin(String login) {
        return userDao.findByLogin(login);
    }

    @Override
    public User findByEmail(String email) {
        return userDao.findByEmail(email);
    }

    protected User getUserByForm(Map<String, String> userForm) {
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
            throw new IllegalArgumentException("Birthday date is incorrect. You shoud use pattern like: dd-MM-yyyy");
        }
        
        String roleName = userForm.get("role").toString();
        Role role = new Role();
        try {
            role.setName(roleName);
            role.setId(roleDao.findByName(roleName).getId());
        } catch (NumberFormatException badNum) {
            throw new IllegalArgumentException("Role name is incorrect");
        }

        user.setRole(role);

        return user;
    }
}
