package com.nixsolutions.ponarin.controller;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nixsolutions.ponarin.Constants;
import com.nixsolutions.ponarin.service.UserService;
import com.nixsolutions.ponarin.service.impl.JdbcUserService;

public class DbOperationController extends HttpServlet {
    private static final Logger logger = LoggerFactory
            .getLogger(DbOperationController.class);
    private static final long serialVersionUID = 1L;
    private UserService userService = new JdbcUserService();

    @Override
    protected void doPost(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        logger.trace("inside doPost");
        String action = request.getParameter("action");

        Map<String, String> userForm = normaliseParameterMap(
                request.getParameterMap());

        logger.trace("action for handling is " + action);

        switch (action) {
        case "create": {
            try {
                userService.create(userForm);
            } catch (Exception badArg) {
                request.setAttribute(Constants.ATTR_USER_FORM, userForm);
                request.setAttribute(Constants.ATTR_ERROR_MESSAGE,
                        badArg.getMessage());
                request.getRequestDispatcher(Constants.PAGE_CREATE_UPDATE_USER)
                        .forward(request, response);
            }

            break;
        }
        case "update": {
            // userDao.update(user);
            break;
        }
        case "delete": {
            // userDao.remove(user);
            break;
        }
        default:
            // response.sendRedirect(Constants.PAGE_ERROR_DB_UPDATE);
        }
    }

    private Map<String, String> normaliseParameterMap(
            Map<String, String[]> rowMap) {
        Map<String, String> normalizedMap = new HashMap<>();

        for (Map.Entry<String, String[]> entry : rowMap.entrySet()) {
            normalizedMap.put(entry.getKey(), entry.getValue()[0]);
        }

        return normalizedMap;
    }
}
