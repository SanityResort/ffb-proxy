package com.fumbbl.ffbproxy

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory
import org.springframework.boot.web.servlet.server.ServletWebServerFactory

@SpringBootTest
class FfbProxyApplicationTests {

	@Value("\${server.publicPort}")
	val publicPort: Int = 0

	@Test
	fun contextLoads() {
	}

	@Test
	fun additionalConnector(@Autowired servletContainerFactory: ServletWebServerFactory) {
		assertTrue(servletContainerFactory is TomcatServletWebServerFactory)
		val webserverFactory: TomcatServletWebServerFactory = servletContainerFactory as TomcatServletWebServerFactory
		assertEquals(1, webserverFactory.additionalTomcatConnectors.size)
		assertEquals(publicPort, webserverFactory.additionalTomcatConnectors[0].port)
	}
}
