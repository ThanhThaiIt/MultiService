package com.example.multiservice.repository;

import com.example.multiservice.entity.RolePermissionEntity;
import com.example.multiservice.entity.keys.RolePermissionKey;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface RolePermissionRepository  extends JpaRepository<RolePermissionEntity, RolePermissionKey> {

    @Modifying
    @Transactional
    @Query(value = "INSERT INTO role_permission(role_id,permission_id) VALUES (:roleId,:permissionId)", nativeQuery = true)
    void insertRolePermission(int roleId, int permissionId);

    @Modifying
    @Transactional
    @Query(value = "DELETE FROM role_permission WHERE role_id = :roleId", nativeQuery = true)
    void deleteByRoleId(int roleId);

}
