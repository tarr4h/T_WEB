package com.demo.t_web.program.sys.service;

import com.demo.t_web.comn.util.Utilities;
import com.demo.t_web.program.sys.model.ExceptionHst;
import com.demo.t_web.program.sys.repository.ExceptionRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * <pre>
 * com.demo.t_web.program.sys.service.ExceptionService
 *   - ExceptionService.java
 * </pre>
 *
 * @author : tarr4h
 * @className : ExceptionService
 * @description :
 * @date : 12/18/24
 */
@Service
@Slf4j
@AllArgsConstructor
public class ExceptionService {

    private ExceptionRepository exceptionRepository;

    public void addExceptionHst(Exception e, HttpServletRequest request) {
        try {
            String uri = request.getRequestURI();
            String exception = e.getClass().getSimpleName();
            StringWriter sw = new StringWriter();
            e.printStackTrace(new PrintWriter(sw));
            String msg = sw.toString();
            if(msg.length() > 4000){
                msg = msg.substring(0, 4000);
            }

            Enumeration<String> params = request.getParameterNames();
            List<String> values = new ArrayList<>();
            while(params.hasMoreElements()){
                String name = params.nextElement();
                values.add(name + ":" + request.getParameter(name));
            }

            String paramStr = Utilities.listToSeparatedString(values, ",");
            if(paramStr.length() > 4000){
                paramStr = paramStr.substring(0, 4000);
            }

            ExceptionHst exceptionHst = ExceptionHst.builder()
                    .uri(uri)
                    .exception(exception)
                    .msg(msg)
                    .params(paramStr)
                    .build();

            exceptionRepository.save(exceptionHst);

            log.error("exception occured ===================");
            log.error("=>", e);
            log.error("=====================================");
        } catch (Exception e1){
            log.error("addExceptionHst error => ", e1);
        }
    }
}
