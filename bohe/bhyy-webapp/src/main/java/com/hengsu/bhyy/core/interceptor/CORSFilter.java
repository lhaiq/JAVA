package com.hengsu.bhyy.core.interceptor;

import org.springframework.http.HttpMethod;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class CORSFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse,
                         FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        String method = request.getMethod();
        response.setHeader("Access-Control-Allow-Origin", "*");
        if (HttpMethod.OPTIONS.toString().equals(method)) {
            response.setHeader("Access-Control-Allow-Headers", "Origin,Content-Type,Authorization,Accept,X-Requested-With");
            response.setHeader("Access-Control-Allow-Methods", "POST, GET,PUT,DELETE, OPTIONS");
            response.setHeader("Access-Control-Max-Age", "60");
            return;
        }

        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {

    }

}
