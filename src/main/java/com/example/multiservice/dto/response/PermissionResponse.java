package com.example.multiservice.dto.response;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PermissionResponse {
    int id;
    String title;
    String slug;
    String description;
    LocalDateTime created_at;
    LocalDateTime updated_at;
    String content;
}
