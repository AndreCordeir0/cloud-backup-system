package com.cloud.backup.system.security;

public class AuthResponse {
    private AuthResponse(){}
    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public static AuthResponse token(String token) {
        var auth = new AuthResponse();
        auth.setToken(token);
        return auth;
    }
}
