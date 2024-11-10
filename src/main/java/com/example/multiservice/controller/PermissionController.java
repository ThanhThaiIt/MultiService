package com.example.multiservice.controller;

import com.example.multiservice.dto.request.PermissionRequest;
import com.example.multiservice.dto.request.PermissionUpdRequest;
import com.example.multiservice.dto.response.ApiResponse;
import com.example.multiservice.dto.response.PermissionResponse;

 import com.example.multiservice.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionController {
    PermissionService permissionService;
    //UserRoleRepository roleRepository;


    @GetMapping
    ApiResponse<List<PermissionResponse>> getPermissions() {
        return ApiResponse.<List<PermissionResponse>>builder().result(permissionService.getAllPermissions()).build();

    }

    @PostMapping
    ApiResponse<PermissionResponse> createPermission(@RequestBody PermissionRequest permissionRequest) {
        return ApiResponse.<PermissionResponse>builder().result(permissionService.create(permissionRequest)).build();
    }

    @DeleteMapping("/{id}")
    ApiResponse<Void> deletePermissions(@PathVariable int id ) {
        permissionService.delete(id);
        return  ApiResponse.<Void>builder().build();
    }


    @GetMapping("/{id}")
    ApiResponse<PermissionResponse> getPermissionWithID(@PathVariable int id ) {

        return  ApiResponse.<PermissionResponse>builder().result(permissionService.getPermissionById(id)).build();
    }

    @PutMapping
    ApiResponse<PermissionResponse> updatePermissions(@RequestBody PermissionUpdRequest permissionUpdRequest) {
        return ApiResponse.<PermissionResponse>builder().result(permissionService.update(permissionUpdRequest)).build();

    }
}
