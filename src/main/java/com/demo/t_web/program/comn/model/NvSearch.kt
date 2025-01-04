package com.demo.t_web.program.comn.model


data class NvSearch(
    var display : Int,
    var lastBuildDate : String,
    var start : Int,
    var total : Int,
    var items : List<NvSearchDetail>
)
