package com.demo.t_web.program.comn.controller

import com.demo.t_web.comn.model.Tmap
import com.demo.t_web.comn.util.Utilities
import com.demo.t_web.program.comn.service.ComnService
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/comn")
class ComnController(
    private val service : ComnService
) {

//    @PostMapping("importFiles")
//    fun importFiles(@RequestParam(value = "file", required = false) fileList : Array<MultipartFile>) : ResponseEntity<*> {
//        return ResponseEntity.ok().body(service.importFile(fileList))
//    }

    @GetMapping("getData")
    fun getData(@RequestParam param : Map<String, Any>) : ResponseEntity<*>{
        return Utilities.retValue(service.getData(Tmap(param)))
    }

    @GetMapping("getDriving")
    fun getDriving(@RequestParam param : Map<String, Any>) : ResponseEntity<*>{
        return Utilities.retValue(service.getDriving(Tmap(param)))
    }

    @GetMapping("getRegion1")
    fun getRegion1(@RequestParam param : Map<String, Any>) : ResponseEntity<*>{
        return Utilities.retValue(service.getRegion1(param))
    }

    @GetMapping("getRegion2")
    fun getRegion2(@RequestParam param : Map<String, Any>) : ResponseEntity<*>{
        return Utilities.retValue(service.getRegion2(param))
    }

    @GetMapping("getRegionGeoLoc")
    fun getRegionGeoLoc(@RequestParam param : Map<String, Any>) : ResponseEntity<*>{
        return Utilities.retValue(service.getRegionGeoLoc(param))
    }

    @GetMapping("getNvSearch")
    fun getNvSearch(@RequestParam param : Map<String, Any>) : ResponseEntity<*>{
        return Utilities.retValue(service.getNvSearch(Tmap(param)))
    }

    @GetMapping("nvSearch")
    fun nvSearch(@RequestParam param : Map<String, Any>) : ResponseEntity<*>{
        return Utilities.retValue(service.getNvSearch(Tmap(param)))
    }

    @PostMapping("visitLog")
    fun visitLog(request : HttpServletRequest){
        service.visitLog(request)
    }

    @PostMapping("requestNewData")
    fun requestNewData(@RequestBody param : Tmap) : ResponseEntity<*>{
        return Utilities.retValue(service.insertRequestData(param))
    }
}