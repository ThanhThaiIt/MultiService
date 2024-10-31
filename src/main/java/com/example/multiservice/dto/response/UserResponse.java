package com.example.multiservice.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
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
  String roleName;
}
