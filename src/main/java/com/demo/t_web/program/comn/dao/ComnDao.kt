package com.demo.t_web.program.comn.dao

import com.demo.t_web.comn.model.MapSearch
import com.demo.t_web.comn.model.Tmap
import com.demo.t_web.program.comn.model.ExcludeCategory
import com.demo.t_web.program.comn.model.MapData
import com.demo.t_web.program.comn.model.NaverMap
import org.apache.ibatis.annotations.Mapper

@Mapper
interface ComnDao {
    fun insertMapData(map: NaverMap): Int

    fun selectMapDataCount(map: NaverMap): Int

    fun selectMapDataList(c: MapSearch): List<MapData>

    fun selectMapDataList(c: Map<String, Any>): List<MapData>

    fun selectMcidList(c: MapSearch): List<Tmap?>?

    fun getRegion1(param: Map<String, Any>): List<Tmap?>

    fun getRegion2(param: Map<String, Any>): List<Tmap?>

    fun getRegionMapData(param: Map<String, Any>): List<MapData>

    fun checkVisit(param: Map<String, Any>): Int

    fun insertVisitLog(param: Map<String, Any>): Int

    fun updateVisitLog(param: Map<String, Any>): Int

    fun checkVanish(param: Map<String, Any>): Int

    fun insertVanish(param: Map<String, Any>): Int

    fun updateVanish(param: Map<String, Any>): Int

    fun selectMapData(stringObjectMap: Map<String, Any>): MapData?

    fun insertLocationChange(cmap: Map<String, Any>): Int

    fun updateMapDataLocation(mdt: MapData?): Int

    fun selectRelatedCategories(param: Map<String, Any>): List<ExcludeCategory>

    fun selectMapDataExist(param: Map<String, Any>): List<MapData>

    fun insertRequestData(param: Map<String, Any>): Int

    fun insertDuplicateLocationData(d: Tmap?): Int

    fun checkIsDuplicateInserted(string: String): Int
    
}