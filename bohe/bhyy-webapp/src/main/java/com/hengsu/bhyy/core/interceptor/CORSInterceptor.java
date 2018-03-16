//package com.hengsu.bhyy.core.interceptor;
//
//import com.google.common.cache.Cache;
//import com.hengsu.bhyy.core.HRErrorCode;
//import com.hengsu.bhyy.core.annotation.IgnoreAuth;
//import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang3.StringUtils;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.http.HttpMethod;
//import org.springframework.web.bind.annotation.RestController;
//import org.springframework.web.method.HandlerMethod;
//import org.springframework.web.servlet.HandlerInterceptor;
//import org.springframework.web.servlet.ModelAndView;
//
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//
///**
// * Created by haiquanli on 15/11/20.
// */
//@Slf4j
//public class CORSInterceptor implements HandlerInterceptor {
//
//    @Override
//    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//
//
//        return true;
//    }
//
//    @Override
//    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView
//            modelAndView) throws Exception {
//        String method = request.getMethod();
//        response.setHeader("Access-Control-Allow-Origin", "*");
//        if (HttpMethod.OPTIONS.toString().equals(method)) {
//            response.setHeader("Access-Control-Allow-Headers", "Origin,Content-Type,Authorization,Accept,X-Requested-With");
//            response.setHeader("Access-Control-Allow-Methods", "POST, GET,PUT,DELETE, OPTIONS");
//            response.setHeader("Access-Control-Max-Age", "60");
//            return;
//        }
//
//
//    }
//
//    @Override
//    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception
//            ex) throws Exception {
//
//    }
//
//
//
//
//}
