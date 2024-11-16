package com.example.multiservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.multiservice.dto.request.UserRequest;
import com.example.multiservice.dto.request.UserUpdateRequest;
import com.example.multiservice.dto.response.UserResponse;
import com.example.multiservice.entity.UserEntity;
import com.example.multiservice.utils.DateTimeUtils;

@Mapper(
        componentModel = "spring",
        uses = {DateTimeUtils.class})
public interface UserMapper {

    @Mapping(source = "registered_at", target = "registered_at", qualifiedByName = "parseStringToLocalDateTime")
    @Mapping(source = "last_login", target = "last_login", qualifiedByName = "parseStringToLocalDateTime")
    // @Mapping(source = "role_id", target = "roleEntity.id")
    @Mapping(target = "roles", ignore = true)
    UserEntity toUser(UserRequest userRequest);

    // source: paramater , target return
    @Mapping(source = "registered_at", target = "registered_at", qualifiedByName = "parseStringToLocalDateTime")
    @Mapping(source = "last_login", target = "last_login", qualifiedByName = "parseStringToLocalDateTime")
    // @Mapping(source = "role_id", target = "roleEntity.id")
    @Mapping(target = "roles", ignore = true)
    UserEntity toUserUpdate(UserUpdateRequest userUpdateRequest);

    // @Mapping(source = "role_id", target = "roleEntity.id")
    UserResponse toUserResponse(UserEntity userEntity);
}
