package com.everton.pilltime.user;

public class AuthenticationDTO {

    private String login;
    private String senha;

    public AuthenticationDTO(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }

    public AuthenticationDTO() {
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

}
