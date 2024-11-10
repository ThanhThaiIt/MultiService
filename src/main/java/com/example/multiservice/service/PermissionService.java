package com.example.multiservice.service;

import com.example.multiservice.dto.request.PermissionRequest;
import com.example.multiservice.dto.request.PermissionUpdRequest;
import com.example.multiservice.dto.response.PermissionResponse;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public interface PermissionService {

    PermissionResponse create(PermissionRequest request);

    List<PermissionResponse> getAllPermissions();

    void delete(int id);
    PermissionResponse update(PermissionUpdRequest permissionUpdRequest);
    PermissionResponse getPermissionById(int id);
}
