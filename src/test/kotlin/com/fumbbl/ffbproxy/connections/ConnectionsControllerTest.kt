package com.fumbbl.ffbproxy.connections

import com.fumbbl.ffbproxy.config.Connections
import com.fumbbl.ffbproxy.ffb.FfbConnection
import com.google.gson.Gson
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers
import java.net.URL


@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
internal class ConnectionsControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun connections() {
        val response =
            mockMvc.perform(MockMvcRequestBuilders.get("/connections")).andExpect(MockMvcResultMatchers.status().isOk)
                .andReturn().response.contentAsString

        val connections: Connections = Gson().fromJson(response, Connections::class.java)
        assertEquals("conn2", connections.primaryName)
        assertEquals(
            listOf(
                FfbConnection("conn1", URL("http://localhost:22226"), URL("http://localhost:22227/jnlp1")),
                FfbConnection("conn2", URL("http://localhost:22228"), URL("http://localhost:22229/jnlp2"))
            ), connections.availableConnections
        )
    }

    @Test
    fun setPrimary() {
        val response = mockMvc.perform(MockMvcRequestBuilders.get("/connections/primary/conn1"))
            .andExpect(MockMvcResultMatchers.status().isOk)
            .andReturn().response.contentAsString

        val connections: Connections = Gson().fromJson(response, Connections::class.java)
        assertEquals("conn1", connections.primaryName)
        assertEquals(
            listOf(
                FfbConnection("conn1", URL("http://localhost:22226"), URL("http://localhost:22227/jnlp1")),
                FfbConnection("conn2", URL("http://localhost:22228"), URL("http://localhost:22229/jnlp2"))
            ), connections.availableConnections
        )
    }

    @Test
    fun setInvalidPrimary() {
        mockMvc.perform(MockMvcRequestBuilders.get("/connections/primary/invalid"))
            .andExpect(MockMvcResultMatchers.status().isBadRequest)
            .andExpect(MockMvcResultMatchers.content().string("No connection defined for 'invalid'"))
    }
}