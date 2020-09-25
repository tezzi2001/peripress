package org.bondarenko.core.filter;

public enum Role {
    USER("USER"),
    ADMIN("ADMIN"),
    MODERATOR("MODERATOR"),
    PUBLISHER("PUBLISHER");

    private final String role;

    Role(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return role;
    }
}