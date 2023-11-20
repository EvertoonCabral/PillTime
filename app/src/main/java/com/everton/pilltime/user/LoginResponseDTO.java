package com.everton.pilltime.user;

public class LoginResponseDTO {

    private String token;

    private Long pessoaId;

    public LoginResponseDTO() {
    }

    public LoginResponseDTO(String token, Long pessoaId) {
        this.token = token;
        this.pessoaId = pessoaId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Long getPessoaId() {
        return pessoaId;
    }

    public void setPessoaId(Long pessoaId) {
        this.pessoaId = pessoaId;
    }
}
