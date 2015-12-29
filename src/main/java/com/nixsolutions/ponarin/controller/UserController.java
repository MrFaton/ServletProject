package com.nixsolutions.ponarin.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.nixsolutions.ponarin.Constants;
import com.nixsolutions.ponarin.dao.UserDao;
import com.nixsolutions.ponarin.dao.impl.JdbcUserDao;
import com.nixsolutions.ponarin.entity.User;

public class UserController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDao userDao = new JdbcUserDao();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String passedLogin = req.getParameter(Constants.PARAM_LOGIN);
        String passedPassword = req.getParameter(Constants.PARAM_PASSWORD);
        
        User user;
        
        try {
            user = userDao.findByLogin(passedLogin);
        } catch (IllegalArgumentException badArgument) {
            resp.sendRedirect(Constants.PAGE_USER_NOT_FOUND);
            return;
        }
        

        if (user != null && user.getPassword().equals(passedPassword)) {
            HttpSession session = req.getSession();
            session.setAttribute(Constants.ATTR_USER, user);
            String roleName = user.getRole().getName();

            if (roleName.equalsIgnoreCase(Constants.ROLE_ADMIN)) {
                req.getRequestDispatcher(Constants.PAGE_ADMIN).forward(req,
                        resp);
            } else if (roleName.equalsIgnoreCase(Constants.ROLE_USER)) {
                req.getRequestDispatcher(Constants.PAGE_USER).forward(req,
                        resp);
            } else {
                resp.sendRedirect(Constants.PAGE_ERROR);
            }
        } else {
            resp.sendRedirect(Constants.PAGE_USER_NOT_FOUND);
        }
    }
}
