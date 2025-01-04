package com.demo.t_web.program.comn.model

import java.io.Serializable
import java.util.*

data class ExcludeCategory (
    var categoryName : String,
    var relatedMcid : String,
    var updatedAt : Date
) : Serializable