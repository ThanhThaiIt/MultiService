package com.example.multiservice.service;

import com.example.multiservice.dto.request.UserRequest;
import com.example.multiservice.dto.request.UserUpdateRequest;
import com.example.multiservice.dto.response.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {



      boolean createUser(UserRequest userRequest);
      boolean updateUser(UserUpdateRequest userUpdateRequest);
      UserResponse getUserById(int id);
      List<UserResponse> getAllUsers();
      boolean deleteUserById(int id);
}
