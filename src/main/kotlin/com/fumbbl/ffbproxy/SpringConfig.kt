package com.fumbbl.ffbproxy

import com.fumbbl.ffbproxy.config.*
import org.apache.catalina.connector.Connector
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.boot.web.servlet.server.ServletWebServerFactory
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

    @Value("\${server.publicPort}")
    private val publicPort: Int = 0


    @Bean
    fun servletContainer(): ServletWebServerFactory {
        val tomcat = TomcatServletWebServerFactory()
        val additionalConnectors = additionalConnector()
        tomcat.addAdditionalTomcatConnectors(additionalConnectors)
        return tomcat
    }

    private fun additionalConnector(): Connector {
        val connector = Connector("org.apache.coyote.http11.Http11NioProtocol")
        connector.scheme = "http"
        connector.port = publicPort
        return connector
    }
}