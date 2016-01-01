package com.nixsolutions.ponarin.controller;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.HashMap;
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

public class EditController extends HttpServlet {
    private static final Logger logger = LoggerFactory
            .getLogger(EditController.class);
    private static final long serialVersionUID = 1L;
    private UserService userService = new JdbcUserService();

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        logger.trace("inside doPost");
        String login = request.getParameter(Constants.PARAM_LOGIN);

        System.out.println(login);
        
        if (login == null || login.length() == 0) {
            String title = "Missing parameter";
            String message = "Parameter login that identify user for editing was missed";
            request.setAttribute(Constants.ATTR_ERROR_MESSAGE_TITLE, title);
            request.setAttribute(Constants.ATTR_ERROR_MESSAGE, message);
            request.getRequestDispatcher(Constants.PAGE_ERROR).forward(request,
                    response);
            return;
        }

        User user = userService.findByLogin(login);

        if (user != null) {
            Map<String, String> userForm = getUserForm(user);
            request.setAttribute("edit", true);
            request.setAttribute(Constants.ATTR_USER_FORM, userForm);
            request.getRequestDispatcher(Constants.PAGE_CREATE_UPDATE_USER)
                    .forward(request, response);
        } else {
            String title = "Not existing user";
            String message = "User with login '" + login + "' is not exists";
            request.setAttribute(Constants.ATTR_ERROR_MESSAGE_TITLE, title);
            request.setAttribute(Constants.ATTR_ERROR_MESSAGE, message);
            request.getRequestDispatcher(Constants.PAGE_ERROR).forward(request,
                    response);
        }
    }

    private Map<String, String> getUserForm(User user) {
        Map<String, String> userForm = new HashMap<>();

        userForm.put("login", user.getLogin());
        userForm.put("password", user.getPassword());
        userForm.put("confirm_password", user.getPassword());
        userForm.put("email", user.getEmail());
        userForm.put("first_name", user.getFirstName());
        userForm.put("last_name", user.getLastName());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        userForm.put("birth_day", dateFormat.format(user.getBirthDay()));

        userForm.put("role", user.getRole().getName());

        return userForm;
    }
}
