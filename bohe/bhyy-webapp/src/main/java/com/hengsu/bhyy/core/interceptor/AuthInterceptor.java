package com.hengsu.bhyy.core.interceptor;

import com.google.common.cache.Cache;
import com.hengsu.bhyy.core.HRErrorCode;
import com.hengsu.bhyy.core.annotation.IgnoreAuth;
import com.hengsu.bhyy.core.model.SessionUserModel;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created by haiquanli on 15/11/20.
 */
@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Autowired
    @Qualifier("sessionCache")
    private Cache<String, SessionUserModel> sessionCache;

    private final static String AUTHORIZATION = "Authorization";
    private final static String ROLE = "Role";


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(!(handler instanceof HandlerMethod)){
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        boolean isIgnore = checkIgnore(handler);

        if (!isIgnore) {

            RestController restController = handlerMethod.getBeanType().getAnnotation(RestController.class);
            if (null != restController) {

                String authToken = request.getHeader(AUTHORIZATION);
                if (StringUtils.isEmpty(authToken)) {
                    HRErrorCode.throwBusinessException(HRErrorCode.UN_LOGIN);
                }

                SessionUserModel sessionUserModel = sessionCache.getIfPresent(authToken);
                if (null == sessionUserModel) {
                    HRErrorCode.throwBusinessException(HRErrorCode.UN_LOGIN);
                }

                request.setAttribute("userId", sessionUserModel.getUserId());
            }
        }

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView
            modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception
            ex) throws Exception {

    }


    private boolean checkIgnore(Object handler) {
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        IgnoreAuth ignoreAuth = handlerMethod.getMethodAnnotation(IgnoreAuth.class);
        if (null != ignoreAuth) {
            return true;
        }

        return false;
    }

}
