package com.nixsolutions.ponarin.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.nixsolutions.ponarin.Constants;
import com.nixsolutions.ponarin.dao.UserDao;
import com.nixsolutions.ponarin.dao.impl.JdbcUserDao;
import com.nixsolutions.ponarin.entity.User;

public class LoginResolveController extends HttpServlet {
    private UserDao userDao = new JdbcUserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        Object userObject = session.getAttribute(Constants.ATTRIBUTE_USER);

        if (userObject != null || userObject instanceof User) {
            User user = (User) userObject;
            user = userDao.findByLogin(user.getLogin());

            if (user != null) {
                String roleName = user.getRole().getName();
                
                if (roleName.equalsIgnoreCase(Constants.ROLE_ADMIN)) {
                    req.getRequestDispatcher(Constants.PAGE_ADMIN).forward(req, resp);
                } else if (roleName.equalsIgnoreCase(Constants.ROLE_USER)) {
                    req.getRequestDispatcher(Constants.PAGE_USER).forward(req, resp);
                }
                
            } else {
                session.removeAttribute(Constants.ATTRIBUTE_USER);
                req.setAttribute(Constants.ATTRIBUTE_USER, user);
                req.getRequestDispatcher(Constants.PAGE_USER_ERROR).forward(req,
                        resp);
            }

        } else {
            req.getRequestDispatcher(Constants.PAGE_LOGIN).forward(req, resp);
        }
    }
}
