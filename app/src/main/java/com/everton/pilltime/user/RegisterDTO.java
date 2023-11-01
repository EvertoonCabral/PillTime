package com.everton.pilltime.user;

import com.everton.pilltime.models.Pessoa;

public class RegisterDTO {


    private String login;
    private String senha;
    private UserRole role;
    private Pessoa pessoa;

    public RegisterDTO(String login, String senha, UserRole role, Pessoa pessoa) {
        this.login = login;
        this.senha = senha;
        this.role = role;
        this.pessoa = pessoa;

    }

    public RegisterDTO() {
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public Pessoa getPessoa() {
        return pessoa;
    }

    public void setPessoa(Pessoa pessoa) {
        this.pessoa = pessoa;
    }
}
