package com.nixsolutions.ponarin.controller;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.nixsolutions.ponarin.Constants;

public class LogoutController extends HttpServlet{

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession session = req.getSession();
        if (session != null) {
            session.removeAttribute(Constants.ATTR_USER);
            resp.sendRedirect(Constants.PAGE_LOGIN);
        }
    }
    
}
