package com.example.multiservice.dto.response;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RoleResponse {
    int id;
    String title;
    String slug;
    String description;
    int active;
    LocalDateTime created_at;
    LocalDateTime updated_at;
    String content;
    List<PermissionResponse> permissions;
    List<UserResponse> users;
}
