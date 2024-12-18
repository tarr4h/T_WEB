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

    @Autowired
    CacheableService cacheService;

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

    public Object getData(Tmap param) {
        MapSearch c = getMaxLatLng(param);
        c.setMcid(param.getString("mcid"));
        c.setAddr1(param.getString("addr1"));
        c.setAddr2(param.getString("addr2"));

        List<String> placeName = new ArrayList<>();
        String p = param.getString("placeName");
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
        List<MapData> dataList = cacheService.selectMapDataList(c);

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

        Tmap ret = new Tmap();
        ret.put("dataList", availList);
        ret.put("maxLatLng", c);
        ret.put("mcidList", mcidList);
        return ret;
    }

    public MapSearch getMaxLatLng(Tmap param){
        double lat = param.getDouble("lat");
        double lng = param.getDouble("lng");
        int radius = param.getInt("radius");

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
    public Object getDriving(Tmap param) {
        double centerLat = param.getDouble("centerLat");
        double centerLng = param.getDouble("centerLng");
        double lat = param.getDouble("lat");
        double lng = param.getDouble("lng");

        DrivingVo driving = Utilities.getDriving(centerLat, centerLng, lat, lng);
        if(driving != null){
            int duration = 0;
            if(driving.getRoute() != null){
                duration = driving.getRoute().getTraoptimal().get(0).getSummary().getDuration();
            }
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

    public Object getNvSearch(Tmap param) {
        NvSearch search = Utilities.getNvSearch(param);
        if(search == null || search.getItems().isEmpty()){
            boolean vanishYn = param.getBoolean("vanishYn");
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
        double cLat = param.getDouble("lat");
        double cLng = param.getDouble("lng");

        if(search.getItems().size() > 1){
            for(NvSearch.NvSearchDetail nvDetail : search.getItems()){
                List<ExcludeCategory> exCtgrs = dao.selectRelatedCategories(param);
                if(exCtgrs.stream().anyMatch(v -> nvDetail.getCategory().contains(v.getCategoryName()))){
                    continue;
                }
                double centerDistance = Utilities.calculateArea(cLat, cLng, nvDetail.getLat(), nvDetail.getLng());
                if(centerDistance < distance){
                    distance = centerDistance;
                    ret = nvDetail;
                }
            }
        } else {
            if(!search.getItems().isEmpty()){
                ret = search.getItems().get(0);
            }
        }

        if(ret != null){
            String sep1 = Utilities.listToSeparatedString(ret.getCategory().split(">"), " > ");
            String sep2 = Utilities.listToSeparatedString(sep1.split(","), ", ");
            ret.setCategory(sep2);

            String[] address;
            if(ret.getRoadAddress() != null && !ret.getRoadAddress().isEmpty()){
                address = ret.getRoadAddress().split(" ");
            } else {
                address = ret.getAddress().split(" ");
            }

            Map<String, Object> tm = new HashMap<>();
            tm.put("name", ret.getTitle().replace("<b>", "").replace("</b>", ""));
            tm.put("addr1", address[0]);
            tm.put("addr2", address[1]);
            List<MapData> mdtList = dao.selectMapDataList(tm);

            if(!mdtList.isEmpty()){
                if(mdtList.size() > 1){
                    List<Tmap> dupList = new ArrayList<>();
                    for(MapData data : mdtList){
                        Tmap d = new Tmap();
                        d.put("id", data.getId());
                        d.put("name", data.getName());
                        d.put("addr1", data.getAddr1());
                        d.put("addr2", data.getAddr2());
                        dupList.add(d);
                    }

                    String relIds = Utilities.listToSeparatedString(dupList, ",", "id");

                    int chk = dao.checkIsDuplicateInserted(relIds);
                    if(chk == 0){
                        for(Tmap d : dupList){
                            d.put("relatedId", relIds);
                            dao.insertDuplicateLocationData(d);
                        }
                    }
                } else {
                    MapData mdt = mdtList.get(0);

                    String xsb = ret.getLngStr();
                    String ysb = ret.getLatStr();

                    if(!mdt.getPx().equals(xsb) || !mdt.getPy().equals(ysb)){
                        Map<String, Object> cmap = new HashMap<>();
                        cmap.put("id", mdt.getId());
                        cmap.put("prevLat", mdt.getPy());
                        cmap.put("prevLng", mdt.getPx());
                        cmap.put("chngLat", ysb);
                        cmap.put("chngLng", xsb);
                        cmap.put("prevAddr", mdt.getAddress());
                        cmap.put("chngAddr", ret.getAddress());

                        int ll = dao.insertLocationChange(cmap);
                        if(ll != 0){
                            mdt.setPy(ysb);
                            mdt.setPx(xsb);
                            mdt.setAddress(ret.getRoadAddress() == null ? ret.getAddress() : ret.getRoadAddress());
                            mdt.setAddr1(address[0]);
                            mdt.setAddr2(address[1]);
                            mdt.setAddr3(address[2]);

                            dao.updateMapDataLocation(mdt);
                        }
                    }
                }
            }
        }

        return ret;
    }

    public void visitLog(HttpServletRequest request) {
        String ip = request.getHeader("X-Forwarded-For");
        if(ip == null || ip.isEmpty() || "0:0:0:0:0:0:0:1".equals(ip) || "127.0.0.1".equals(ip)){
            return;
        }
        Map<String, Object> param = new HashMap<>();
        String userAgent = request.getHeader("user-agent");

        param.put("ip", ip);
        param.put("userAgent", userAgent);

        int isExist = dao.checkVisit(param);

        if(isExist == 0){
            dao.insertVisitLog(param);
        } else {
            dao.updateVisitLog(param);
        }
    }

    public int insertRequestData(Tmap param) {
        String name = param.getString("title");
        param.put("name", name);

        String[] nameArrs = name.split(" ");
        if(nameArrs.length > 1){
            param.put("nameList", nameArrs);
        }

        String pxStr = new StringBuilder((param.getString("mapx"))).insert(3, ".").toString();
        String pyStr = new StringBuilder((param.getString("mapy"))).insert(2, ".").toString();
        param.put("px", pxStr);
        param.put("py", pyStr);

        List<MapData> exist = dao.selectMapDataExist(param);
        if(exist.isEmpty()){
            return dao.insertRequestData(param);
        } else {
            return 0;
        }
    }


}
