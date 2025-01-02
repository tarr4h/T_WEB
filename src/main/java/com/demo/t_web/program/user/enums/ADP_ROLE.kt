package com.demo.t_web.program.user.enums

import java.util.*

enum class ADP_ROLE(
    var id : String,
    var roleName : String,
    val level : Int
) {

    PUBLIC("PUBLIC", "전체", 1),
    USER("USER", "사용자", 2),
    ADMIN("ADMIN", "관리자", 99),
    PRIVATE("PRIV", "숨김", 999);

    companion object {
        fun getRoles(vararg role : ADP_ROLE) = role.toList()
        @OptIn(ExperimentalStdlibApi::class)
        fun getRolesByLevel(level: Int) = Arrays.stream(entries.toTypedArray())
            .filter { it.level <= level }.toList()
    }
}