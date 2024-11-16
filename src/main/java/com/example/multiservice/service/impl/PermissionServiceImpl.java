package com.example.multiservice.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.example.multiservice.dto.request.PermissionRequest;
import com.example.multiservice.dto.request.PermissionUpdRequest;
import com.example.multiservice.dto.response.PermissionResponse;
import com.example.multiservice.entity.PermissionEntity;
import com.example.multiservice.exception.AppException;
import com.example.multiservice.exception.enums.ErrorStatusCode;
import com.example.multiservice.mapper.PermissionMapper;
import com.example.multiservice.repository.PermissionRepository;
import com.example.multiservice.service.PermissionService;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionServiceImpl implements PermissionService {

    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    @Override
    public PermissionResponse create(PermissionRequest request) {

        if (permissionRepository.existsBySlug(request.slug())) {
            throw new AppException(ErrorStatusCode.PERMISSION_ALREADY_EXISTS);
        }

        PermissionEntity permissionEntity = permissionMapper.toPermissionEntity(request);
        permissionEntity = permissionRepository.save(permissionEntity);

        return permissionMapper.toPermissionResponse(permissionEntity);
    }

    @Override
    public List<PermissionResponse> getAllPermissions() {
        var permissionEntities = permissionRepository.findAll();

        return permissionEntities.stream()
                .map(p -> permissionMapper.toPermissionResponse(p))
                .toList();
    }

    @Override
    public void delete(int id) {

        if (permissionRepository.existsById(id)) {
            permissionRepository.deleteById(id);
        } else throw new AppException(ErrorStatusCode.PERMISSION_NOT_FOUND);
    }

    @Override
    public PermissionResponse update(PermissionUpdRequest permissionUpdRequest) {
        if (!permissionRepository.existsById(permissionUpdRequest.id())) {
            throw new AppException(ErrorStatusCode.PERMISSION_NOT_FOUND);
        }

        try {
            var permissionEntity = permissionMapper.toPermissionEntityWithId(permissionUpdRequest);
            var rs = permissionRepository.save(permissionEntity);
            return permissionMapper.toPermissionResponse(rs);
        } catch (RuntimeException e) {
            throw new AppException(ErrorStatusCode.FAILED_UPDATE);
        }
    }

    @Override
    public PermissionResponse getPermissionById(int id) {
        Optional<PermissionEntity> permissionEntity = permissionRepository.findById(id);
        if (permissionEntity.isPresent()) {
            return permissionMapper.toPermissionResponse(permissionEntity.get());
        }
        throw new AppException(ErrorStatusCode.PERMISSION_NOT_FOUND);
    }
}
