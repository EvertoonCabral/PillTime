package com.everton.pilltime.dto;

public class ResponseFotoDTO {

    private Long idFoto;


    public ResponseFotoDTO(Long idFoto) {
        this.idFoto = idFoto;
    }

    public ResponseFotoDTO() {
    }

    public Long getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(Long idFoto) {
        this.idFoto = idFoto;
    }
}
