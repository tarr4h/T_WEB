package com.demo.t_web.program.comn.model

import com.demo.t_web.comn.util.Utilities
import com.fasterxml.jackson.annotation.JsonIgnore
import java.io.Serializable
import java.sql.Timestamp
import java.time.LocalDateTime
import java.util.*

data class MapData(
    var id : String,
    var name : String,
    var px : String,
    var py : String,
    var address : String,
    var addr1 : String,
    var addr2 : String,
    var addr3 : String,
    var mcid : String,
    var mcidName : String,
    var available : String,
    var updatedAt : LocalDateTime? = null,
    var centerDistance : Double = 0.0,
    @JsonIgnore
    var driving : DrivingVo? = null
) : Serializable, Comparable<MapData> {

    // 명시적 생성자
    constructor(
        id: String, name: String, px: String, py: String, address: String,
        addr1: String, addr2: String, addr3: String, mcid: String,
        mcidName: String, available: String, updatedAt: LocalDateTime?
    ) : this(
        id, name, px, py, address, addr1, addr2, addr3, mcid, mcidName, available, updatedAt, 0.0, null
    )

    fun getDLat() : Double{
        return Utilities.parseDouble(py)
    }

    fun getDLng() : Double{
        return Utilities.parseDouble(px)
    }

    override fun compareTo(o: MapData): Int {
        return if(centerDistance < o.centerDistance){
            -1
        } else if(centerDistance == o.centerDistance){
            0
        } else {
            1
        }
    }
}