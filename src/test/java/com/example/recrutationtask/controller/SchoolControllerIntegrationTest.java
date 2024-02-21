package com.example.recrutationtask.controller;

import com.example.recrutationtask.model.command.CreateSchoolCommand;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class SchoolControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllSchoolsTest() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/v1/school")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists());
    }

    @Test
    void addSchoolTest() throws Exception {
        CreateSchoolCommand command = new CreateSchoolCommand();
        command.setName("Test School");
        command.setHourPrice(100.0);

        mockMvc.perform(post("http://localhost:8080/api/v1/school")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value("Test School"))
                .andExpect(jsonPath("$.hourPrice").value(100.0));
    }
}