package com.example.multiservice.dto.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public record UserRequest(

            @Size(min = 6, max = 9,message = "USER_FIRST_NAME_INVALID")
          String first_name,

            @Size(min = 6, max = 9,message = "USER_MIDDLE_NAME_INVALID")
            String middle_name,

            @Size(min = 6, max = 9,message = "USER_LAST_NAME_INVALID")
            String last_name,

          @NotNull
          String mobile,
            @NotNull
          String email,

          @Size(min = 8, message = "INVALID_PASSWORD")
          String password_hash,
          String registered_at,
          String last_login,
          String intro,
          String bio,
          String avatar_url,
          String social_links,
            List<Integer> roles

) {
    public UserRequest(String first_name, String middle_name, String last_name,
                       String mobile, String email, String password_hash,
                       String last_login, String intro, String bio,
                       String avatar_url, String social_links, List<Integer> roles) {
        this(first_name, middle_name, last_name, mobile, email, password_hash,
                LocalDateTime.now().format(DateTimeFormatter.ISO_DATE_TIME),
                last_login, intro, bio, avatar_url, social_links, roles);
    }

}
