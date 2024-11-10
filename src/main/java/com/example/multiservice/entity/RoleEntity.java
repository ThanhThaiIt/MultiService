package com.example.multiservice.entity;


import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Builder
@Entity(name = "roles")
public class RoleEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
     int id;

    @Column(name = "title")
     String title;

    @Column(name = "slug")
     String slug;

    @Column(name = "description")
     String description;

    @Column(name = "active")
     int active;

    @Column(name = "created_at")
     LocalDateTime created_at;

    @Column(name = "updated_at")
     LocalDateTime updated_at;

    @Column(name = "content")
     String content;

    public RoleEntity(int id) {
        this.id = id;
    }



//    @OneToMany(mappedBy = "roleEntity")
//     List<UserRoleEntity> userRoleEntities;

    @OneToMany(mappedBy = "roleEntity")
     List<RolePermissionEntity> rolePermissionEntities;
}
