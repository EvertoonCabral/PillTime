package com.everton.pilltime.models;

public enum TipoUsuario {

    C("CUIDADOR"), I("IDOSO");

    private String descricao;

    TipoUsuario(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

}
