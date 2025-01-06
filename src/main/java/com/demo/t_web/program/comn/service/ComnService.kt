package com.demo.t_web.program.comn.service

import com.demo.t_web.comn.model.MapSearch
import com.demo.t_web.comn.model.Tmap
import com.demo.t_web.comn.util.Utilities
import com.demo.t_web.program.comn.dao.ComnDao
import com.demo.t_web.program.comn.model.MapData
import com.demo.t_web.program.comn.model.NvSearchDetail
import com.demo.t_web.program.memo.model.Memo
import com.demo.t_web.program.memo.repository.MemoRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import org.apache.poi.ss.usermodel.CellType
import org.apache.poi.xssf.usermodel.XSSFCell
import org.apache.poi.xssf.usermodel.XSSFSheet
import org.apache.poi.xssf.usermodel.XSSFWorkbook
import org.springframework.stereotype.Service
import java.io.FileInputStream
import java.text.NumberFormat
import java.util.*
import kotlin.collections.HashMap
import kotlin.math.ceil

@Service
class ComnService(
    val dao: ComnDao,
    val cacheService: CacheableService,
    val memoRepository: MemoRepository
) {

    private val log = KotlinLogging.logger {}

    fun getData(param : Tmap) : Tmap {
        val c : MapSearch = getMaxLatLng(param)
        c.mcid = param.getString("mcid")
        c.addr1 = param.getString("addr1")
        c.addr2 = param.getString("addr2")

        val placeName = mutableListOf<String>()
        val p = param.getString("placeName")
        if(p != null){
            if(p.contains(",")){
                val arr = p.split(",")
                if(arr.isNotEmpty()) {
                    placeName.addAll(arr)
                }
            } else {
                placeName.add(p)
            }
            c.placeName = placeName
        }

        val dataList : List<MapData> = cacheService.selectMapDataList(c)
        val availList = mutableListOf<MapData>()
        val mcidList = dao.selectMcidList(c)

        for(data in dataList){
            val distance = Utilities.calculateArea(c.lat, c.lng, data.getDLat(), data.getDLng())
            data.centerDistance = distance

            if(distance < c.radius) availList.add(data)
        }

        availList.sort()

        return Tmap()
            .direct("dataList", availList)
            .direct("maxLatLng", c)
            .direct("mcidList", mcidList)
    }

    fun getMaxLatLng(param : Tmap) : MapSearch {
        val lat = param.getDouble("lat")
        val lng = param.getDouble("lng")
        val radius  = param.getInt("radius")

        return Utilities.getMaxLatLng(lat, lng, radius)
    }

    fun getDriving(param : Tmap) : Any{
        val centerLat = param.getDouble("centerLat")
        val centerLng = param.getDouble("centerLng")
        val lat = param.getDouble("lat")
        val lng = param.getDouble("lng")

        val driving = Utilities.getDriving(centerLat, centerLng, lat, lng)
        if(driving != null){
            var duration : Int = 0
            if(driving.route != null){
                duration = driving.route!!.traoptimal[0].summary.duration
                driving.duration = duration.toDouble()
                driving.durationMin = Utilities.miliSec2min(duration)
            }
        }

        return driving
    }

    fun getRegion1(param : Map<String, Any>) : Any {
        return dao.getRegion1(param)
    }

    fun getRegion2(param : Map<String, Any>) : Any {
        return dao.getRegion2(param)
    }

    fun getRegionGeoLoc(param : Map<String, Any>) : Any? {
        val mapDataList = dao.getRegionMapData(param)
        val returnMap: MutableMap<String, Any> = HashMap()
        if(mapDataList.isEmpty()){
            return returnMap
        }

        var minLat = 0.0
        var maxLat = 0.0
        var minLng = 0.0
        var maxLng = 0.0
        for(data in mapDataList){
            if(minLat == 0.0 || data.getDLat() < minLat) minLat = data.getDLat()
            if(data.getDLat() > maxLat) maxLat = data.getDLat()
            if(minLng == 0.0 || data.getDLng() < minLng) minLng = data.getDLng()
            if(data.getDLng() > maxLng) maxLng = data.getDLng()
        }

        val centralLat = (minLat + maxLat) / 2
        val centralLng = (minLng + maxLng) / 2

        // 반경계산
        var radius = 0.0
        for(data in mapDataList){
            val distance = Utilities.calculateArea(centralLat, centralLng, data.getDLat(), data.getDLng())
            if(distance > radius) radius = distance
        }
        radius = ceil(radius)

        returnMap["latitude"] = centralLat
        returnMap["longitude"] = centralLng
        returnMap["radius"] = radius
        return returnMap
    }

    fun getNvSearch(param : Tmap) : Any? {
        val search = Utilities.getNvSearch(param)
        if(search == null || search.items.isEmpty()){
            val vanishYn = param.getBoolean("vanishYn")
            if(vanishYn){
                if(dao.checkVanish(param) == 0){
                    dao.insertVanish(param)
                } else {
                    dao.updateVanish(param)
                }
            }

            return null
        }

        var ret : NvSearchDetail? = null
        var distance = 99999.0
        val cLat = param.getDouble("lat")
        val cLng = param.getDouble("lng")

        if(search.items.size > 1){
            for(nvd in search.items){
                val exCtgrs = dao.selectRelatedCategories(param)
                if(exCtgrs.stream().anyMatch{v -> nvd.category.contains(v.categoryName)}){
                    continue
                }
                val centerDistance = Utilities.calculateArea(cLat, cLng, nvd.getLat(), nvd.getLng())
                if(centerDistance > distance){
                    distance = centerDistance
                    ret = nvd
                }
            }
        } else {
            if(search.items.isNotEmpty()){
                ret = search.items.get(0)
            }
        }

        if(ret != null){
            val sep1 = Utilities.listToSeparatedString(ret.category.split(">"), " > ")
            val sep2 = Utilities.listToSeparatedString(sep1.split(","), ", ")
            ret.category = sep2

            var address : List<String>? = null
            ret.roadAddress?.let {
                address = if(it.isNotEmpty()){
                    it.split(" ")
                } else {
                    ret.address!!.split(" ")
                }
            }

            val tm : MapSearch = getMaxLatLng(param)
            tm.name = ret.title.replace("<b>", "").replace("</b>", "")
            tm.addr1 = address!![0]
            tm.addr2 = address!![1]
            val mdtList : List<MapData> = dao.selectMapDataList(tm)

            if(mdtList.isNotEmpty()){
                if(mdtList.size > 1){
                    val dupList : MutableList<Tmap> = mutableListOf()
                    for(data in mdtList){
                        dupList.add(
                            Tmap()
                                .direct("id", data.id)
                                .direct("name", data.name)
                                .direct("addr1", data.addr1)
                                .direct("addr2", data.addr2)
                        )
                    }

                    val relIds = Utilities.listToSeparatedString(dupList, ",", "id")

                    val chk = dao.checkIsDuplicateInserted(relIds)
                    if(chk == 0){
                        for(d in dupList){
                            d.put("relatedId", relIds)
                            dao.insertDuplicateLocationData(d)
                        }
                    }
                }
            } else {
                val mdt = mdtList[0]

                val xsb = ret.getLngStr()
                val ysb = ret.getLatStr()

                if(!mdt.px.equals(xsb) || !mdt.py.equals(ysb)){
                    val cmap : MutableMap<String, Any> = mutableMapOf()
                    cmap["id"] = mdt.id
                    cmap["prevLat"] = mdt.py
                    cmap["prevLng"] = mdt.px
                    cmap["chngLat"] = ysb
                    cmap["chngLng"] = xsb
                    cmap["prevAddr"] = mdt.address
                    cmap["chngAddr"] = ret.address!!

                    val ll = dao.insertLocationChange(cmap)
                    if(ll != 0){
                        mdt.py = ysb
                        mdt.px = xsb
                        if(ret.address == null){
                            mdt.address = ret.address!!
                        } else {
                            mdt.address = ret.roadAddress!!
                        }
                        mdt.addr1 = address!![0]
                        mdt.addr2 = address!![1]
                        mdt.addr3 = address!![2]

                        dao.updateMapDataLocation(mdt)
                    }
                }
            }
        }


        return ret
    }

    fun visitLog(request : HttpServletRequest) {
        val ip = request.getHeader("X-Forwarded-For")
        if((ip == null || ip.isEmpty() || "0:0:0:0:0:0:0:1" == ip) || "127.0.0.1" == ip){
            return
        }
        val param : MutableMap<String, Any> = mutableMapOf()
        param["userAgent"] = request.getHeader("user-agent")

        if(dao.checkVisit(param) == 0){
            dao.insertVisitLog(param)
        } else {
            dao.updateVisitLog(param)
        }
    }

    fun insertRequestData(param : Tmap) : Int {
        val name = param.getString("title")
        param["name"] = name

        val nameArrs : List<String> = name.split(" ")
        if(nameArrs.size > 1){
            param["nameList"] = nameArrs
        }

        param["px"] = StringBuilder(param.getString("mapx")).insert(3, ".").toString()
        param["py"] = StringBuilder(param.getString("mapy")).insert(2, ".").toString()

        val exist : List<MapData> = dao.selectMapDataExist(param)
        return if(exist.isEmpty()){
            dao.insertRequestData(param)
        } else {
            0
        }
    }









}