package com.demo.t_web.program.comn.service;

import com.demo.t_web.comn.model.MapSearch;
import com.demo.t_web.program.comn.dao.ComnDao;
import com.demo.t_web.program.comn.model.MapData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <pre>
 * com.demo.t_web.program.comn.service.CacheableService
 *   - CacheableService.java
 * </pre>
 *
 * @author : 한태우
 * @className : CacheableService
 * @description :
 * @date : 7/24/24
 */
@Service
@Slf4j
public class CacheableService {


    @Autowired
    ComnDao dao;

    @Cacheable(cacheNames = "MAPDATA", key="#param.cacheKey")
    public List<MapData> selectMapDataList(MapSearch param) {
        log.debug("flow test222");
        return dao.selectMapDataList(param);
    }
}
