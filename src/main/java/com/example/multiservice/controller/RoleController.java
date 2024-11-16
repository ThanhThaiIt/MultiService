package com.example.multiservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.example.multiservice.dto.request.RoleRequest;
import com.example.multiservice.dto.response.ApiResponse;
import com.example.multiservice.dto.response.RoleResponse;
import com.example.multiservice.service.RoleService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RoleController {

    RoleService roleService;

    @PostMapping
    public ApiResponse<RoleResponse> addRole(@RequestBody RoleRequest role) {
        return ApiResponse.<RoleResponse>builder()
                .result(roleService.create(role))
                .build();
    }

    @GetMapping
    public ApiResponse<List<RoleResponse>> getAllRoles() {

        return ApiResponse.<List<RoleResponse>>builder()
                .result(roleService.getAllRoles())
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteRole(@PathVariable int id) {
        return ApiResponse.<String>builder().result(roleService.delete(id)).build();
    }
}
