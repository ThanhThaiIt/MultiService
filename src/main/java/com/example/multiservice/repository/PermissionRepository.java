package com.example.multiservice.repository;

import com.example.multiservice.entity.PermissionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionRepository extends JpaRepository<PermissionEntity, Integer> {

    boolean existsById(int id);

    @Query("SELECT COUNT(p) > 0 FROM permission p WHERE p.slug = :slug")
    boolean existsBySlug(@Param("slug") String slug);

    List<PermissionEntity> findAllByIdIn(List<Integer> ids);

    @Query("SELECT p FROM permission p JOIN p.rolePermissionEntities rp WHERE rp.rolePermissionKey.roleId = :roleId")
    List<PermissionEntity> findPermissionsByRoleId(@Param("roleId") int roleId);
}
