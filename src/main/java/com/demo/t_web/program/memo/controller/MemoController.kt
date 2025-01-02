package com.demo.t_web.program.memo.controller;

import com.demo.t_web.comn.model.Tmap;
import com.demo.t_web.program.memo.model.Memo;
import com.demo.t_web.program.memo.service.MemoService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

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

    @PostMapping("/search")
    public ResponseEntity<Flux<String>> search(@RequestBody Tmap param){
        return ResponseEntity.ok().body(service.search(param));
    }

    @PostMapping("/saveChatHistory")
    public ResponseEntity<Tmap> saveChatHistory(@RequestBody Tmap param){
        return ResponseEntity.ok().body(service.saveChatHistory(param));
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Memo memo){
        return ResponseEntity.ok().body(service.add(memo));
    }

    @PostMapping("/delete")
    public ResponseEntity<?> delete(@RequestBody Memo memo){
        return ResponseEntity.ok().body(service.delete(memo));
    }

    @GetMapping("/list")
    public ResponseEntity<?> list(){
        return ResponseEntity.ok().body(service.selectMemoList());
    }
}
