package com.demo.t_web.program.memo.controller

import com.demo.t_web.comn.model.Tmap
import com.demo.t_web.program.memo.model.Memo
import com.demo.t_web.program.memo.service.MemoService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import reactor.core.publisher.Flux

@RestController
@RequestMapping("/memo")
class MemoController (
    private val service: MemoService
){

    @PostMapping("search")
    fun search(@RequestBody param : Tmap) : ResponseEntity<Flux<String>> {
        return ResponseEntity.ok().body(service.search(param))
    }

    @PostMapping("/saveChatHistory")
    fun saveChatHistory(@RequestBody param: Tmap): ResponseEntity<Tmap> {
        return ResponseEntity.ok().body(service.saveChatHistory(param))
    }

    @PostMapping("/add")
    fun add(@RequestBody memo: Memo): ResponseEntity<*> {
        return ResponseEntity.ok().body(service.add(memo))
    }

    @PostMapping("/delete")
    fun delete(@RequestBody memo: Memo): ResponseEntity<*> {
        return ResponseEntity.ok().body(service.delete(memo))
    }

    @GetMapping("/list")
    fun list(): ResponseEntity<*> {
        return ResponseEntity.ok().body(service.selectMemoList())
    }
}