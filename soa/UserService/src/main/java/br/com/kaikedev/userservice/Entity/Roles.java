package br.com.kaikedev.userservice.Entity;

public enum Roles {
    ADMIN("ADMIN"), USER("USER");

    private String role;

    Roles(String role) {
        this.role = role;
    }

    String getRole() {
        return role;
    }
}
