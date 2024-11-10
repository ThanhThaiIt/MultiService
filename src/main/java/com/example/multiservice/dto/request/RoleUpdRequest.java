package com.example.multiservice.dto.request;

import java.util.List;

public record RoleUpdRequest(
        int id,
        String title,
        String slug,
        String description,
        int active,
        String created_at,
        String updated_at,
        String content,
        List<Integer> permission
) {
}