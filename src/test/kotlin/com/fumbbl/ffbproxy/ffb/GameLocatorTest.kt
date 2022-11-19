package com.fumbbl.ffbproxy.ffb

import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.net.URL

internal class GameLocatorTest {

    val gameLocator = GameLocator()
    val ffbConnection: FfbConnection = mockk()

    @Test
    fun hasGameWithId() {
        assertTrue(gameLocator.hasGame(1459, ffbConnection));
    }

    @Test
    fun hasGameWithName() {
        assertTrue(gameLocator.hasGame("foo", ffbConnection));
    }

    @Test
    fun hasNoGameWithId() {
        assertFalse(gameLocator.hasGame(1, ffbConnection));
    }

    @Test
    fun hasNoGameWithName() {
        assertFalse(gameLocator.hasGame("bar", ffbConnection));
    }


}

