package com.everton.pilltime.user;

import com.everton.pilltime.models.TipoUsuario;

public class LoginResponseDTO {

    private String token;

    private Long pessoaId;

    private TipoUsuario tipoUsuario;



    public LoginResponseDTO() {
    }

    public LoginResponseDTO(String token, Long pessoaId, TipoUsuario tipoUsuario) {
        this.token = token;
        this.pessoaId = pessoaId;
        this.tipoUsuario = tipoUsuario;
    }

    // Getters e setters
    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
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
