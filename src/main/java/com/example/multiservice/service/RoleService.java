package com.example.multiservice.service;

import com.example.multiservice.dto.request.RoleRequest;
import com.example.multiservice.dto.response.RoleResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RoleService {

    RoleResponse create(RoleRequest role);

    List<RoleResponse> getAllRoles();

    String delete(int id);
}
