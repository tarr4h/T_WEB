package com.demo.t_web.program.comn.controller;

import com.demo.t_web.comn.model.Tmap;
import com.demo.t_web.comn.util.Utilities;
import com.demo.t_web.program.comn.service.ComnService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;
import java.util.Map;

/**
 * <pre>
 * com.demo.t_web.program.comn.controller.ComnController
 *   - ComnController.java
 * </pre>
 *
 * @author : 한태우
 * @ClassName : ComnController
 * @description :
 * @date : 2023/10/15
 */
@RestController
@Slf4j
@RequestMapping("/comn")
public class ComnController {

    @Autowired
    ComnService service;

    @PostMapping("importFiles")
    public ResponseEntity<?> importFiles(@RequestParam(value="file", required = false) MultipartFile[] fileList){
        log.debug("fileList = {}", fileList.length);
        return ResponseEntity.ok().body(service.importFile(fileList));
    }

    @GetMapping("getData")
    public ResponseEntity<?> getData(@RequestParam Map<String, Object> param) {
        return Utilities.retValue(service.getData(new Tmap(param)));
    }

    @GetMapping("getDriving")
    public ResponseEntity<?> getDriving(@RequestParam Map<String, Object> param){
        return Utilities.retValue(service.getDriving(new Tmap(param)));
    }

    @GetMapping("getRegion1")
    public ResponseEntity<?> getRegion1(@RequestParam Map<String, Object> param){
        return Utilities.retValue(service.getRegion1(param));
    }

    @GetMapping("getRegion2")
    public ResponseEntity<?> getRegion2(@RequestParam Map<String, Object> param){
        return Utilities.retValue(service.getRegion2(param));
    }

    @GetMapping("getRegionGeoLoc")
    public ResponseEntity<?> getRegionGeoLoc(@RequestParam Map<String, Object> param){
        return Utilities.retValue(service.getRegionGeoLoc(param));
    }

    @GetMapping("getNvSearch")
    public ResponseEntity<?> getNvSearch(@RequestParam Map<String, Object> param){
        return Utilities.retValue(service.getNvSearch(new Tmap(param)));
    }

    @GetMapping("nvSearch")
    public ResponseEntity<?> nvSearch(@RequestParam Map<String, Object> param){
        return Utilities.retValue(Utilities.getNvSearch(new Tmap(param)));
    }

    @GetMapping("verifyData")
    public ResponseEntity<?> verifyData(@RequestParam Map<String, Object> param){
        return Utilities.retValue(Arrays.asList("123123", "asdlfiajsdfl"));
    }

    @PostMapping("visitLog")
    public void visitLog(HttpServletRequest request){
        service.visitLog(request);
    }

    @PostMapping("requestNewData")
    public ResponseEntity<?> requestNewData(@RequestBody Tmap param){
        return Utilities.retValue(service.insertRequestData(param));
    }
}
