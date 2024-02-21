package com.example.recrutationtask.controller;

import com.example.recrutationtask.model.command.CreateChildCommand;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ActiveProfiles("test")
@SpringBootTest
@AutoConfigureMockMvc
class ChildControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getAllChildrenTest() throws Exception {
        mockMvc.perform(get("http://localhost:8080/api/v1/child")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content").exists());
    }

    @Test
    void addSchoolTest() throws Exception {
        UUID testSchoolId = UUID.fromString("a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a11");
        UUID testParentId = UUID.fromString("a0eebc99-9c0b-4ef8-bb6d-6bb9bd380a15");
        CreateChildCommand command = new CreateChildCommand();
        command.setFirstName("Test");
        command.setLastName("Child");
        command.setSchoolId(testSchoolId);
        command.setParentId(testParentId);

        mockMvc.perform(post("http://localhost:8080/api/v1/child")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(command)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.firstName").value("Test"))
                .andExpect(jsonPath("$.lastName").value("Child"))
                .andExpect(jsonPath("$.schoolId").value(testSchoolId.toString()))
                .andExpect(jsonPath("$.parentId").value(testParentId.toString()));
    }
}