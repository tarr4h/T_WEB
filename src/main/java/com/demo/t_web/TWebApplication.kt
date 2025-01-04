package com.demo.t_web

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.cache.annotation.EnableCaching

@SpringBootApplication
@EnableCaching
@ConfigurationPropertiesScan("com.demo", "com.demo")
class TWebApplication {

    companion object{
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(TWebApplication::class.java, *args)
        }
    }

}