package com.demo.t_web.program.comn.controller;

import com.demo.t_web.comn.util.Utilities;
import com.demo.t_web.program.comn.service.ComnService;
import lombok.extern.slf4j.Slf4j;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

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
    public ResponseEntity<?> getData(@RequestParam Map<String, Object> param){
        return Utilities.retValue(service.getData(param));
    }

    @GetMapping("getMcid")
    public ResponseEntity<?> getMcid(@RequestParam Map<String, Object> param){
        return Utilities.retValue(service.getMcidList(param));
    }

}
