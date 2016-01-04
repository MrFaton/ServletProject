package com.nixsolutions.ponarin.controller;

import java.io.IOException;
import java.util.Map;

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
import com.nixsolutions.ponarin.utils.UserUtils;

public class EditController extends HttpServlet {
    private static final Logger logger = LoggerFactory
            .getLogger(EditController.class);
    private static final long serialVersionUID = 1L;
    private UserService userService = new JdbcUserService();
    private UserUtils userUtils = new UserUtils();

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        String login = request.getParameter(Constants.PARAM_LOGIN);

        User user = userService.findByLogin(login);

        if (user != null) {
            Map<String, String> userForm = userUtils.getUserForm(user);
            request.setAttribute("edit", true);
            request.setAttribute(Constants.ATTR_USER_FORM, userForm);
            request.getRequestDispatcher(Constants.PAGE_CREATE_UPDATE_USER)
                    .forward(request, response);
        } else {
            logger.debug("Not existing user was requierd");
            String title = "Not existing user";
            String message = "User with login '" + login + "' is not exists";
            request.setAttribute(Constants.ATTR_ERROR_MESSAGE_TITLE, title);
            request.setAttribute(Constants.ATTR_ERROR_MESSAGE, message);
            request.getRequestDispatcher(Constants.PAGE_ERROR).forward(request,
                    response);
        }
    }
}
