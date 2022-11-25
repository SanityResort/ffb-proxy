package com.fumbbl.ffbproxy

import io.mockk.Called
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import io.mockk.verify
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@SpringBootTest
@ExtendWith(MockKExtension::class)
internal class AccessFilterTest {

    @Value("\${server.publicPort}")
    private val publicPort: Int = 0

    @Value("\${server.port:8080}")
    private val port: Int = 0

    @Autowired
    private lateinit var filter: AccessFilter

    @MockK
    private lateinit var request: HttpServletRequest

    @MockK
    private lateinit var response: HttpServletResponse

    @MockK
    private lateinit var chain: FilterChain

    @Test
    fun doFilterAllowPublicOnAnyPort() {
        every { request.requestURI } returns "/public/"
        every { chain.doFilter(request, response) } returns Unit

        filter.doFilter(request, response, chain)

        verify { response wasNot Called }
        verify(exactly = 1) { chain.doFilter(request, response) }
    }

    @Test
    fun doFilterAllowRestrictedOnRestrictedPort() {
        every { request.requestURI } returns "/connections"
        every { request.localPort } returns port
        every { chain.doFilter(request, response) } returns Unit

        filter.doFilter(request, response, chain)

        verify { response wasNot Called }
        verify(exactly = 1) { chain.doFilter(request, response) }
    }

    @Test
    fun doFilterBlockRestrictedOnPublicPort() {
        every { request.requestURI } returns "/connections"
        every { request.localPort } returns publicPort
        every {response.sendError(403) } returns Unit

        filter.doFilter(request, response, chain)

        verify { chain wasNot Called }
        verify(exactly = 1) { response.sendError(403) }
    }

    @Test
    fun doFilterAllowNullOnAnyPort() {
        every { request.requestURI } returns null
        every { chain.doFilter(request, response) } returns Unit

        filter.doFilter(request, response, chain)

        verify { response wasNot Called }
        verify(exactly = 1) { chain.doFilter(request, response) }
    }

}