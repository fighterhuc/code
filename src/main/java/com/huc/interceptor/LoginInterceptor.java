package com.huc.interceptor;

import com.huc.domain.base.UserBean;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.session.Session;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
public class LoginInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String url = httpServletRequest.getRequestURI();
        if(url.indexOf("/login")>0){
            return true;
        }
        UserBean user = getUserBySession();
        if(user==null){
            httpServletResponse.setStatus(10086);
            httpServletResponse.setHeader("content-type","base/html;character=UTF-8");
            httpServletResponse.getOutputStream().write("登陆超时,请重新登陆!".getBytes());
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    /**
     * 获取session中的用户对象
     * @return
     */
    private UserBean getUserBySession(){
        Session session = SecurityUtils.getSubject().getSession();
        if (session==null){
            return null;
        }
        return session.getAttribute("user")==null?null:(UserBean)session.getAttribute("user");
    }
}
