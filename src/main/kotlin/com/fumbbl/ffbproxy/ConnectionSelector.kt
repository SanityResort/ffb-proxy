package com.fumbbl.ffbproxy

import com.fumbbl.ffbproxy.ffb.FfbConnection
import com.fumbbl.ffbproxy.ffb.GameLocator
import org.springframework.stereotype.Component

@Component
class ConnectionSelector(private val locator: GameLocator, private val config: ConnectionConfig) {

    fun select(id: Long): FfbConnection {
        return select({ connection -> locator.hasGame(id, connection) })
    }

    fun select(name: String): FfbConnection {
        return select({ connection -> locator.hasGame(name, connection) })
    }

    private fun select(predicate: (FfbConnection) -> Boolean): FfbConnection {
        return config.running.stream().filter(predicate).findFirst()
            .orElse(config.primary)
    }

}