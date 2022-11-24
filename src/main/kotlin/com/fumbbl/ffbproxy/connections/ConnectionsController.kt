package com.fumbbl.ffbproxy.connections

import com.fumbbl.ffbproxy.config.Connections
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class ConnectionsController(val connections: Connections) {

    @RequestMapping("/connections")
    fun connections(): Connections {
        return connections
    }

    @RequestMapping("/connections/primary/{newPrimary}")
    fun setPrimary(@PathVariable newPrimary: String): Connections {
        connections.primaryName = newPrimary
        return connections
    }

}
