package com.fumbbl.ffbproxy.ffb

import org.jsoup.Connection
import org.springframework.stereotype.Component
import java.net.URL

@Component
class GameLocator(private val jsoupConnection: Connection) {
    private val CACHE_PATH = "/admin/cache"

    fun hasGame(id: Long, ffb: FfbConnection): Boolean {
        return containsAttributeValue(ffb, "id", id.toString())
    }

    fun hasGame(name: String, ffb: FfbConnection): Boolean {
        return containsAttributeValue(ffb, "name", name)
    }

    private fun containsAttributeValue(ffb: FfbConnection, name: String, value: String): Boolean {
        val cacheUrl = URL(ffb.apiBaseUrl.toString()+CACHE_PATH)

        return jsoupConnection.newRequest().url(cacheUrl).get().select("game").stream()
            .map { element -> element.attr(name) }.anyMatch { attrValue -> attrValue.equals(value) }
    }
}