package com.nixsolutions.ponarin.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.nixsolutions.ponarin.Constants;

public class LoginFilter extends BaseFilter {
    // mapping "/*"
    @Override
    public void doFilter(HttpServletRequest request,
            HttpServletResponse response, FilterChain filterChain)
                    throws IOException, ServletException {

        System.out.println("inside do filter");

        if (request.getSession(false) != null) {
            System.out.println("to servlet");
            request.getRequestDispatcher(Constants.SERVLET_LOGIN)
                    .forward(request, response);
        } else {
            System.out.println("to page");
            request.getRequestDispatcher(Constants.PAGE_LOGIN).forward(request,
                    response);
        }
    }
}
