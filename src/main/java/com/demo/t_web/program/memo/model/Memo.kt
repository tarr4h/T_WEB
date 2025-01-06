package com.demo.t_web.program.memo.model

import com.demo.t_web.program.sys.model.BaseVo
import jakarta.persistence.*


@Entity
@Table(name = "s_memo")
@SequenceGenerator(name = "memo_seq", sequenceName = "memo_seq", allocationSize = 1)
data class Memo(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "memo_generator")
    @Column(name = "id", nullable = false)
    val id : Long = 0L,

    @Column(name = "title", length = 100, nullable = false)
    var title : String? = "",

    @Column(name = "content", length = 1000)
    var content : String? = ""

) : BaseVo()