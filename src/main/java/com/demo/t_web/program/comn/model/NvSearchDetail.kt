package com.demo.t_web.program.comn.model

import com.demo.t_web.comn.util.Utilities
import com.fasterxml.jackson.annotation.JsonIgnore

data class NvSearchDetail(
    var address : String?,
    var category : String,
    var description : String,
    var link : String,
    var mapx : String,
    var mapy : String,
    var roadAddress : String?,
    var telephone : String,
    var title : String
) {
    @JsonIgnore
    fun getLatStr() : String {
        return StringBuilder(mapy).insert(2, ".").toString()
    }

    @JsonIgnore
    fun getLngStr() : String {
        return StringBuilder(mapx).insert(3, ".").toString()
    }

    @JsonIgnore
    fun getLat() : Double {
        return Utilities.parseDouble(getLatStr())
    }

    @JsonIgnore
    fun getLng() : Double {
        return Utilities.parseDouble(getLngStr())
    }
}
