package com.everton.pilltime.dto;

import com.everton.pilltime.user.UserRole;

public class UsuarioDTO {

    private Long id;
    private String login;
    private UserRole role;

    public UsuarioDTO(Long id, String login, UserRole role) {
        this.id = id;
        this.login = login;
        this.role = role;
    }

    public UsuarioDTO() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }
}
