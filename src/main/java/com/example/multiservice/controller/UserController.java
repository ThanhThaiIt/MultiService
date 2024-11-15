package com.example.multiservice.controller;

import com.example.multiservice.dto.request.UserRequest;
import com.example.multiservice.dto.request.UserUpdateRequest;
import com.example.multiservice.dto.response.ApiResponse;
import com.example.multiservice.dto.response.UserResponse;
import com.example.multiservice.entity.UserEntity;
import com.example.multiservice.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserController {

    UserService userService;

    @PostMapping
    public ApiResponse<UserResponse> createUser(@RequestBody @Valid UserRequest userRequest) {
        log.info("Controller: createUser");


        return ApiResponse.<UserResponse>builder().result(userService.createUser(userRequest)).build();
    }

    @PutMapping
    public String updateUser(@RequestBody UserUpdateRequest userUpdateRequest) {
        if (userService.updateUser(userUpdateRequest)) {
            return "User updated";
        }
        return "User fail";
    }

    @GetMapping
    public List<UserResponse> getAllUser() {
        return userService.getAllUsers();
    }

    @GetMapping("/{userId}")
    public UserResponse getUser(@PathVariable int userId) {
        return userService.getUserById(userId);
    }


    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable int userId) {
        String resultMsg = "";
        if (userService.deleteUserById(userId)) {
            resultMsg = "User deleted";
        } else {
            resultMsg = "User fail";

        }
        return resultMsg;
    }

    @GetMapping("/my-info")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getUserByEmail())
                .build();
    }


}
