package com.fumbbl.ffbproxy.resolve

import com.fumbbl.ffbproxy.ffb.FfbConnection
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.extension.ExtendWith
import java.net.URL

@ExtendWith(MockKExtension::class)
internal class UrlRewriterTest {

    private fun urlRewriter() = UrlRewriter()

    private val originalUrl = URL("http://orignalHost:8080/servletPath")
    @MockK
    private lateinit var ffbConnection: FfbConnection

    @Test
    fun buildFfbUrl() {
        every { ffbConnection.apiBaseUrl } returns URL("https://redirectHost:8081/basePath")
        assertEquals(URL("https://redirectHost:8081/basePath/servletPath"), urlRewriter().buildFfbUrl(originalUrl, ffbConnection))
    }

    @Test
    fun buildFfbUrlWithoutBasePath() {
        every { ffbConnection.apiBaseUrl } returns URL("https://redirectHost:8081")
        assertEquals(URL("https://redirectHost:8081/servletPath"), urlRewriter().buildFfbUrl(originalUrl, ffbConnection))
    }

    @Test
    fun buildFfbUrlWithQueries() {
        every { ffbConnection.apiBaseUrl } returns URL("https://redirectHost:8081/basePath?base=value1")
        assertEquals(URL("https://redirectHost:8081/basePath/servletPath?base=value1&orignal=value2"),
            urlRewriter().buildFfbUrl(URL("http://orignalHost:8080/servletPath?orignal=value2"), ffbConnection))
    }

    @Test
    fun buildFfbUrlFromDomainOnly() {
        every { ffbConnection.apiBaseUrl } returns URL("https://redirectHost")
        assertEquals(URL("https://redirectHost:8080/servletPath"),
            urlRewriter().buildFfbUrl(originalUrl, ffbConnection))
    }

    @Test
    fun buildJnlpUrl() {
        every { ffbConnection.jnlpUrl } returns URL("https://redirectHost:8081/basePath")
        assertEquals(URL("https://redirectHost:8081/basePath/servletPath"), urlRewriter().buildJnlpUrl(originalUrl, ffbConnection))
    }

    @Test
    fun buildJnlpUrlWithoutBasePath() {
        every { ffbConnection.jnlpUrl } returns URL("https://redirectHost:8081")
        assertEquals(URL("https://redirectHost:8081/servletPath"), urlRewriter().buildJnlpUrl(originalUrl, ffbConnection))
    }

    @Test
    fun buildJnlpUrlWithQueries() {
        every { ffbConnection.jnlpUrl } returns URL("https://redirectHost:8081/basePath?base=value1")
        assertEquals(URL("https://redirectHost:8081/basePath/servletPath?base=value1&orignal=value2"),
            urlRewriter().buildJnlpUrl(URL("http://orignalHost:8080/servletPath?orignal=value2"), ffbConnection))
    }

    @Test
    fun buildJnlpUrlFromDomainOnly() {
        every { ffbConnection.jnlpUrl } returns URL("https://redirectHost")
        assertEquals(URL("https://redirectHost:8080/servletPath"),
            urlRewriter().buildJnlpUrl(originalUrl, ffbConnection))
    }
}