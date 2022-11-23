package com.fumbbl.ffbproxy.config

import com.fumbbl.ffbproxy.ffb.FfbConnection
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import java.lang.IllegalArgumentException

@ConstructorBinding
@ConfigurationProperties(prefix = "ffb")
data class Connections(var availableConnections: List<FfbConnection>, var primaryName: String) {

    var activeConnections: List<String> = emptyList()
    var primary: FfbConnection

    init {
        require(availableConnections.isNotEmpty()) { "No available connection configured" }
        require(primaryName.isNotEmpty()) { "Missing primary declaration" }
        primary = availableConnections.stream().filter { connection -> connection.name.equals(primaryName) }.findFirst()
            .orElseThrow { IllegalArgumentException("Primary does not refer to an available connection") }
        activeConnections = activeConnections.plus(primary.name)
    }
}