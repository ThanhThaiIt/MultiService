package com.example.multiservice.dto.request;

public record PermissionRequest(
        String title,
        String slug,
        String description,
        String created_at,
        String updated_at,
        String content
) {
}
