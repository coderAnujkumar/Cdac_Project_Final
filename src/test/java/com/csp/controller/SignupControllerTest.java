package com.csp.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;

@SpringBootTest
@AutoConfigureMockMvc
class SignupControllerTest {

    @Autowired
    private MockMvc mockMvc;

    // ✅ Test GET /signup
    @Test
    void signupPageLoads() throws Exception {
        mockMvc.perform(get("/signup"))
               .andExpect(status().isOk());
    }

    // ✅ Test POST /signup (FORM SUBMISSION)
    @Test
    void signupFormSubmitSuccess() throws Exception {
        mockMvc.perform(post("/signup")
                .param("name", "Test User")
                .param("email", "testuser" + System.currentTimeMillis() + "@gmail.com")
                .param("password", "123456")
                .param("mobile", "9876543210")
                .param("role", "USER")
        )
        .andExpect(status().isOk());
    }


}
