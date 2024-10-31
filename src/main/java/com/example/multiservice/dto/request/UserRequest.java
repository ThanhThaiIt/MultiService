package com.example.multiservice.dto.request;


import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

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
          int role_id

) {


}
