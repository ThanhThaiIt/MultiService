// package com.example.multiservice.entity;
//
//
// import com.example.multiservice.entity.keys.UserRoleKey;
// import jakarta.persistence.*;
// import lombok.*;
// import lombok.experimental.FieldDefaults;
//
// @Data
// @AllArgsConstructor
// @NoArgsConstructor
// @Builder
// @FieldDefaults(level = AccessLevel.PRIVATE)
//
// @Entity(name = "user_roles")
// public class UserRoleEntity {
//
//    @EmbeddedId
//     UserRoleKey userRoleKey;
//
//    @ManyToOne
//    @MapsId("userId")
//    @JoinColumn(name = "user_id")
//     UserEntity userEntity;
//
//    @ManyToOne
//    @MapsId("roleId")
//    @JoinColumn(name = "role_id")
//     RoleEntity roleEntity;
//
//
//    @Override
//    public String toString() {
//        return "UserRoleEntity{" +
//                "userRoleKey=" + userRoleKey +
//                ", userId=" + (userEntity != null ? userEntity.getId() : null) +
//                ", roleId=" + (roleEntity != null ? roleEntity.getId() : null) +
//                '}';
//    }
// }
