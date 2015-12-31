package com.nixsolutions.ponarin.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nixsolutions.ponarin.Constants;
import com.nixsolutions.ponarin.entity.User;
import com.nixsolutions.ponarin.service.UserService;
import com.nixsolutions.ponarin.service.impl.JdbcUserService;

public class LoginController extends HttpServlet {
    private static final Logger logger = LoggerFactory
            .getLogger(LoginController.class);
    private static final long serialVersionUID = 1L;
    private UserService userService = new JdbcUserService();

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        logger.trace("inside doPost");
        String passedLogin = request.getParameter("login");
        String passedPassword = request.getParameter("password");

        User user = userService.findByLogin(passedLogin);

        if (user != null && user.getPassword().equals(passedPassword)) {
            request.getSession().setAttribute(Constants.ATTR_USER, user);
            response.sendRedirect(
                    request.getContextPath() + Constants.PAGE_MAIN);
        } else {
            String title = "Authorisation error";
            String message = "User with login '" + passedLogin
                    + "' not found or password was incorrect";
            request.setAttribute(Constants.ATTR_ERROR_MESSAGE_TITLE, title);
            request.setAttribute(Constants.ATTR_ERROR_MESSAGE, message);
            request.getRequestDispatcher(Constants.PAGE_ERROR).forward(request,
                    response);
        }
    }
}
