package com.epam.esm.entity;

public enum UserRole {
    USER("ROLE_USER"),
    ADMIN("ROLE_ADMIN");

    private final String value;

    UserRole(String value){
        this.value = value;
    }

    @Override
    public String toString() {
        return value;
    }
}
