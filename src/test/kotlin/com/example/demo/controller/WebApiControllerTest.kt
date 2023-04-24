package com.example.demo.controller

import org.hamcrest.CoreMatchers.containsString
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.get
import kotlin.test.Test

@WebMvcTest
class WebApiControllerTest {
    @Autowired
    lateinit var mockMvc: MockMvc

    @Test
    fun `hello`() {
        mockMvc.get("/api/hello").andExpect {
            status { isOk() }
            content { containsString("Hello World!") }
        }
    }
}