package com.demo.t_web.program.comn.model

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty

data class NaverMap(
    @JsonProperty
    var bookmarkId : String,
    @JsonProperty
    var name : String,
    @JsonProperty
    var px : Double,
    @JsonProperty
    var py : Double,
    @JsonProperty
    var type : String,
    @JsonProperty
    var address : String,
    @JsonProperty
    var mcid : String,
    @JsonProperty
    var mcidName : String,
    @JsonProperty
    var available : Boolean,

    @JsonIgnore
    var displayName : String,
    @JsonIgnore
    var useTime : String,
    @JsonIgnore
    var lastUpdateTime : String,
    @JsonIgnore
    var creationTime : String,
    @JsonIgnore
    var order : Int,
    @JsonIgnore
    var sid : String,
    @JsonIgnore
    var memo : String,
    @JsonIgnore
    var url : String,
    @JsonIgnore
    var rcode : String,
    @JsonIgnore
    var cidPath : List<String>,
    @JsonIgnore
    var folderMappings : String,
    @JsonIgnore
    var placeInfo : Any,
    @JsonIgnore
    var isIndoor : Any
)