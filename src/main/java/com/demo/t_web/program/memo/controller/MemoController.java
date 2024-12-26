package com.demo.t_web.program.memo.controller;

import com.demo.t_web.program.memo.model.Memo;
import com.demo.t_web.program.memo.service.MemoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * <pre>
 * com.demo.t_web.program.memo.controller.MemoController
 *   - MemoController.java
 * </pre>
 *
 * @author : tarr4h
 * @className : MemoController
 * @description :
 * @date : 12/27/24
 */
@RestController
@RequestMapping("/memo")
@AllArgsConstructor
public class MemoController {

    private MemoService service;

    @GetMapping("/search")
    public ResponseEntity<?> search(@RequestParam Map<String, Object> param){
        return ResponseEntity.ok().body(service.search(param));
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Memo memo){
        return ResponseEntity.ok().body(service.add(memo));
    }
}
