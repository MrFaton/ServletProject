package com.nixsolutions.ponarin.controller;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nixsolutions.ponarin.Constants;
import com.nixsolutions.ponarin.dao.UserDao;
import com.nixsolutions.ponarin.dao.impl.JdbcUserDao;
import com.nixsolutions.ponarin.entity.Role;
import com.nixsolutions.ponarin.entity.User;

public class DbOperationController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDao userDao = new JdbcUserDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String action = req.getParameter("action");

        try {
            User user = buildUserByRequestParams(req);

            switch (action) {
            case "create": {
                userDao.create(user);
                break;
            }
            case "update": {
                userDao.update(user);
                break;
            }
            case "delete": {
                userDao.remove(user);
                break;
            }
            default:
                resp.sendRedirect(Constants.PAGE_ERROR_DB_UPDATE);
            }
        } catch (IllegalArgumentException badUserEx) {
            resp.sendRedirect(Constants.PAGE_ERROR_DB_UPDATE);
        }
    }

    private User buildUserByRequestParams(final HttpServletRequest req) {
        User user = new User();

        try {
            user.setId(Long.valueOf(req.getParameter("id_user").toString()));
        } catch (NumberFormatException badNum) {
            throw new IllegalArgumentException("Can't parse user's id");
        }

        user.setLogin(req.getParameter("login"));
        user.setPassword(req.getParameter("password"));
        user.setEmail(req.getParameter("email"));
        user.setFirstName(req.getParameter("first_name"));
        user.setLastName(req.getParameter("last_name"));

        String birthDayStr = req.getParameter("birth_day");
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            user.setBirthDay(dateFormat.parse(birthDayStr));
        } catch (ParseException e) {
            throw new IllegalArgumentException("Can't parse birth day");
        }

        Role role = new Role();
        try {
            role.setId(Integer.valueOf(req.getParameter("id_role").toString()));
        } catch (NumberFormatException badNum) {
            throw new IllegalArgumentException("Can't parse role's id");
        }
        role.setName(req.getParameter("role_name"));

        user.setRole(role);

        return user;
    }
}
