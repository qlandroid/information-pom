package com.information.interceptor;

import com.information.HaltException;
import com.information.base.Result;
import com.google.gson.Gson;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@ControllerAdvice
public class ExceptionOperate {
    Gson gson = new Gson();


    @ExceptionHandler(Exception.class)
    public void handlerException(HttpServletRequest request, HttpServletResponse response, Exception ex) throws IOException {
        response.setCharacterEncoding("UTF-8");
        PrintWriter writer = response.getWriter();
        response.setStatus(200);
        ex.printStackTrace();
        if (ex instanceof HaltException) {
            writer.append(gson.toJson(Result.error(((HaltException) ex).getCode(), ex.getMessage())));
        } else if (ex instanceof NullPointerException) {
            writer.append(gson.toJson(Result.error("缺少参数")));
        } else if (ex instanceof ClassCastException) {
            writer.append(gson.toJson(Result.error("传递参数类型不正确")));
        } else {
            writer.append(gson.toJson(Result.error("服务器异常，抢修中")));
        }
    }


}


