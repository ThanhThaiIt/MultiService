package com.example.multiservice.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.multiservice.dto.request.RoleRequest;
import com.example.multiservice.dto.response.RoleResponse;

@Service
public interface RoleService {

    RoleResponse create(RoleRequest role);

    List<RoleResponse> getAllRoles();

    String delete(int id);
}
