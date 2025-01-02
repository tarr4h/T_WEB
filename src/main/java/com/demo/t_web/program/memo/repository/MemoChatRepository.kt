package com.demo.t_web.program.memo.repository

import com.demo.t_web.program.memo.model.MemoChat
import org.springframework.data.jpa.repository.JpaRepository

interface MemoChatRepository : JpaRepository<MemoChat, Long>