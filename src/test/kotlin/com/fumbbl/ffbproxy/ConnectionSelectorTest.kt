package com.fumbbl.ffbproxy

import com.fumbbl.ffbproxy.config.Connections
import com.fumbbl.ffbproxy.ffb.FfbConnection
import com.fumbbl.ffbproxy.ffb.GameLocator
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith

@ExtendWith(MockKExtension::class)
internal class ConnectionSelectorTest {

    private val FIRST = "first"
    private val SECOND = "second"

    private lateinit var selector: ConnectionSelector

    @MockK
    private lateinit var firstNode: FfbConnection

    @MockK
    private lateinit var secondNode: FfbConnection

    @MockK
    private lateinit var config: Connections

    @RelaxedMockK
    private lateinit var locator: GameLocator

    @BeforeEach
    fun setup() {
        every { firstNode.name } returns FIRST
        every { secondNode.name } returns SECOND

        every { config.availableConnections } returns listOf(firstNode, secondNode)
        every { config.activeConnections } returns setOf(FIRST, SECOND)
        every { config.getPrimary() } returns secondNode
        every { config.primaryName } returns SECOND

        selector = ConnectionSelector(locator, config)
    }

    @Test
    fun selectFirstAvailableById() {
        every { locator.hasGame(any<Long>(), firstNode) } returns true
        assertSame(firstNode, selector.select(1))
    }

    @Test
    fun selectSecondAvailableById() {
        every { locator.hasGame(any<Long>(), secondNode) } returns true
        assertSame(secondNode, selector.select(1))
    }

    @Test
    fun selectPrimaryIfIdIsNotFound() {
        assertSame(secondNode, selector.select(1))
    }

    @Test
    fun selectFirstAvailableByName() {
        every { locator.hasGame(any<String>(), firstNode) } returns true
        assertSame(firstNode, selector.select(""))
    }

    @Test
    fun selectSecondAvailableByName() {
        every { locator.hasGame(any<String>(), secondNode) } returns true
        assertSame(secondNode, selector.select(""))
    }

    @Test
    fun selectPrimaryIfNameIsNotFound() {
        assertSame(secondNode, selector.select(""))
    }
}