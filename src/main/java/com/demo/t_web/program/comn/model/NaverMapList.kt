package com.demo.t_web.program.comn.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty

data class NaverMapList(
    @JsonIgnore
    var folder : Map<String, Any>,
    @JsonProperty("bookmarkList")
    var bookmarkList : List<NaverMap>,
    @JsonIgnore
    var unavailableCount : Int,
    @JsonIgnore
    var removed : Boolean
)