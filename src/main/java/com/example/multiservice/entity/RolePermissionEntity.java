package com.example.multiservice.entity;

import java.time.LocalDateTime;

import jakarta.persistence.*;

import com.example.multiservice.entity.keys.RolePermissionKey;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity(name = "role_permission")
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RolePermissionEntity {

    @EmbeddedId
    RolePermissionKey rolePermissionKey;

    @ManyToOne
    @MapsId("roleId")
    @JoinColumn(name = "role_id", nullable = false)
    RoleEntity roleEntity;

    @ManyToOne
    @MapsId("permissionId")
    @JoinColumn(name = "permission_id", nullable = false)
    PermissionEntity permissionEntity;

    @Column(name = "created_at")
    LocalDateTime created_at = LocalDateTime.now();

    @Column(name = "updated_at")
    LocalDateTime updated_at = LocalDateTime.now();
}
