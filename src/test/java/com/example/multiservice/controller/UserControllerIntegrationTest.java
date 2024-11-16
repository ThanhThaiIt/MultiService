package com.example.multiservice.controller;

import com.example.multiservice.dto.request.UserRequest;
import com.example.multiservice.dto.response.UserResponse;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.MySQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
public class UserControllerIntegrationTest {


    @Container
    static final MySQLContainer<?> mysqlContainer = new MySQLContainer<>("mysql:latest");


    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", mysqlContainer::getJdbcUrl);
        registry.add("spring.datasource.username", mysqlContainer::getUsername);
        registry.add("spring.datasource.password", mysqlContainer::getPassword);
        registry.add("spring.datasource.driver-class-name", () -> mysqlContainer.getDriverClassName());
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "update");



    }

    @Autowired
    private MockMvc mockMvc;





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



        //When
        var response= mockMvc.perform(MockMvcRequestBuilders
                        .post("/users")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(contentJson)

                ).andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("result.first_name").value("nguyen"))
                .andExpect(MockMvcResultMatchers.jsonPath("result.middle_name").value("vannnn"))
                .andExpect(MockMvcResultMatchers.jsonPath("result.last_name").value("teonnnn"))

        ;
        //Then

        log.info("resultR: "+response.andReturn().getResponse().getContentAsString());

    }



}
