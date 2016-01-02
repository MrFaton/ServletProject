package com.nixsolutions.ponarin.controller;

import java.io.IOException;
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

        @SuppressWarnings("unchecked")
        Map<String, String> userForm = normaliseParameterMap(
                request.getParameterMap());

        try {
            switch (action) {
            case "create": {
                userService.create(userForm);
                break;
            }
            case "update": {
                try {
                    userService.update(userForm);
                } catch (IllegalArgumentException argEx) {
                    request.setAttribute("edit", true);
                    throw argEx;
                }
                break;
            }
            case "delete": {
                userService.remove(request.getParameter("login"));
                break;
            }
            default:
                String title = "Bad DB operation action";
                String message = "The system doesn't have handler for action "
                        + action;
                request.setAttribute(Constants.ATTR_ERROR_MESSAGE_TITLE, title);
                request.setAttribute(Constants.ATTR_ERROR_MESSAGE, message);
                request.getRequestDispatcher(Constants.PAGE_ERROR)
                        .forward(request, response);
                return;
            }
            response.sendRedirect(
                    request.getContextPath() + Constants.PAGE_MAIN);
        } catch (IllegalArgumentException badArg) {
            logger.debug(
                    "Bad argument occurs during handling action: " + action,
                    badArg);
            request.setAttribute(Constants.ATTR_USER_FORM, userForm);
            request.setAttribute(Constants.ATTR_ERROR_MESSAGE,
                    badArg.getMessage());
            request.getRequestDispatcher(Constants.PAGE_CREATE_UPDATE_USER)
                    .forward(request, response);
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
