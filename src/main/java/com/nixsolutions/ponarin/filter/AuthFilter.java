package com.nixsolutions.ponarin.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.nixsolutions.ponarin.Constants;
import com.nixsolutions.ponarin.entity.User;

public class AuthFilter extends BaseFilter {
    private static final Logger logger = LoggerFactory
            .getLogger(AuthFilter.class);

    @Override
    public void doFilter(HttpServletRequest request,
            HttpServletResponse response, FilterChain filterChain)
                    throws IOException, ServletException {
        logger.trace("inside doFilter");
        String servletPath = request.getServletPath();

        // Allow non authorized pages
        if (servletPath.equals("/login.do") || servletPath.equals("/login.jsp")
                || servletPath.equals("/logout.do")
                || servletPath.equals("/logout.jsp")) {
            logger.trace("request was made for non authorized pages");
            filterChain.doFilter(request, response);
            return;
        }

        User user = (User) request.getSession()
                .getAttribute(Constants.ATTR_USER);

        if (user != null) {
            logger.trace("request authorized by user " + user.getLogin());
            filterChain.doFilter(request, response);
        } else {
            logger.trace("request not authorized, redirect to login page");
            response.sendRedirect(
                    request.getContextPath() + Constants.PAGE_LOGIN);
        }
    }
}
