package org.bondarenko.constant;

public enum  Role {
    USER("USER"),
    ADMIN("ADMIN"),
    BANNED("BANNED");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return role;
    }
}
