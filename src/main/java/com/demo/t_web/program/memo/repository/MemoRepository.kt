package com.demo.t_web.program.memo.repository

import com.demo.t_web.program.memo.model.Memo
import org.springframework.data.jpa.repository.JpaRepository

interface MemoRepository : JpaRepository<Memo, Long>