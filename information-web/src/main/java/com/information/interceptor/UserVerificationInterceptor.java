package com.information.interceptor;

import com.information.HaltException;
import com.information.TokenHelper;
import com.information.base.Result;
import com.google.gson.Gson;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * 用户信息验证
 */
public class UserVerificationInterceptor implements HandlerInterceptor {
    Gson gson = new Gson();

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        response.setHeader("Access-Control-Allow-Headers", TokenHelper.PRO_ACCESS_TOKEN +","+TokenHelper.PRO_AUTHOR +"," +"Content-Type");
        response.setHeader("Access-Control-Allow-Origin","*");


        return true;

    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        // TODO Auto-generated method stub

    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        // TODO Auto-generated method stub
        if (ex instanceof HaltException) {
            PrintWriter writer = response.getWriter();
            response.setStatus(200);
            writer.append(gson.toJson(Result.error(ex.getMessage())));
        }

    }


}