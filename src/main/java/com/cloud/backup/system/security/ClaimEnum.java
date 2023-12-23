package com.cloud.backup.system.security;

public enum ClaimEnum {

    ID("id"),
    EMAIL("email");

    private String claim;

    ClaimEnum(String claim) {
        this.claim = claim;
    }

    public String getClaim() {
        return claim;
    }
}
