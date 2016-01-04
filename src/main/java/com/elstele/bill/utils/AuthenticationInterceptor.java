package com.elstele.bill.utils;

import com.elstele.bill.domain.LocalUser;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        String path = httpServletRequest.getRequestURI().substring(httpServletRequest.getContextPath().length());
        LocalUser localUser = (LocalUser)httpServletRequest.getSession().getAttribute(Constants.LOCAL_USER);
        if(path.equals("/") || path.contains("/resources") || (path.equals("/login.html") && httpServletRequest.getMethod().equals("POST"))){
            return true;
        }
        if(localUser == null){
            httpServletResponse.sendRedirect("/");
            return false;
        }
        else{
            return true;
        }
    }

}
