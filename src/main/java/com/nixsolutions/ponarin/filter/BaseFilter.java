package com.nixsolutions.ponarin.filter;

import javax.servlet.*;
import javax.servlet.http.*;
import java.io.IOException;

public abstract class BaseFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // NOP
    }

    @Override
    public void doFilter(ServletRequest servletRequest,
            ServletResponse servletResponse, FilterChain filterChain)
                    throws IOException, ServletException {
        doFilter((HttpServletRequest) servletRequest,
                (HttpServletResponse) servletResponse, filterChain);
    }

    @Override
    public void destroy() {
        // NOP
    }

    public abstract void doFilter(HttpServletRequest request,
            HttpServletResponse response, FilterChain filterChain)
                    throws IOException, ServletException;
}
