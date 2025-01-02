package com.demo.t_web.program.sys.model

import jakarta.persistence.*

@Entity
@Table(name = "exception_hst")
@SequenceGenerator(name = "exception_hst_generator", sequenceName = "exception_hst_seq", initialValue = 1, allocationSize = 1)
data class ExceptionHst(

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "exception_hst_generator")
    @Column(name = "id")
    var id : Long = 0L,

    @Column(name = "uri", length = 100)
    var uri : String,

    @Column(name = "exception", length = 100)
    var exception : String,

    @Column(name = "msg", length = 4000)
    var msg : String,

    @Column(name = "params", length = 4000)
    var params : String
) : BaseVo()
