package com.example.hellospring;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class HelloSpringApplicationTests {
    @Autowired private MockMvc mockMvc;
    @Autowired private MessageRepository repository;
    @Autowired private MessageService service;

    @BeforeEach
    void resetRepository() {
        repository.clear();
        repository.save(new Message("Hello, Spring!", "System"));
        repository.save(new Message("In-memory storage works", "System"));
    }

    @Test
    void helloEndpoint() throws Exception {
        mockMvc.perform(get("/api/").contextPath("/api"))
                .andExpect(status().isOk())
                .andExpect(content().string("Hello, World!"));
    }

    @Test
    void messagesEndpointReturnsSampleData() throws Exception {
        mockMvc.perform(get("/api/messages").contextPath("/api"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].content").value("Hello, Spring!"))
                .andExpect(jsonPath("$[1].author").value("System"));
    }

    @Test
    void createsMessageWithAuthor() throws Exception {
        mockMvc.perform(post("/api/messages").contextPath("/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\":\"Привет из теста\",\"author\":\"Арсений\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.content").value("Привет из теста"))
                .andExpect(jsonPath("$.author").value("Арсений"));
    }

    @Test
    void rejectsMessageWithoutAuthor() throws Exception {
        mockMvc.perform(post("/api/messages").contextPath("/api")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"content\":\"Текст\",\"author\":\"\"}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.error").exists());
    }

    @Test
    void returnsOnlyLastThirtyMessages() throws Exception {
        repository.clear();
        for (int i = 1; i <= 35; i++) service.createMessage(new Message("Сообщение " + i, "Автор"));
        mockMvc.perform(get("/api/messages").contextPath("/api"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(30)))
                .andExpect(jsonPath("$[0].content").value("Сообщение 6"))
                .andExpect(jsonPath("$[29].content").value("Сообщение 35"));
    }
}
