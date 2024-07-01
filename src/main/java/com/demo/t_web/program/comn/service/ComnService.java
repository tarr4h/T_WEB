package com.demo.t_web.program.comn.service;

import com.demo.t_web.comn.model.MapSearch;
import com.demo.t_web.comn.model.Tmap;
import com.demo.t_web.comn.util.Utilities;
import com.demo.t_web.program.comn.dao.ComnDao;
import com.demo.t_web.program.comn.model.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
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
                            existCnt += 1;
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
        c.setAddr1((String) param.get("addr1"));
        c.setAddr2((String) param.get("addr2"));
        List<String> placeName = new ArrayList<>();
        String p = (String) param.get("placeName");
        if(p != null){
            if(p.contains(",")){
                String[] arr = p.split(",");
                if(arr.length > 0){
                    placeName.addAll(Arrays.asList(arr));
                }
            } else {
                placeName.add(p);
            }
            c.setPlaceName(placeName);
        }

        List<MapData> dataList = dao.selectMapDataList(c);

        List<MapData> availList = new ArrayList<>();
        List<Tmap> mcidList = dao.selectMcidList(c);

        for(MapData data : dataList){
            double distance = Utilities.calculateArea(c.getLat(), c.getLng(), data.getLat(), data.getLng());
            data.setCenterDistance(distance);

            if(distance < c.getRadius()){
                availList.add(data);
            }
        }

        Collections.sort(availList);

        Map<String, Object> ret = new HashMap<>();
        ret.put("dataList", availList);
        ret.put("maxLatLng", c);
        ret.put("mcidList", mcidList);
        return ret;
    }

    public MapSearch getMaxLatLng(Map<String, Object> param){
        double lat = Utilities.parseDouble(param.get("lat"));
        double lng = Utilities.parseDouble(param.get("lng"));
        int radius = Utilities.parseInt(param.get("radius"));

        return Utilities.getMaxLatLng(lat, lng, radius);
    }

    /**
     * Returns the driving information based on the given parameters.
     *
     * @param param a map containing the following key-value pairs:
     *              - "centerLat": the latitude of the center location (double)
     *              - "centerLng": the longitude of the center location (double)
     *              - "lat": the latitude of the destination location (double)
     *              - "lng": the longitude of the destination location (double)
     * @return the driving information as a {@link DrivingVo} object, or null if no information is found
     */
    public Object getDriving(Map<String, Object> param) {
        double centerLat = Utilities.parseDouble(param.get("centerLat"));
        double centerLng = Utilities.parseDouble(param.get("centerLng"));
        double lat = Utilities.parseDouble(param.get("lat"));
        double lng = Utilities.parseDouble(param.get("lng"));
        DrivingVo driving = Utilities.getDriving(centerLat, centerLng, lat, lng);
        if(driving != null){
            int duration = driving.getRoute().getTraoptimal().get(0).getSummary().getDuration();
            driving.setDuration(duration);
            driving.setDurationMin(Utilities.miliSec2min(duration));
        }

        return driving;
    }

    public Object getRegion1(Map<String, Object> param) {
        return dao.getRegion1(param);
    }

    public Object getRegion2(Map<String, Object> param) {
        return dao.getRegion2(param);
    }

    public Object getRegionGeoLoc(Map<String, Object> param) {
        List<MapData> mapDataList = dao.getRegionMapData(param);
        Map<String, Object> returnMap = new HashMap<>();
        if(mapDataList.isEmpty()){
            return returnMap;
        }

        double minLat = 0;
        double maxLat = 0;
        double minLng = 0;
        double maxLng = 0;
        for(MapData data : mapDataList){
            if(minLat == 0 || data.getLat() < minLat) {
                minLat = data.getLat();
            }
            if(data.getLat() > maxLat) {
                maxLat = data.getLat();
            }
            if(minLng == 0 || data.getLng() < minLng){
                minLng = data.getLng();
            }
            if(data.getLng() > maxLng){
                maxLng = data.getLng();
            }
        }

        double centralLat = (minLat + maxLat) / 2;
        double centralLng = (minLng + maxLng) / 2;

        // 반경계산
        double radius = 0;
        for(MapData data : mapDataList){
            double distance = Utilities.calculateArea(centralLat, centralLng, data.getLat(), data.getLng());
            if(distance > radius){
                radius = distance;
            }

        }
        radius = Math.ceil(radius);

        returnMap.put("latitude", centralLat);
        returnMap.put("longitude", centralLng);
        returnMap.put("radius", radius);

        return returnMap;
    }

    public Object getNvSearch(Map<String, Object> param) {
        NvSearch search = Utilities.getNvSearch(param);
        if(search == null || search.getItems().size() == 0){
            boolean vanishYn = Boolean.parseBoolean((String) param.get("vanishYn"));
            if(vanishYn) {
                int chkVanish = dao.checkVanish(param);
                if(chkVanish == 0){
                    dao.insertVanish(param);
                } else {
                    dao.updateVanish(param);
                }
            }

            return null;
        }

        NvSearch.NvSearchDetail ret = null;
        double distance = 99999;
        double cLat = Utilities.parseDouble(param.get("lat"));
        double cLng = Utilities.parseDouble(param.get("lng"));

        if(search.getItems().size() > 1){
            for(NvSearch.NvSearchDetail nvDetail : search.getItems()){
                if(Utilities.calculateArea(cLat, cLng, nvDetail.getLat(), nvDetail.getLng()) < distance){
                    ret = nvDetail;
                }
            }
        } else {
            if(!search.getItems().isEmpty()){
                ret = search.getItems().get(0);
            }
        }

        if(ret != null){
            String ctgr = ret.getCategory();
            String[] cStr = ctgr.split(">");
            StringBuilder sb = new StringBuilder();
            for(int i = 0; i < cStr.length; i++){
                sb.append(cStr[i]);
                if(i != cStr.length - 1){
                    sb.append(" > ");
                }
            }
            ret.setCategory(sb.toString());
        }
        return ret;
    }

    public void visitLog(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if(ip == null || "".equals(ip) || "0:0:0:0:0:0:0:1".equals(ip) || "127.0.0.1".equals(ip)){
            return;
        }
        Map<String, Object> param = new HashMap<>();
        String userAgent = request.getHeader("user-agent");

        param.put("ip", ip);
        param.put("userAgent", userAgent);
        param.put("sessionId", request.getSession().getId());

        int isExist = dao.checkVisit(param);

        if(isExist == 0){
            dao.insertVisitLog(param);
        } else {
            dao.updateVisitLog(param);
        }
    }
}
