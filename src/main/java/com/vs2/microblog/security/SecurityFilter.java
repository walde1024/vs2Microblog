package com.vs2.microblog.security;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Walde on 28.03.16.
 */
@Component
public class SecurityFilter implements Filter {

    @Autowired
    SecurityExcludeConfiguration securityExcludeConfiguration;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;

        if (!isRequestPathExcluded(request) && !isUserAuthenticated(request)) {
            ((HttpServletResponse) servletResponse).sendRedirect("/login");
            return;
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private boolean isUserAuthenticated(HttpServletRequest request) {
        String email = (String) request.getSession().getAttribute("email");
        if (email != null) {
            return true;
        }

        return false;
    }

    public boolean isRequestPathExcluded(HttpServletRequest request) {
        String path = request.getRequestURI().substring(request.getContextPath().length());
        return securityExcludeConfiguration.isExcluded(path);
    }

    @Override
    public void destroy() {

    }
}
