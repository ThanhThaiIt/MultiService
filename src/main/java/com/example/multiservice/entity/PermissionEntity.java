package com.example.multiservice.entity;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "permission")
public class PermissionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "slug")
    private String slug;

    @Column(name = "description")
    private String description;

    @Column(name = "active")
    private int active;

    @Column(name = "created_at")
    private LocalDateTime created_at;

    @Column(name = "updated_at")
    private LocalDateTime updated_at;

    @Column(name = "content")
    private String content;

    @OneToMany(mappedBy = "permissionEntity")
    private List<RolePermissionEntity> rolePermissionEntities;

    @Override
    public String toString() {
        return "PermissionEntity{" + "id="
                + id + ", title='"
                + title + '\'' + ", slug='"
                + slug + '\'' + ", description='"
                + description + '\'' + ", active="
                + active + ", created_at="
                + created_at + ", updated_at="
                + updated_at + ", content='"
                + content + '\'' + ", rolePermissionEntities="
                + rolePermissionEntities + '}';
    }
}
