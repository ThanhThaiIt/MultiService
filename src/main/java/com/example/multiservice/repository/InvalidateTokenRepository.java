package com.example.multiservice.repository;

import com.example.multiservice.entity.InvalidateToken;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;

@Repository
public interface InvalidateTokenRepository extends JpaRepository<InvalidateToken, String> {


    @Transactional
    @Modifying
    @Query("DELETE FROM invalidate_token t WHERE t.expiryDate < :currentDate")// if current time > expireTime is would be remove from Database
    void deleteExpiredTokens(Date currentDate);
}