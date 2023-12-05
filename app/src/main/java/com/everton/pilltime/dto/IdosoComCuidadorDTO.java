package com.everton.pilltime.dto;

import com.everton.pilltime.models.TipoUsuario;
import com.everton.pilltime.user.UserRole;

public class IdosoComCuidadorDTO {

    private Long idosoId;
    private String nomeIdoso;
    private Long cuidadorId;


    public IdosoComCuidadorDTO(Long idosoId, String nomeIdoso, Long cuidadorId) {
        this.idosoId = idosoId;
        this.nomeIdoso = nomeIdoso;
        this.cuidadorId = cuidadorId;
    }

    public IdosoComCuidadorDTO() {
    }

    public Long getIdosoId() {
        return idosoId;
    }

    public void setIdosoId(Long idosoId) {
        this.idosoId = idosoId;
    }

    public String getNomeIdoso() {
        return nomeIdoso;
    }

    public void setNomeIdoso(String nomeIdoso) {
        this.nomeIdoso = nomeIdoso;
    }

    public Long getCuidadorId() {
        return cuidadorId;
    }

    public void setCuidadorId(Long cuidadorId) {
        this.cuidadorId = cuidadorId;
    }

}
