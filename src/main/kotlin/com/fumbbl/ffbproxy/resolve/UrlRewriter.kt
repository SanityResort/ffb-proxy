package com.fumbbl.ffbproxy.resolve

import com.fumbbl.ffbproxy.ffb.FfbConnection
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import java.net.URL

@Component
class UrlRewriter {

    fun buildFfbUrl(originalUrl: URL, ffbConnection: FfbConnection): URL {

        val scheme = ffbConnection.apiBaseUrl.protocol.ifBlank { originalUrl.protocol }
        val host = ffbConnection.apiBaseUrl.host.ifBlank { originalUrl.host }
        val port = if (ffbConnection.apiBaseUrl.port > 0) ffbConnection.apiBaseUrl.port else originalUrl.port
        val path = ffbConnection.apiBaseUrl.path + originalUrl.path
        val query =
            if (ffbConnection.apiBaseUrl.query == null || ffbConnection.apiBaseUrl.query.isBlank()) originalUrl.query else ffbConnection.apiBaseUrl.query + "&" + originalUrl.query

        return UriComponentsBuilder.newInstance()
            .scheme(scheme)
            .host(host)
            .port(port)
            .path(path)
            .query(query)
            .build().toUri().toURL()
    }

}