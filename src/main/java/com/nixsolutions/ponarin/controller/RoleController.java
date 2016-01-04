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
        User user = (User) request.getSession()
                .getAttribute(Constants.ATTR_USER);

        if (user == null) {
            response.sendRedirect(
                    request.getContextPath() + Constants.PAGE_LOGIN);
            return;
        }

        Role role = user.getRole();

        if (role.getName().equalsIgnoreCase(Constants.ROLE_ADMIN)) {
            logger.trace("forward to admin page");
            request.getRequestDispatcher(Constants.PAGE_ADMIN).forward(request,
                    response);
        } else if (role.getName().equalsIgnoreCase(Constants.ROLE_USER)) {
            logger.trace("forward to user page");
            request.getRequestDispatcher(Constants.PAGE_USER).forward(request,
                    response);
        } else {
            logger.debug("unknown role: " + role.getName());
            String title = "Unknown role";
            String message = "Passed role is unknown: " + role.getName();
            request.setAttribute(Constants.ATTR_ERROR_MESSAGE_TITLE, title);
            request.setAttribute(Constants.ATTR_ERROR_MESSAGE, message);
            request.getRequestDispatcher(Constants.PAGE_ERROR).forward(request,
                    response);
        }
    }
}
