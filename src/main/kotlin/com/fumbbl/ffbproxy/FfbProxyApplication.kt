package com.fumbbl.ffbproxy

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FfbProxyApplication

fun main(args: Array<String>) {
	runApplication<FfbProxyApplication>(*args)
}
