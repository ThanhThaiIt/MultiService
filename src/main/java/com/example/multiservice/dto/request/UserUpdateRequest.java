package com.example.multiservice.dto.request;

import java.time.LocalDate;
import java.util.List;

import com.example.multiservice.validator.constraints.DobConstraints;

public record UserUpdateRequest(
        int id,
        String first_name,
        String middle_name,
        String last_name,
        String mobile,
        String email,
        @DobConstraints(min = 18, max = 80, message = "DOB_EXCEPTION") LocalDate dob,
        String password_hash,
        String registered_at,
        String last_login,
        String intro,
        String bio,
        String avatar_url,
        String social_links,
        int active,
        List<Integer> roles) {}
