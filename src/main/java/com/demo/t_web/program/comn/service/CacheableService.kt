package com.demo.t_web.program.comn.service

import com.demo.t_web.comn.model.MapSearch
import com.demo.t_web.program.comn.dao.ComnDao
import com.demo.t_web.program.comn.model.MapData
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class CacheableService (
    val dao : ComnDao
){

    @Cacheable(cacheNames = ["MAPDATA"], key = "#param.cacheKey")
    fun selectMapDataList(param : MapSearch) : List<MapData> {
        return dao.selectMapDataList(param)
    }
}