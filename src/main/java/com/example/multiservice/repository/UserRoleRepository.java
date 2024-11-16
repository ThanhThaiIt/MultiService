// package com.example.multiservice.repository;
//
// import com.example.multiservice.entity.RoleEntity;
// import com.example.multiservice.entity.UserEntity;
// import com.example.multiservice.entity.UserRoleEntity;
// import com.example.multiservice.entity.keys.UserRoleKey;
// import jakarta.transaction.Transactional;
// import org.springframework.data.jpa.repository.JpaRepository;
// import org.springframework.data.jpa.repository.Modifying;
// import org.springframework.data.jpa.repository.Query;
// import org.springframework.data.repository.query.Param;
// import org.springframework.stereotype.Repository;
//
// import java.util.List;
//
// @Repository
// public interface UserRoleRepository extends JpaRepository<UserRoleEntity, UserRoleKey> {
//
////    @Query("SELECT ur.roleEntity from user_roles ur where ur.userEntity.id = :userId")
////    List<RoleEntity> findRolesByUserId(@Param("userId") int userId);
//
//
//    List<UserRoleEntity> findByUserEntity(UserEntity userEntity);
//
//    @Modifying
//    @Transactional
//    @Query(value = "INSERT INTO user_roles (user_id, role_id) VALUES (:userId, :roleId)", nativeQuery = true)
//    void insertUserRole(int userId, int roleId);
// }
