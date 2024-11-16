package com.example.multiservice.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import com.example.multiservice.dto.request.PermissionRequest;
import com.example.multiservice.dto.request.PermissionUpdRequest;
import com.example.multiservice.dto.response.PermissionResponse;
import com.example.multiservice.entity.PermissionEntity;
import com.example.multiservice.utils.DateTimeUtils;

@Mapper(
        componentModel = "spring",
        uses = {DateTimeUtils.class})
public interface PermissionMapper {

    @Mapping(source = "created_at", target = "created_at", qualifiedByName = "parseStringToLocalDateTime")
    @Mapping(source = "updated_at", target = "updated_at", qualifiedByName = "parseStringToLocalDateTime")
    PermissionEntity toPermissionEntity(PermissionRequest permissionRequest);

    @Mapping(source = "created_at", target = "created_at", qualifiedByName = "parseStringToLocalDateTime")
    @Mapping(source = "updated_at", target = "updated_at", qualifiedByName = "parseStringToLocalDateTime")
    PermissionEntity toPermissionEntityWithId(PermissionUpdRequest permissionUpdRequest);

    //    @Mapping(source = "created_at", target = "created_at",qualifiedByName = "parseStringToLocalDateTime")
    //    @Mapping(source = "updated_at", target = "updated_at",qualifiedByName = "parseStringToLocalDateTime")
    PermissionResponse toPermissionResponse(PermissionEntity permissionEntity);
}
