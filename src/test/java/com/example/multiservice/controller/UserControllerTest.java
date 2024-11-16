package com.example.multiservice.controller;

import com.example.multiservice.dto.request.UserRequest;
import com.example.multiservice.dto.response.UserResponse;
import com.example.multiservice.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/test.properties")
public class UserControllerTest {
    @Autowired
    private MockMvc mockMvc;


    @MockBean
    private UserService userService;

    // request
    private UserRequest userRequest;

    private UserRequest badUserRequest;


    private UserResponse userResponse;
    @BeforeEach
    void initData() {

        userRequest = UserRequest.builder()
                .first_name("nguyen")
                .middle_name("vannnn")
                .last_name("teonnnn")
                .mobile("0909090909")
                .email("nguyen@gmail.com")
                .password_hash("123456")
                .dob(LocalDate.of(1990, 1, 1))
                .registered_at("2024-10-28T14:30:00")
                .last_login("2024-10-28T16:45:00")
                .intro("con meo")
                .bio("meo con")
                .avatar_url("https://www.google.com")
                .social_links("https://www.facebook.com")
                .roles(new ArrayList<>(Arrays.asList(1, 2)))
                .build();

        userResponse = UserResponse.builder()
                .id(20)
                .first_name("nguyennn")
                .middle_name("vannnn")
                .last_name("teonnnn")
                .mobile("0909090909")
                .email("nguyen@gmail.com")
                .dob(LocalDate.of(1990, 1, 1))
                .registered_at(LocalDateTime.now())
                .last_login(LocalDateTime.now())
                .intro("con meo")
                .bio("meo con")
                .avatar_url("https://www.google.com")
                .social_links("https://www.facebook.com")
                .build();


        badUserRequest = UserRequest.builder()
                .first_name("ngn")
                .middle_name("vannnn")
                .last_name("teonnnn")
                .mobile("0909090909")
                .email("nguyen@gmail.com")
                .password_hash("123456")
                .dob(LocalDate.of(1990, 1, 1))
                .registered_at("2024-10-28T14:30:00")
                .last_login("2024-10-28T16:45:00")
                .intro("con meo")
                .bio("meo con")
                .avatar_url("https://www.google.com")
                .social_links("https://www.facebook.com")
                .roles(new ArrayList<>(Arrays.asList(1, 2)))
                .build();

    }


    @Test
    void createUser_ValidRequest_Success() throws Exception {

        //Given
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());// parse localdate
        String contentJson = objectMapper.writeValueAsString(userRequest);


        Mockito.when(userService.createUser(ArgumentMatchers.any())).thenReturn(userResponse);

        //When
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(contentJson)

                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1000));
        //Then

    }


    @Test
    void createUser_UserNameInValidRequest_Fail() throws Exception {

        //Given
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());// parse localdate
        String contentJson = objectMapper.writeValueAsString(badUserRequest);


        Mockito.when(userService.createUser(ArgumentMatchers.any())).thenReturn(userResponse);

        //When
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(contentJson)

                ).andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("code").value(1002))
                .andExpect(MockMvcResultMatchers.jsonPath("message").value("First Name must be At Least 5 characters and Maximum 10 characters"));
        //Then

    }
}
