package com.fumbbl.ffbproxy.ffb

import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.jsoup.Connection
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import java.net.URL
import java.nio.charset.StandardCharsets

@ExtendWith(MockKExtension::class)
internal class GameLocatorTest {

    var ffbUrl = URL("https://localhost")

    var ffbConnection = FfbConnection("name", ffbUrl)

    lateinit var gameLocator: GameLocator
    @MockK
    lateinit var jsoupConnection: Connection
    @MockK
    lateinit var newRequest: Connection

    @MockK
    lateinit var url: Connection

    lateinit var document: Document

    @BeforeEach
    fun setup() {
        document = Jsoup.parse(Thread.currentThread().contextClassLoader.getResourceAsStream("gameCache.xml")!!, StandardCharsets.UTF_8.toString(), "")
        every { jsoupConnection.newRequest() } returns newRequest
        every { newRequest.url(ffbUrl) } returns url
        every { url.get() } returns document

        gameLocator = GameLocator(jsoupConnection)
    }

    @Test
    fun hasGameWithId() {
        assertTrue(gameLocator.hasGame(1459, ffbConnection))
    }

    @Test
    fun hasGameWithName() {
        assertTrue(gameLocator.hasGame("foo", ffbConnection))
    }

    @Test
    fun hasNoGameWithId() {
        assertFalse(gameLocator.hasGame(1, ffbConnection))
    }

    @Test
    fun hasNoGameWithName() {
        assertFalse(gameLocator.hasGame("bar", ffbConnection))
    }


}

