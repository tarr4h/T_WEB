package com.demo.t_web.program.comn.service;

import com.demo.t_web.comn.model.MapSearch;
import com.demo.t_web.comn.util.Utilities;
import com.demo.t_web.program.comn.dao.ComnDao;
import com.demo.t_web.program.comn.model.MapData;
import com.demo.t_web.program.comn.model.NaverMap;
import com.demo.t_web.program.comn.model.NaverMapList;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * <pre>
 * com.demo.t_web.program.comn.service.ComnService
 *   - ComnService.java
 * </pre>
 *
 * @author : 한태우
 * @ClassName : ComnService
 * @description :
 * @date : 2023/10/19
 */
@Service
@Slf4j
public class ComnService {

    @Autowired
    ComnDao dao;

    public Object importFile(MultipartFile[] fileList) {
        log.info("fileList size = {}", fileList.length);
        int cnt = 0;
        int existCnt = 0;
        for(MultipartFile file : fileList){
            log.info("----------------------------------------------------");
            log.info("file_name = {}", file.getOriginalFilename());
            try {
                String jsonString = new String(file.getBytes(), StandardCharsets.UTF_8);

                ObjectMapper obj = new ObjectMapper();
                NaverMapList mapList = obj.readValue(jsonString, NaverMapList.class);

                log.info("list cnt = {}", mapList.getBookmarkList().size());

                List<NaverMap> maps = mapList.getBookmarkList();
                for(NaverMap map : maps){
                    if(map.isAvailable()){
                        int exist = dao.selectMapDataCount(map);
                        if(exist == 0){
                            cnt += dao.insertMapData(map);
                        } else {
                            existCnt++;
                        }
                    }
                }

                log.info("insert cnt = {}", cnt);
                log.info("----------------------------------------------------");
            } catch (IOException e){
                log.error(e.getMessage());
            }
        }
        return cnt;
    }

    public Object getData(Map<String, Object> param) {
        MapSearch c = getMaxLatLng(param);
        c.setMcid((String) param.get("mcid"));
        c.setPlaceName((String) param.get("placeName"));

        List<MapData> dataList = dao.selectMapDataList(c);

        List<MapData> availList = new ArrayList<>();
        for(MapData data : dataList){
            double parseLat = Utilities.parseDouble(data.getPy());
            double parseLng = Utilities.parseDouble(data.getPx());
            double distance = Utilities.calculateArea(c.getLat(), c.getLng(), parseLat, parseLng);
            data.setCenterDistance(distance);

            if(distance < c.getRadius()){
                availList.add(data);
            }
        }

        Collections.sort(availList);

        Map<String, Object> ret = new HashMap<>();
        ret.put("dataList", availList);
        ret.put("maxLatLng", c);
        return ret;
    }

    public Object getMcidList(Map<String, Object> param) {
        log.debug("getMcidList param = {}", param);
        return dao.getMcidList(getMaxLatLng(param));
//        return dao.getMcidList(new MapSearch());
    }

    public MapSearch getMaxLatLng(Map<String, Object> param){
        double lat = Utilities.parseDouble(param.get("lat"));
        double lng = Utilities.parseDouble(param.get("lng"));
        int radius = Utilities.parseInt(param.get("radius"));

        return Utilities.getMaxLatLng(lat, lng, radius);
    }
}
