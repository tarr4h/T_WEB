package com.demo.t_web.program.admin.controller;

import com.demo.t_web.comn.model.Tmap;
import com.demo.t_web.comn.util.Utilities;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * <pre>
 * com.demo.t_web.program.admin.controller.AdminController
 *   - AdminController.java
 * </pre>
 *
 * @author : tarr4h
 * @className : AdminController
 * @description :
 * @date : 12/18/24
 */
@Controller
@RequestMapping("/admin")
@Slf4j
public class AdminController {

    @PostMapping("/test")
    public ResponseEntity<?> test(@RequestBody Tmap param){
        log.debug("admin test -----");
        return Utilities.retValue("test success");
    }
}
