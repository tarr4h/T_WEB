package com.demo.t_web.program.sys.service

import com.demo.t_web.comn.util.Utilities
import com.demo.t_web.program.sys.model.ExceptionHst
import com.demo.t_web.program.sys.repository.ExceptionRepository
import io.github.oshai.kotlinlogging.KotlinLogging
import jakarta.servlet.http.HttpServletRequest
import lombok.extern.slf4j.Slf4j
import org.springframework.stereotype.Service
import java.io.PrintWriter
import java.io.StringWriter
import java.util.*


@Service
@Slf4j
class ExceptionService (
    private var exceptionRepository: ExceptionRepository
){
    private val log = KotlinLogging.logger {}

    fun addExceptionHst(e : Exception, request : HttpServletRequest) {
        try {
            val uri = request.requestURI
            val exception = e.javaClass.simpleName
            val sw = StringWriter()

            e.printStackTrace(PrintWriter(sw))
            var msg = sw.toString()
            if(msg.length > 4000) msg = msg.substring(0, 4000)

            val params : Enumeration<String> = request.parameterNames
            val values : MutableList<String> = mutableListOf()
            while(params.hasMoreElements()) {
                val name = params.nextElement()
                values.add(name + ":" + request.getParameter(name))
            }

            var paramStr = Utilities.listToSeparatedString(values, ",")
            if(paramStr.length > 4000){
                paramStr = paramStr.substring(0, 4000)
            }

            exceptionRepository.save(ExceptionHst(
                uri = uri,
                exception = exception,
                msg = msg,
                params = paramStr,
            ))

            log.error { "exception occured ===================" }
            log.error { "=> $e" }
            log.error { "=====================================" }
        } catch (e1 : Exception) {
            log.debug { "addExceptionHst error => $e1" }
        }

    }
}