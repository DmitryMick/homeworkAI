package com.example.userstore.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.example.userstore.model.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.security.test.context.support.WithMockUser;
import static org.junit.jupiter.api.Assertions.assertTrue;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @WithMockUser
    void getAllUsers_returnsOk() throws Exception {
        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void createUser_returnsCreated() throws Exception {
        User user = new User();
        user.setName("Test User");
        user.setUsername("testuser2");
        user.setEmail("testuser2@example.com");
        user.setPhone("123-456-7890");
        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isCreated());
    }

    @Test
    @WithMockUser
    void getUserById_returnsOkOrNotFound() throws Exception {
        mockMvc.perform(get("/users/1"))
                .andExpect(result -> {
                    int s = result.getResponse().getStatus();
                    assertTrue(s == 200 || s == 404);
                });
    }

    @Test
    @WithMockUser
    void getUsersByCity_returnsOk() throws Exception {
        mockMvc.perform(get("/users?city=TestCity"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void getUsersByName_returnsOk() throws Exception {
        mockMvc.perform(get("/users?name=Test"))
                .andExpect(status().isOk());
    }

    @Test
    @WithMockUser
    void getUserByUsername_returnsOkOrNotFound() throws Exception {
        mockMvc.perform(get("/users?username=testuser2"))
                .andExpect(result -> {
                    int s = result.getResponse().getStatus();
                    assertTrue(s == 200 || s == 404);
                });
    }

    @Test
    @WithMockUser
    void getUserByEmail_returnsOkOrNotFound() throws Exception {
        mockMvc.perform(get("/users?email=testuser2@example.com"))
                .andExpect(result -> {
                    int s = result.getResponse().getStatus();
                    assertTrue(s == 200 || s == 404);
                });
    }

    @Test
    @WithMockUser
    void updateUser_returnsOkOrNotFound() throws Exception {
        User user = new User();
        user.setName("Updated User");
        user.setUsername("testuser2");
        user.setEmail("testuser2@example.com");
        user.setPhone("123-456-7890");
        mockMvc.perform(put("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(result -> {
                    int s = result.getResponse().getStatus();
                    assertTrue(s == 200 || s == 404);
                });
    }

    @Test
    @WithMockUser
    void patchUser_returnsOkOrNotFound() throws Exception {
        User user = new User();
        user.setName("Patched User");
        mockMvc.perform(patch("/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(result -> {
                    int s = result.getResponse().getStatus();
                    assertTrue(s == 200 || s == 404);
                });
    }

    @Test
    @WithMockUser
    void deleteUser_returnsNoContentOrNotFound() throws Exception {
        mockMvc.perform(delete("/users/1"))
                .andExpect(result -> {
                    int s = result.getResponse().getStatus();
                    assertTrue(s == 204 || s == 404);
                });
    }
} 