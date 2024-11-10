package com.example.multiservice.dto.request;

import java.util.List;

public record UserUpdateRequest(
        int id,
        String first_name,
        String middle_name,
        String last_name,
        String mobile,
        String email,
        String password_hash,
        String registered_at,
        String last_login,
        String intro,
        String bio,
        String avatar_url,
        String social_links,
        int active,
        List<Integer> roles
) {
}
