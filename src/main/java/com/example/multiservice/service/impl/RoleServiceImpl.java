package com.example.multiservice.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.example.multiservice.dto.request.RoleRequest;
import com.example.multiservice.dto.response.PermissionResponse;
import com.example.multiservice.dto.response.RoleResponse;
import com.example.multiservice.entity.PermissionEntity;
import com.example.multiservice.entity.RoleEntity;
import com.example.multiservice.exception.AppException;
import com.example.multiservice.exception.enums.ErrorStatusCode;
import com.example.multiservice.mapper.PermissionMapper;
import com.example.multiservice.mapper.RoleMapper;
import com.example.multiservice.repository.PermissionRepository;
import com.example.multiservice.repository.RolePermissionRepository;
import com.example.multiservice.repository.RoleRepository;
import com.example.multiservice.service.RoleService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class RoleServiceImpl implements RoleService {
    RoleRepository roleRepository;
    RoleMapper roleMapper;
    PermissionRepository permissionRepository;
    RolePermissionRepository rolePermissionRepository;
    PermissionMapper permissionMapper;

    @Override
    public RoleResponse create(RoleRequest role) {
        var entity = roleMapper.roleToRoleEntity(role);

        var permissionEntities = permissionRepository.findAllByIdIn(role.permission());

        var roleEntity = roleRepository.save(entity);
        for (var permissionEntity : permissionEntities) {
            rolePermissionRepository.insertRolePermission(roleEntity.getId(), permissionEntity.getId());
        }

        return null;
    }

    @Override
    public List<RoleResponse> getAllRoles() {
        List<RoleEntity> roles = roleRepository.findAll();

        List<RoleResponse> roleResponses = new ArrayList<>();

        for (RoleEntity roleEntity : roles) {
            List<PermissionEntity> permissionEntities =
                    permissionRepository.findPermissionsByRoleId(roleEntity.getId());
            List<PermissionResponse> permissionResponses = new ArrayList<>();

            for (PermissionEntity permissionEntity : permissionEntities) {
                permissionResponses.add(permissionMapper.toPermissionResponse(permissionEntity));
            }
            RoleResponse roleResponse = roleMapper.roleToRoleResponse(roleEntity);
            roleResponse.setPermissions(permissionResponses);
            roleResponses.add(roleResponse);
        }

        return roleResponses;
    }

    @Override
    public String delete(int id) {
        try {

            if (!roleRepository.existsById(id)) {
                throw new AppException(ErrorStatusCode.NOT_FOUND);
            }
            rolePermissionRepository.deleteByRoleId(id);
            roleRepository.deleteById(id);
            return "success";
        } catch (Exception e) {
            throw new AppException(ErrorStatusCode.FAILED_DELETE);
        }
    }
}
