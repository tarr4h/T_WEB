package com.demo.t_web.program.comn.controller

import com.demo.t_web.comn.util.Utilities
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Controller
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam

@Controller
@RequestMapping("/awsMas")
class AwsMasController {

    @GetMapping("healthCheck")
    fun healthCheck(@RequestParam param : Map<String, Any>) : ResponseEntity<*> {
        return Utilities.retValue(true)
    }
}