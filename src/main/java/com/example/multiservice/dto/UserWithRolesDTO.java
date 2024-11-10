package com.example.multiservice.dto;

import com.example.multiservice.entity.RoleEntity;
import com.example.multiservice.entity.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class UserWithRolesDTO {
    private List<String> username;
    private List<String> roles;

    public UserWithRolesDTO(List<String> username, List<String> roles) {
        this.username = username;
        this.roles = roles;
    }
}
