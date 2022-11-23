package com.fumbbl.ffbproxy

import com.fumbbl.ffbproxy.config.*
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SpringConfig {

    @Value("\${ffb.timeout}")
    private val timeout: Int = 0

    @Bean
    fun jsoupConnection(): Connection {
        return Jsoup.newSession().timeout(timeout).userAgent("FFB Proxy")
    }
}