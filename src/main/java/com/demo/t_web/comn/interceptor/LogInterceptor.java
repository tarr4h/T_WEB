package com.demo.t_web.comn.interceptor;

import com.demo.t_web.comn.util.Utilities;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import java.util.Enumeration;

/**
 * <pre>
 * com.trv.log.interceptor.LogInterceptor
 *  - LogInterceptor.java
 * </pre>
 *
 * @author : tarr4h
 * @ClassName : LogInterceptor
 * @description :
 * @date : 2023-04-19
 */

@Slf4j
@Component
public class LogInterceptor implements HandlerInterceptor {

    public boolean isAppendable(HttpServletRequest request){
        boolean appendBool = true;
        if(request.getRemoteAddr().equals("172.31.50.179")){
            appendBool = false;
        } else if(request.getHeader("user-agent") == null || request.getHeader("user-agent").isEmpty()){
            appendBool = false;
        } else if(request.getHeader("user-agent").contains("ELB")){
            appendBool = false;
        } else if(request.getHeader("user-agent").contains("masscan")){
            appendBool = false;
        } else if(request.getRequestURI().contains("healthCheck")){
            appendBool = false;
        } else if(request.getHeader("accept") == null || request.getHeader("accept").isEmpty()){
            appendBool = false;
        } else if(!request.getRequestURL().toString().contains("tarr4h")){
            appendBool = false;
        }

        return appendBool;
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        boolean appendBool = isAppendable(request);
        StringBuilder info = new StringBuilder();
        info.append("\n =====================================================================================================");
        info.append("\n == [  REQUEST_URL  ]  ").append(request.getRequestURL());
        info.append("\n == [  REQUEST_URI  ]  ").append(request.getRequestURI());
        info.append("\n == [  METHOD       ]  ").append(request.getMethod());
        info.append("\n == [  REMOTE_IP    ]  ").append(request.getRemoteAddr());
        info.append("\n == [  ACCEPT       ]  ").append(request.getHeader("accept"));
        info.append("\n == [  USER_AGENT   ]  ").append(request.getHeader("user-agent"));

        long currTm = System.currentTimeMillis();
        info.append(getUriRow(request.getRequestURI(), "PROGRESS", currTm));
        request.setAttribute("currTm", currTm);

        if(appendBool){
            log.debug(info.toString());
        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        boolean appendBool = isAppendable(request);
        String info = "";
        info += getUriRow(request.getRequestURI(), "TERMINATE", Utilities.pasreLong(request.getAttribute("currTm")));
        info += "\n =====================================================================================================";
        if(appendBool){
            log.debug(info);
        }
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    public String getUriRow(String requestUri, String text, double currTm){
        StringBuilder info = new StringBuilder();
        info.append("\n --------------------------------");
        info.append(" [ ").append(text);
        info.append(" ".repeat(10 - text.length()));
        info.append(" | ");
        info.append(requestUri);
        info.append(" | ");
        info.append(currTm);
        info.append(" ] ");
        int uriLen = requestUri.length();
        int dashLen = "--------------------------------------------------".length();
        info.append("-".repeat(Math.max(0, dashLen - uriLen)));

        return info.toString();
    }
}
