package com.dev.backendforcursach.enums;

public enum Role {
    OWNER, USER, ADMIN;

    public static Role fromString(String role) {
        if (role == null) return USER;

        try {
            return Role.valueOf(role.toUpperCase());
        } catch (IllegalArgumentException e) {
            return USER;
        }
    }
}
