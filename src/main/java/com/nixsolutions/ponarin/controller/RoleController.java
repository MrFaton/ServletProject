package com.nixsolutions.ponarin.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nixsolutions.ponarin.Constants;
import com.nixsolutions.ponarin.entity.Role;
import com.nixsolutions.ponarin.entity.User;

public class RoleController extends HttpServlet {
    private static final Logger logger = LoggerFactory
            .getLogger(RoleController.class);
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        logger.trace("inside doGet");
        User user = (User) request.getSession()
                .getAttribute(Constants.ATTR_USER);
        Role role = user.getRole();

        if (role.getName().equalsIgnoreCase(Constants.ROLE_ADMIN)) {
            logger.trace("forward to admin page");
            request.getRequestDispatcher(Constants.PAGE_ADMIN).forward(request,
                    response);
        } else {
            logger.trace("forward to user page");
            request.getRequestDispatcher(Constants.PAGE_USER).forward(request,
                    response);
        }
    }
}