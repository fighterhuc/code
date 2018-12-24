package com.huc.filter;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class LoginFilter implements Filter {
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        System.out.println("asdsadasdads");
        HttpServletResponse response = (HttpServletResponse) servletResponse;
        HttpServletRequest request=(HttpServletRequest) servletRequest;
//        filterChain.doFilter(servletRequest,servletResponse);
        if(request.getRequestURI().contains("login")){
            filterChain.doFilter(request,response);
            return;
        }else{
            response.sendRedirect("/pms/login");
        }
    }

    @Override
    public void destroy() {

    }
}
