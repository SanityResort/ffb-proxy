package com.fumbbl.ffbproxy.redirect

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
internal class RedirectControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Test
    fun jnlp() {
        mockMvc.perform(MockMvcRequestBuilders.get("/public/jnlp?id=12345"))
            .andExpect(MockMvcResultMatchers.status().isFound)
            .andExpect(MockMvcResultMatchers.header().string("Location", "http://localhost:22229/jnlp2?id=12345"))
    }

    @Test
    fun ffb() {
        mockMvc.perform(MockMvcRequestBuilders.get("/ffb/servlet/path?id=12345"))
            .andExpect(MockMvcResultMatchers.status().isFound)
            .andExpect(MockMvcResultMatchers.header().string("Location", "http://localhost:22228/servlet/path?id=12345"))
    }
}