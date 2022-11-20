package com.fumbbl.ffbproxy.ffb

import org.jsoup.Connection
import org.springframework.stereotype.Component

@Component
class GameLocator(private val jsoupConnection: Connection) {

    fun hasGame(id: Long, ffb: FfbConnection): Boolean {
        return containsAttributeValue(ffb, "id", id.toString())
    }

    fun hasGame(name: String, ffb: FfbConnection): Boolean {
        return containsAttributeValue(ffb, "name", name)
    }

    private fun containsAttributeValue(ffb: FfbConnection, name: String, value: String): Boolean {
        return jsoupConnection.newRequest().url(ffb.url).get().select("game").stream()
            .map { element -> element.attr(name) }.anyMatch { attrValue -> attrValue.equals(value) }
    }
}