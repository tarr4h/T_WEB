package com.demo.t_web.program.sys.model

import jakarta.persistence.Column
import jakarta.persistence.MappedSuperclass
import jakarta.persistence.PrePersist
import jakarta.persistence.Temporal
import jakarta.persistence.TemporalType
import java.io.Serializable
import java.util.*

@MappedSuperclass
open class BaseVo : Serializable {

    @Column(name = "reg_dt")
    @Temporal(TemporalType.TIMESTAMP)
    var regDt : Date? = null

    @Column(name = "upt_dt")
    @Temporal(TemporalType.TIMESTAMP)
    var uptDt : Date? = null

    @PrePersist
    open fun prePersist(){
        regDt ?: Date()
        uptDt ?: Date()
    }
}
