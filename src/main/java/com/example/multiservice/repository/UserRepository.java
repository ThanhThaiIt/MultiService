package com.example.multiservice.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.multiservice.entity.UserEntity;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    List<UserEntity> findByActive(int active);

    boolean existsById(int id);

    boolean existsByEmail(String email);

    boolean existsByMobile(String mobile);

    Optional<UserEntity> findByEmail(String email);

    @Query(
            value = "SELECT u.email, r.title " + "FROM user u "
                    + "JOIN user_roles ur ON u.id = ur.user_id "
                    + "JOIN roles r ON ur.role_id = r.id "
                    + "WHERE u.id = :userId",
            nativeQuery = true)
    List<Object[]> findUserWithRolesNative(@Param("userId") int userId);
}
