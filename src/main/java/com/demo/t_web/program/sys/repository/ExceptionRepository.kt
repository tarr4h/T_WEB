package com.demo.t_web.program.sys.repository

import com.demo.t_web.program.sys.model.ExceptionHst
import org.springframework.data.jpa.repository.JpaRepository

interface ExceptionRepository : JpaRepository<ExceptionHst, Long>