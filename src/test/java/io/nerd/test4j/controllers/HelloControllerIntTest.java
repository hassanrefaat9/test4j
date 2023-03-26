package io.nerd.test4j.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(HelloController.class)
class HelloControllerIntTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void hello() throws Exception {
        RequestBuilder request = MockMvcRequestBuilders.get("/hello");
        MvcResult result = mockMvc.perform(request).andReturn();
        assertEquals("Hello, World", result.getResponse().getContentAsString());
    }

    @Test
    void testHelloWithName() throws Exception {
        var request = MockMvcRequestBuilders.get("/hello?name=Hassan");
        var result = mockMvc.perform(request).andReturn();
        assertEquals("Hello, Hassan", result.getResponse().getContentAsString());

    }
}