package com.everton.pilltime.user;

public enum UserRole {

    ADMIN("admin"),
    USER("user");


    private String role;

    UserRole(String role){
        this.role = role;
    }

    private String getRole(){
        return role;
    }


}
