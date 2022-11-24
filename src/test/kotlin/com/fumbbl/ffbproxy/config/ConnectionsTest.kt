package com.fumbbl.ffbproxy.config

import com.fumbbl.ffbproxy.ffb.FfbConnection
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Disabled
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.net.URL

internal class ConnectionsTest {

    private val ffb1 = FfbConnection("conn1", URL("http://localhost:8080/cache1"), URL("http://localhost:8080/jnlp1"))
    private val ffb2 = FfbConnection("conn2", URL("http://localhost:8080/cache2"), URL("http://localhost:8080/jnlp2"))

    private val availableConnections = listOf(ffb1, ffb2)

    @Disabled
    @Test
    fun addPrimaryAsActive() {
        val connections = Connections(availableConnections, ffb1.name)
        assertEquals(setOf(ffb1.name), connections.activeConnections)
    }

    @Test
    fun setPrimaryFromName() {
        val connections = Connections(availableConnections, ffb2.name)
        assertEquals(ffb2, connections.getPrimary())
    }

    @Test
    fun missingPrimary() {
        val exception = assertThrows<IllegalArgumentException> { Connections(availableConnections, "") }

        assertEquals("Missing primary declaration", exception.message)
    }

    @Test
    fun missingAvailableInstances() {
        val exception = assertThrows<IllegalArgumentException> { Connections(emptyList(), "someName") }

        assertEquals("No available connection configured", exception.message)
    }

    @Test
    fun primaryNotAvailable() {
        val exception = assertThrows<IllegalArgumentException> { Connections(availableConnections, "someName") }

        assertEquals("Primary does not refer to an available connection", exception.message)

    }

    @Test
    fun changePrimary() {
        val connections = Connections(availableConnections, ffb2.name)
        connections.primaryName = ffb1.name
        assertEquals(ffb1, connections.getPrimary())
    }

    @Test
    fun changePrimaryToInvalidValue() {
        val connections = Connections(availableConnections, ffb2.name)
        val exception = assertThrows<IllegalArgumentException> { connections.primaryName = "unknown" }

        assertEquals("No connection defined for 'unknown'", exception.message)
    }

}