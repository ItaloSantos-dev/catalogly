package com.italo.catalogy.model.enums;

public enum UserRole {
    USER("USER"), SELLER("SELLER"), ADMIN("ADMIN");

    private String role;

    UserRole (String role){
        this.role=role;
    }

    public String getRole() {
        return this.role;
    }
}
