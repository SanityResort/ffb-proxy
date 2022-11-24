package com.fumbbl.ffbproxy.resolve

import com.fumbbl.ffbproxy.ffb.FfbConnection
import org.springframework.stereotype.Component
import org.springframework.web.util.UriComponentsBuilder
import java.net.URL

@Component
class UrlRewriter {

    fun buildFfbUrl(originalUrl: URL, ffbConnection: FfbConnection, stripPath: String?): URL {
        return buildUrl(originalUrl, ffbConnection.apiBaseUrl, stripPath)
    }

    fun buildJnlpUrl(originalUrl: URL, ffbConnection: FfbConnection, stripPath: String?): URL {
        return buildUrl(originalUrl, ffbConnection.jnlpUrl, stripPath)
    }

    private fun buildUrl(originalUrl: URL, baseUrl: URL, stripPath: String?): URL {

        val scheme = baseUrl.protocol.ifBlank { originalUrl.protocol }
        val host = baseUrl.host.ifBlank { originalUrl.host }
        val port = if (baseUrl.port > 0) baseUrl.port else originalUrl.port
        val path = baseUrl.path + originalUrl.path.replace(stripPath.orEmpty(), "")
        val query =
            if (baseUrl.query == null || baseUrl.query.isBlank()) originalUrl.query else baseUrl.query + "&" + originalUrl.query

        return UriComponentsBuilder.newInstance()
            .scheme(scheme)
            .host(host)
            .port(port)
            .path(path)
            .query(query)
            .build().toUri().toURL()
    }
}