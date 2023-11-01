package com.everton.pilltime.dto;

import java.time.LocalDateTime;
import java.util.List;

public class AlarmeDTO {

    private  String titulo;
    private String descricao;
    private IdosoDTO idoso;
    private List<RemedioDTO> RemediosIdosos;
    private LocalDateTime alarme;


    public AlarmeDTO(String titulo, String descricao, IdosoDTO idoso, List<RemedioDTO> remediosIdosos, LocalDateTime alarme) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.idoso = idoso;
        RemediosIdosos = remediosIdosos;
        this.alarme = alarme;
    }

    public AlarmeDTO() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public IdosoDTO getIdoso() {
        return idoso;
    }

    public void setIdoso(IdosoDTO idoso) {
        this.idoso = idoso;
    }

    public List<RemedioDTO> getRemediosIdosos() {
        return RemediosIdosos;
    }

    public void setRemediosIdosos(List<RemedioDTO> remediosIdosos) {
        RemediosIdosos = remediosIdosos;
    }

    public LocalDateTime getAlarme() {
        return alarme;
    }

    public void setAlarme(LocalDateTime alarme) {
        this.alarme = alarme;
    }
}
