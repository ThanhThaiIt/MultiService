package com.example.multiservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.multiservice.entity.RoleEntity;

@Repository
public interface RoleRepository extends JpaRepository<RoleEntity, Integer> {
    @Query("SELECT r FROM roles r LEFT JOIN FETCH r.rolePermissionEntities rp JOIN FETCH rp.permissionEntity")
    RoleEntity findRoleWithPermissions();
}
