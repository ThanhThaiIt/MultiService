package com.example.multiservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.multiservice.dto.request.RoleRequest;
import com.example.multiservice.dto.request.RoleUpdRequest;
import com.example.multiservice.dto.response.RoleResponse;
import com.example.multiservice.entity.RoleEntity;
import com.example.multiservice.utils.DateTimeUtils;

@Mapper(
        componentModel = "spring",
        uses = {DateTimeUtils.class})
public interface RoleMapper {

    @Mapping(source = "created_at", target = "created_at", qualifiedByName = "parseStringToLocalDateTime")
    @Mapping(source = "updated_at", target = "updated_at", qualifiedByName = "parseStringToLocalDateTime")
    // @Mapping(target = "userRoleEntities",ignore = true)
    @Mapping(target = "rolePermissionEntities", ignore = true)
    RoleEntity roleToRoleEntity(RoleRequest roleRequest);

    @Mapping(source = "created_at", target = "created_at", qualifiedByName = "parseStringToLocalDateTime")
    @Mapping(source = "updated_at", target = "updated_at", qualifiedByName = "parseStringToLocalDateTime")
    // @Mapping(target = "userRoleEntities",ignore = true)
    @Mapping(target = "rolePermissionEntities", ignore = true)
    RoleEntity roleUpdToRoleEntity(RoleUpdRequest roleUpdRequest);

    @Mapping(target = "permissions", ignore = true)
    @Mapping(target = "users", ignore = true)
    RoleResponse roleToRoleResponse(RoleEntity roleEntity);
}
