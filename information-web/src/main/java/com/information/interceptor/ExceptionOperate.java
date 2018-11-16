package com.information.interceptor;

import com.google.gson.Gson;
import com.information.HaltException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@ControllerAdvice
public class ExceptionOperate {

    private static final Logger logger = LoggerFactory.getLogger(ExceptionOperate.class);
    @ExceptionHandler({Exception.class})
    void handleIllegalArgumentException(HttpServletResponse response, HttpServletRequest request, Exception e) throws IOException {
        if(e instanceof HaltException){
            HaltException haltException = (HaltException)e;
            response.sendError(haltException.getCode() != 0 ? haltException.getCode() : HttpStatus.BAD_REQUEST.value(),
                    haltException.getMessage());
        }else{
            e.printStackTrace();
            response.sendError(HttpStatus.INTERNAL_SERVER_ERROR.value(),
                    e.getMessage());
        }
    }

}


