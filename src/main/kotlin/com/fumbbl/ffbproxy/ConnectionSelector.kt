package com.fumbbl.ffbproxy

import com.fumbbl.ffbproxy.config.Connections
import com.fumbbl.ffbproxy.ffb.FfbConnection
import com.fumbbl.ffbproxy.ffb.GameLocator
import org.springframework.stereotype.Component

@Component
class ConnectionSelector(private val locator: GameLocator, private val config: Connections) {

    fun select(id: Long): FfbConnection {
        return select({ connection -> locator.hasGame(id, connection) })
    }

    fun select(name: String): FfbConnection {
        return select({ connection -> locator.hasGame(name, connection) })
    }

    private fun select(predicate: (FfbConnection) -> Boolean): FfbConnection {
        return config.availableConnections.stream().filter { connection -> config.activeConnections.contains(connection.name)}.filter(predicate).findFirst()
            .orElse(config.getPrimary())
    }

}