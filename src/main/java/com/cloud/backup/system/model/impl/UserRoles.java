package com.cloud.backup.system.model.impl;

public enum UserRoles {

    ADMIN("ADMIN"),
    USER("USER");

    private String name;

    UserRoles(String name) {
        this.name = name;
    }

    public String getName() {
        return this.name;
    }
}
