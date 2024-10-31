package com.example.multiservice.repository;

import com.example.multiservice.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {

    List<UserEntity> findByActive(int active);
    boolean existsById(int id);
    boolean existsByEmail(String email);
    boolean existsByMobile(String mobile);
    Optional<UserEntity> findByEmail(String email);


}
