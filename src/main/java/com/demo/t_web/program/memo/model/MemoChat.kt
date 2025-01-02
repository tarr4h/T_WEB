package com.demo.t_web.program.memo.model

import jakarta.persistence.*
import kotlin.jvm.Transient

@Entity
@Table(name = "s_memo_chat")
@SequenceGenerator(name = "chat_generator", sequenceName = "chat_seq", initialValue = 1, allocationSize = 1)
data class MemoChat(
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "chat_generator")
    @Column(name = "id", nullable = false)
    val id : Long = 0L,

    @Column(name = "type", nullable = false)
    val type : Int,

    @Column(name = "content", length = 1000)
    val content : String,

    @Transient
    val memos : MutableList<Memo> = mutableListOf()
)
