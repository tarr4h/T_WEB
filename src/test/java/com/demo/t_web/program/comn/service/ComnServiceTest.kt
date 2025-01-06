package com.demo.t_web.program.comn.service

import com.demo.t_web.program.comn.dao.ComnDao
import com.demo.t_web.program.memo.service.MemoService
import org.junit.Test
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.context.annotation.ComponentScan
import org.springframework.test.annotation.Rollback
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.context.junit4.SpringRunner
import org.springframework.transaction.annotation.Transactional

@RunWith(SpringRunner::class)
@SpringBootTest
@ActiveProfiles("local")
class ComnServiceTest{

    @Autowired
    private lateinit var memoService: MemoService


    @Test
    @Transactional
    @Rollback(false)
    fun test (){
        memoService.insertVs()
    }
}