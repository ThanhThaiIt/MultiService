package com.example.multiservice.dto;

import java.util.List;

import lombok.Data;

@Data
public class UserWithRolesDTO {
    private List<String> username;
    private List<String> roles;

    public UserWithRolesDTO(List<String> username, List<String> roles) {
        this.username = username;
        this.roles = roles;
    }
}
