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

public class AdminFilter extends BaseFilter {
    private static final Logger logger = LoggerFactory
            .getLogger(AdminFilter.class);

    @Override
    public void doFilter(HttpServletRequest request,
            HttpServletResponse response, FilterChain filterChain)
                    throws IOException, ServletException {
        logger.trace("inside doFilter");
        User user = (User) request.getSession()
                .getAttribute(Constants.ATTR_USER);
        if (user.getRole().getName().equalsIgnoreCase(Constants.ROLE_ADMIN)) {
            logger.trace("accsess allow for user " + user.getLogin());
            filterChain.doFilter(request, response);
        } else {
            logger.trace("accsess denied for user " + user.getLogin());
            String title = "Access denied";
            String message = "Access denied! You trying to access to page with role 'Admin'";
            request.setAttribute(Constants.ATTR_ERROR_MESSAGE_TITLE, title);
            request.setAttribute(Constants.ATTR_ERROR_MESSAGE, message);
            request.getRequestDispatcher(Constants.PAGE_ERROR).forward(request,
                    response);
        }
    }

}
