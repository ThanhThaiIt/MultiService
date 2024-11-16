package com.example.multiservice.service;

import com.example.multiservice.dto.request.UserRequest;
import com.example.multiservice.dto.response.UserResponse;
import com.example.multiservice.entity.UserEntity;
import com.example.multiservice.exception.AppException;
import com.example.multiservice.repository.UserRepository;
import org.assertj.core.api.Assertions;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;


import org.mockito.Mockito;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.TestPropertySource;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;


@SpringBootTest
@TestPropertySource("/test.properties")
public class UserServiceImplTest {

    @Autowired
    private UserService userService;

    @MockBean
    private UserRepository userRepository;

    // request
    private UserRequest userRequest;
    private UserRequest badUserRequest;


    private UserResponse userResponse;
    private UserEntity userEntity;

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

        userEntity = UserEntity.builder()
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

    }

    @Test
    void createUser_validRequest_Success() {

        //Given
        when(userRepository.existsByEmail(anyString())).thenReturn(false);
        when(userRepository.existsByMobile(anyString())).thenReturn(false);
        when(userRepository.save(any())).thenReturn(userEntity);

        //When
        var response = userService.createUser(userRequest);

        //Then
        Assertions.assertThat(response.getId()).isEqualTo(20);
        Assertions.assertThat(response.getFirst_name()).isEqualTo("nguyennn");
    }


    @Test
    void createUser_userExist_invalidRequest_Fail() {

        //Given
        when(userRepository.existsByEmail(anyString())).thenReturn(true);
        when(userRepository.existsByMobile(anyString())).thenReturn(true);

        //When
        var exception = assertThrows(AppException.class, () -> userService.createUser(userRequest));

        Assertions.assertThat(exception.getErrorStatusCode().getCode()).isEqualTo(1006);
        Assertions.assertThat(exception.getErrorStatusCode().getMessage()).isEqualTo("User Already Exists");
    }

    @Test
    @WithMockUser(username = "ccccc@gmail.com")
    void getInfo_validRequest_Success() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.of(userEntity));

        var response = userService.getUserByEmail();

        System.out.println(response.getEmail());

        Assertions.assertThat(response.getId()).isEqualTo(20);
        Assertions.assertThat(response.getEmail()).isEqualTo("nguyen@gmail.com");
    }


    @Test
    @WithMockUser(username = "ccccc@gmail.com")
    void getInfo_NotFoundUser_Fail() {
        when(userRepository.findByEmail(anyString())).thenReturn(Optional.ofNullable(null));


        var exception = assertThrows(AppException.class, () -> userService.getUserByEmail());
        System.out.println(exception.getErrorStatusCode().getCode());
        System.out.println(exception.getErrorStatusCode().getMessage());

        Assertions.assertThat(exception.getErrorStatusCode().getCode()).isEqualTo(1000);
        Assertions.assertThat(exception.getErrorStatusCode().getMessage()).isEqualTo("User Not Found");

    }
}