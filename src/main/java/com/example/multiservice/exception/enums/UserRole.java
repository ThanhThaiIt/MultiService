package com.example.multiservice.exception.enums;

public enum UserRole {
    ADMIN(1, "ADMIN"),
    USER(2, "USER");

    private final int id;
    private final String name;

    UserRole(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public static UserRole getById(int id) {
        for (UserRole role : values()) {
            if (role.getId() == id) {
                return role;
            }
        }
        throw new IllegalArgumentException("No role found with id " + id);
    }
}
