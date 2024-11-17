package com.example.multiservice.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.multiservice.dto.request.UserRequest;
import com.example.multiservice.dto.request.UserUpdateRequest;
import com.example.multiservice.dto.response.PermissionResponse;
import com.example.multiservice.dto.response.RoleResponse;
import com.example.multiservice.dto.response.UserResponse;
import com.example.multiservice.entity.PermissionEntity;
import com.example.multiservice.entity.RoleEntity;
import com.example.multiservice.entity.UserEntity;
import com.example.multiservice.exception.AppException;
import com.example.multiservice.exception.enums.ErrorStatusCode;
import com.example.multiservice.mapper.PermissionMapper;
import com.example.multiservice.mapper.RoleMapper;
import com.example.multiservice.mapper.UserMapper;
import com.example.multiservice.repository.PermissionRepository;
import com.example.multiservice.repository.RoleRepository;
import com.example.multiservice.repository.UserRepository;
import com.example.multiservice.service.UserService;
import com.example.multiservice.utils.DateTimeUtils;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserServiceImpl implements UserService {

    DateTimeUtils dateTimeUtils;

    UserRepository userRepository;

    UserMapper userMapper;

    PasswordEncoder passwordEncoder;

    RoleRepository roleRepository;

    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;
    RoleMapper roleMapper;

    @Override
    public UserResponse createUser(UserRequest userRequest) {

        log.info("Serivce: createUser");

        UserEntity userEntity = userMapper.toUser(userRequest);
        userEntity.setPassword_hash(passwordEncoder.encode(userRequest.password_hash()));
        userEntity.setActive(1);
        var roleEntities = roleRepository.findAllById(userRequest.roles());
        userEntity.setRoles(roleEntities);

        try {
            var userEn = userRepository.save(userEntity);

            var roles = roleRepository.findAllById(userRequest.roles());

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

            UserResponse userResponse = userMapper.toUserResponse(userEn);
            userResponse.setRoles(roleResponses);
            return userResponse;

        } catch (DataIntegrityViolationException e) {
            throw new AppException(ErrorStatusCode.USER_ALREADY_EXISTS);
        } catch (RuntimeException e) {
            throw new AppException(ErrorStatusCode.FAILED_CREATE);
        }
    }

    @Override
    public boolean updateUser(UserUpdateRequest userRequest) {
        boolean result = false;

        if (!userRepository.existsById(userRequest.id())) {
            throw new AppException(ErrorStatusCode.USER_NOT_FOUND);
        }

        UserEntity userEntity = userMapper.toUserUpdate(userRequest);
        userEntity.setPassword_hash(passwordEncoder.encode(userRequest.password_hash()));
        var roles = roleRepository.findAllById(userRequest.roles());
        userEntity.setRoles(roles);

        if (userRequest.active() == 1 || userRequest.active() == 0) {
            userEntity.setActive(userRequest.active());
        }
        try {
            userRepository.save(userEntity);
            result = true;
        } catch (RuntimeException e) {
            throw new AppException(ErrorStatusCode.FAILED_UPDATE);
        }
        return result;
    }

    @Override
    @PostAuthorize("(returnObject.email==authentication.name)||(hasRole('ADMIN'))")
    public UserResponse getUserById(int id) {
        log.info("in method getUserById");

        UserEntity userEntity = userRepository.findById(id).orElse(null);

        if (userEntity == null) {
            throw new AppException(ErrorStatusCode.USER_NOT_FOUND);
        }
        UserResponse userResponse = userMapper.toUserResponse(userEntity);
        return userResponse;
    }

    @Override
    // @PreAuthorize("hasRole('ADMIN')")
    @PreAuthorize("hasAnyAuthority('create_post')")
    public List<UserResponse> getAllUsers() {

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Username: {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info("GrantedAuthority: {}", grantedAuthority));

        log.info("in method getAllUsers");

        List<UserEntity> userEntities = userRepository.findByActive(1);

        List<UserResponse> userResponses = new ArrayList<>();
        for (UserEntity userEntity : userEntities) {

            var roles = userEntity.getRoles();

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

            UserResponse userResponse = userMapper.toUserResponse(userEntity);
            userResponse.setRoles(roleResponses);
            userResponses.add(userResponse);
        }

        return userResponses;
    }

    @Override
    @PreAuthorize("hasRole('ADMIN')")
    public boolean deleteUserById(int id) {
        boolean result = false;
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            throw new AppException(ErrorStatusCode.USER_NOT_FOUND);
        } else if (userOptional.isPresent() && userOptional.get().getActive() == 1) {
            UserEntity userEntity = userOptional.get();
            userEntity.setActive(0);
            userRepository.save(userEntity);
            result = true;
        } else {
            throw new AppException(ErrorStatusCode.FAILED_DELETE);
        }

        return result;
    }
    // spotless:off
    @Override
    public UserResponse getUserByEmail() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        UserEntity userEntity =
                userRepository.findByEmail(name).orElseThrow(() -> new AppException(ErrorStatusCode.USER_NOT_FOUND));
        UserResponse userResponse = userMapper.toUserResponse(userEntity);
        return userResponse;
    }
    //spotless:on

}
