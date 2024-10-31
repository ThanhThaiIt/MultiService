package com.example.multiservice.mapper;

import com.example.multiservice.dto.request.UserRequest;
import com.example.multiservice.dto.request.UserUpdateRequest;
import com.example.multiservice.dto.response.UserResponse;
import com.example.multiservice.entity.UserEntity;
import com.example.multiservice.utils.DateTimeUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring",uses = {DateTimeUtils.class})
public interface UserMapper {

    @Mapping(source = "registered_at", target = "registered_at",qualifiedByName = "parseStringToLocalDateTime")
    @Mapping(source = "last_login", target = "last_login",qualifiedByName = "parseStringToLocalDateTime")
    @Mapping(source = "role_id", target = "roleEntity.id")
    UserEntity toUser(UserRequest userRequest);


    // source: paramater , target return
    @Mapping(source = "registered_at", target = "registered_at",qualifiedByName = "parseStringToLocalDateTime")
    @Mapping(source = "last_login", target = "last_login",qualifiedByName = "parseStringToLocalDateTime")
    @Mapping(source = "role_id", target = "roleEntity.id")
    UserEntity toUserUpdate(UserUpdateRequest userUpdateRequest);

    UserResponse toUserResponse(UserEntity userEntity);
}
