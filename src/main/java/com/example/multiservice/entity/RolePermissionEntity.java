package com.example.multiservice.entity;

import com.example.multiservice.entity.keys.RolePermissionKey;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

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
    @JoinColumn(name = "role_id",nullable = false)
     RoleEntity roleEntity;

    @ManyToOne
    @MapsId("permissionId")
    @JoinColumn(name = "permission_id",nullable = false)
     PermissionEntity permissionEntity;

    @Column(name = "created_at")
    LocalDateTime created_at=LocalDateTime.now();

    @Column(name = "updated_at")
    LocalDateTime updated_at=LocalDateTime.now();
}