package com.nixsolutions.ponarin.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nixsolutions.ponarin.Constants;

public class LogoutController extends HttpServlet {
    private static final Logger logger = LoggerFactory
            .getLogger(LogoutController.class);
    private static final long serialVersionUID = 1L;

    @Override
    protected void doGet(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        logger.trace("logout");
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.removeAttribute(Constants.ATTR_USER);
            response.sendRedirect(
                    request.getContextPath() + Constants.PAGE_LOGOUT);
        }
    }
}
