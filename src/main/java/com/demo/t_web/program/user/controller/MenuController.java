package com.demo.t_web.program.user.controller;

import com.demo.t_web.program.sys.model.Menu;
import com.demo.t_web.program.user.service.MenuService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <pre>
 * com.demo.t_web.program.user.controller.MenuController
 *   - MenuController.java
 * </pre>
 *
 * @author : tarr4h
 * @className : MenuController
 * @description :
 * @date : 12/19/24
 */
@RestController
@RequestMapping("/menu")
@AllArgsConstructor
public class MenuController {

    private MenuService service;

    @PostMapping("/add")
    public ResponseEntity<?> addMenu(@RequestBody Menu menu) {
        return ResponseEntity.ok(service.addMenu(menu));
    }

}
