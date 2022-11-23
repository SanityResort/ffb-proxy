package com.fumbbl.ffbproxy

import com.fumbbl.ffbproxy.config.Connections
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication

@SpringBootApplication
@EnableConfigurationProperties(Connections::class)
class FfbProxyApplication

fun main(args: Array<String>) {
	runApplication<FfbProxyApplication>(*args)
}
