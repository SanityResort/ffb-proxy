package com.fumbbl.ffbproxy.config

import com.fumbbl.ffbproxy.ffb.FfbConnection
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding

@ConstructorBinding
@ConfigurationProperties(prefix = "ffb")
class Connections(var availableConnections: List<FfbConnection>, primaryName: String) {

    var activeConnections: Set<String> = emptySet()

    var primaryName = primaryName
        set(value) {
            val newPrimary =
                availableConnections.stream().filter { connection -> connection.name.equals(value) }.findFirst()
            if (newPrimary.isPresent) {
                field = value
                primary = newPrimary.get()
            //        activeConnections = activeConnections.plus(primary.name)
            } else {
                throw IllegalArgumentException("No connection defined for '$value'")
            }
        }

    private var primary: FfbConnection

    fun getPrimary(): FfbConnection {
        return primary
    }

    init {
        require(availableConnections.isNotEmpty()) { "No available connection configured" }
        require(primaryName.isNotEmpty()) { "Missing primary declaration" }
        primary = availableConnections.stream().filter { connection -> connection.name.equals(primaryName) }.findFirst()
            .orElseThrow { IllegalArgumentException("Primary does not refer to an available connection") }
    //        activeConnections = activeConnections.plus(primary.name) // disabled test exists
    }

}