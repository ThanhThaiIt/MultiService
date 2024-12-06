package com.example.multiservice.dto.request;

import org.springframework.web.multipart.MultipartFile;

public record RegisterRequest(
        String first_name,
        String middle_name,
        String last_name,
        String dob,
        String mobile,
        String email,
        String password_hash,
        String intro,
        String bio,
        MultipartFile avatar_url,
        String social_links

) {
}
