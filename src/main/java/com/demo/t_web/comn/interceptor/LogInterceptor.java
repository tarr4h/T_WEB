package com.demo.t_web.comn.interceptor;

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

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        StringBuilder info = new StringBuilder();
        info.append("\n =====================================================================================================");
        info.append("\n == [  REQUEST_URL  ]  ").append(request.getRequestURL());
        info.append("\n == [  REQUEST_URI  ]  ").append(request.getRequestURI());
        info.append("\n == [  METHOD       ]  ").append(request.getMethod());
        info.append("\n == [  REMOTE_IP    ]  ").append(request.getRemoteAddr());
        info.append("\n == [  ACCEPT       ]  ").append(request.getHeader("accept"));
        info.append("\n == [  USER_AGENT   ]  ").append(request.getHeader("user-agent"));
        info.append(getUriRow(request.getRequestURI(), "PROGRESS"));

        log.debug(info.toString());

//        Enumeration headers = request.getHeaderNames();
//        while(headers.hasMoreElements()){
//            String name = (String) headers.nextElement();
//            log.debug("\n name = {}, value = {}", name, request.getHeader(name));
//        }

        return HandlerInterceptor.super.preHandle(request, response, handler);
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        String info = "";
        info += getUriRow(request.getRequestURI(), "TERMINATE");
        info += "\n =====================================================================================================";
        log.debug(info);
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    public String getUriRow(String requestUri, String text){
        StringBuilder info = new StringBuilder();
        info.append("\n --------------------------------");
        info.append(" [ ").append(text);
        info.append(" ".repeat(10 - text.length()));
        info.append(" | ");
        info.append(requestUri);
        info.append(" ] ");
        int uriLen = requestUri.length();
        int dashLen = "--------------------------------------------------".length();
        info.append("-".repeat(Math.max(0, dashLen - uriLen)));

        return info.toString();
    }
}
