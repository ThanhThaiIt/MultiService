package com.example.multiservice.entity;

import com.example.multiservice.entity.keys.RolePermissionKey;
import jakarta.persistence.*;

@Entity(name = "role_permission")
public class RolePermissionEntity {

    @EmbeddedId
    private RolePermissionKey rolePermissionKey;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id",nullable = false)
    private RoleEntity roleEntity;

    @ManyToOne
    @MapsId("permissionId")
    @JoinColumn(name = "permission_id",nullable = false)
    private PermissionEntity permissionEntity;
}
