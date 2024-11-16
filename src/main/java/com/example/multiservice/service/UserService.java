package com.example.multiservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.multiservice.dto.request.UserRequest;
import com.example.multiservice.dto.request.UserUpdateRequest;
import com.example.multiservice.dto.response.UserResponse;

@Service
public interface UserService {

    UserResponse createUser(UserRequest userRequest);

    boolean updateUser(UserUpdateRequest userUpdateRequest);

    UserResponse getUserById(int id);

    List<UserResponse> getAllUsers();

    boolean deleteUserById(int id);

    UserResponse getUserByEmail();
}
