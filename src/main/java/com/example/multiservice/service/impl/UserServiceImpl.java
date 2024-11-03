package com.example.multiservice.service.impl;

import com.example.multiservice.dto.request.UserRequest;
import com.example.multiservice.dto.request.UserUpdateRequest;
import com.example.multiservice.dto.response.UserResponse;
import com.example.multiservice.entity.RoleEntity;
import com.example.multiservice.entity.UserEntity;
import com.example.multiservice.exception.enums.UserRole;
import com.example.multiservice.exception.AppException;
import com.example.multiservice.exception.enums.ErrorStatusCode;
import com.example.multiservice.mapper.UserMapper;
import com.example.multiservice.repository.UserRepository;
import com.example.multiservice.service.UserService;
import com.example.multiservice.utils.DateTimeUtils;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor(onConstructor_ = {@Autowired})
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserServiceImpl implements UserService {

     DateTimeUtils dateTimeUtils;

     UserRepository userRepository;

     UserMapper userMapper;

     PasswordEncoder passwordEncoder;

    @Override
    public boolean createUser(UserRequest userRequest) {
        boolean result = false;

        if (userRepository.existsByEmail(userRequest.email()) || userRepository.existsByMobile(userRequest.mobile())){
            throw new AppException(ErrorStatusCode.USER_ALREADY_EXISTS);
        }




        UserEntity userEntity = userMapper.toUser(userRequest);
        userEntity.setPassword_hash(passwordEncoder.encode(userRequest.password_hash()));
        userEntity.setActive(1);


        try {
            userRepository.save(userEntity);
            result = true;
        }catch (RuntimeException e){
            throw new RuntimeException("Failed to save user");
         }


        return result;
    }

    @Override
    public boolean updateUser(UserUpdateRequest userRequest) {
        boolean result = false;

        if (!userRepository.existsById(userRequest.id())) {
            throw new AppException(ErrorStatusCode.USER_NOT_FOUND);
        }

        UserEntity userEntity = userMapper.toUserUpdate(userRequest);
        if (userRequest.active() == 1 || userRequest.active() == 0  ) {
            userEntity.setActive(userRequest.active());
        }
        try {
            userRepository.save(userEntity);
            result = true;
        }catch (RuntimeException e){
            throw new RuntimeException("Failed to update user");
        }
        return result;
    }

    @Override
    public UserResponse getUserById(int id) {
        UserEntity userEntity = userRepository.findById(id).orElse(null);

        if (userEntity == null) {
            throw new AppException(ErrorStatusCode.USER_NOT_FOUND);
        }
        UserResponse userResponse = userMapper.toUserResponse(userEntity);
        userResponse.setRoleName(UserRole.getById(userEntity.getRoleEntity().getId()).getName());


        return userResponse;

    }

    @Override
    public List<UserResponse> getAllUsers() {

        var authentication = SecurityContextHolder.getContext().getAuthentication();
        log.info("Username: {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info("Role: {}", grantedAuthority.getAuthority()));

        List<UserEntity> userEntities = userRepository.findByActive(1);

        return userEntities.stream().map(userEntity -> new UserResponse(
                userEntity.getId(),
                userEntity.getFirst_name(),
                userEntity.getMiddle_name(),
                userEntity.getLast_name(),
                userEntity.getMobile(),
                userEntity.getEmail(),
                userEntity.getRegistered_at(),
                userEntity.getLast_login(),
                userEntity.getIntro(),
                userEntity.getBio(),
                userEntity.getAvatar_url(),
                userEntity.getSocial_links(),
                UserRole.getById(userEntity.getRoleEntity().getId()).getName()// use enum store role, then user id from db to get name role form enum

        )).collect(Collectors.toList());
    }

    @Override
    public boolean deleteUserById(int id) {
        boolean result = false;
        Optional<UserEntity> userOptional = userRepository.findById(id);
        if (!userOptional.isPresent()) {
            throw new AppException(ErrorStatusCode.USER_NOT_FOUND);
        }else if (userOptional.isPresent()&&userOptional.get().getActive()==1) {
            UserEntity userEntity = userOptional.get();
            userEntity.setActive(0);
            userRepository.save(userEntity);
            result = true;
        }else {
            throw new RuntimeException("Failed to delete user");
        }

        return result;
    }
}
