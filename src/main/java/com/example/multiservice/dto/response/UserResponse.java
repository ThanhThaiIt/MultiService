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
public class UserResponse {
  int id;
  String first_name;
  String middle_name;
  String last_name;
  String mobile;
  String email;
  LocalDateTime registered_at;
  LocalDateTime last_login;
  String intro;
  String bio;
  String avatar_url;
  String social_links;
  List<String> roleName;
  List<RoleResponse> roles;
}
