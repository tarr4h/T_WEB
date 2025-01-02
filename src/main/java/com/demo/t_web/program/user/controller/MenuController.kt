package com.demo.t_web.program.user.controller

import com.demo.t_web.program.sys.model.Menu
import com.demo.t_web.program.user.service.MenuService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/menu")
class MenuController (
    private val service : MenuService
){

    @PostMapping("/add")
    fun addMenu(@RequestBody menu: Menu) : ResponseEntity<*>{
        return ResponseEntity.ok().body(service.addMenu(menu))
    }

    @GetMapping("/selectMenuList")
    fun selectMenuList(): ResponseEntity<*> {
        return ResponseEntity.ok().body(service.selectMenuList())
    }

}