package com.fumbbl.ffbproxy

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
class AccessFilter: Filter {

    @Value("\${server.publicPort}")
    private val publicPort: Int = 0

    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val requestURI = (request as HttpServletRequest).requestURI
        if (requestURI != null && !requestURI.startsWith("/public/") && request.localPort == publicPort) {
            (response as HttpServletResponse).sendError(403)
        } else {
            chain?.doFilter(request, response)
        }
    }
}