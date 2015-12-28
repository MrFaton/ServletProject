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
import com.nixsolutions.ponarin.entity.Role;
import com.nixsolutions.ponarin.entity.User;

public class LoginResolveController extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private UserDao userDao = new JdbcUserDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        
        if (session == null) {
            resp.sendRedirect(Constants.PAGE_LOGIN);
        }
        
        User sessionUser = (User) session.getAttribute(Constants.ATTR_USER);
        if (sessionUser == null) {
            resp.sendRedirect(Constants.PAGE_LOGIN);
        }
        
        User dbUser = userDao.findByLogin(sessionUser.getLogin());
        if (!sessionUser.equals(dbUser)) {
            sessionUser = dbUser;
        }
        
        Role role = sessionUser.getRole();
        if (role.getName().equalsIgnoreCase(Constants.ROLE_ADMIN)) {
            req.getRequestDispatcher(Constants.PAGE_ADMIN).forward(req, resp);
        } else {
            req.getRequestDispatcher(Constants.PAGE_USER).forward(req, resp);
        }
        
//        if (session != null) {
//            User sessionUser = (User) session.getAttribute(Constants.ATTR_USER);
//            if (sessionUser != null) {
//                User dbUser = userDao.findByLogin(sessionUser.getLogin());
//                if (!sessionUser.equals(dbUser)) {
//                    sessionUser = dbUser;
//                }
//                Role role = sessionUser.getRole();
//                if (role.getName().equalsIgnoreCase(Constants.ROLE_ADMIN)) {
//                    req.getRequestDispatcher(Constants.PAGE_ADMIN).forward(req, resp);
//                } else {
//                    req.getRequestDispatcher(Constants.PAGE_USER).forward(req, resp);
//                }
//            } else {
//                resp.sendRedirect(Constants.PAGE_LOGIN);
//            }
//        } else {
//            resp.sendRedirect(Constants.PAGE_LOGIN);
//        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String passedLogin = req.getParameter("login");
        String passedPassword = req.getParameter("password");

        User user = userDao.findByLogin(passedLogin);

        if (user != null && user.getPassword().equals(passedPassword)) {
            HttpSession session = req.getSession();
            session.setAttribute(Constants.ATTR_USER, user);
            
            if (user.getRole().getName().equals(Constants.ROLE_ADMIN)) {
                req.getRequestDispatcher(Constants.PAGE_ADMIN).forward(req, resp);
            } else {
                req.getRequestDispatcher(Constants.PAGE_USER).forward(req, resp);
            }
        } else {
            resp.sendRedirect(Constants.PAGE_USER_ERROR);
        }
    }
    
}
