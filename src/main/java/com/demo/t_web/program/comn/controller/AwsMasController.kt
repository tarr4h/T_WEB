package com.demo.t_web.program.comn.controller;

import com.demo.t_web.comn.util.Utilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * <pre>
 * com.demo.t_web.program.comn.controller.Controller
 *   - Controller.java
 * </pre>
 *
 * @author : 한태우
 * @className : Controller
 * @description :
 * @date : 5/11/24
 */
@Controller
@Slf4j
@RequestMapping("/awsMas")
public class AwsMasController {


    @GetMapping("healthCheck")
    public ResponseEntity<?> healthCheck(@RequestParam Map<String, Object> param){
        log.debug("telifajsleifjalsiejfliase");
        return Utilities.retValue(true);
    }
}
