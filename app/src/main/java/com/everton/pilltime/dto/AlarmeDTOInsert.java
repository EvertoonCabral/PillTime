package com.everton.pilltime.dto;

import java.time.LocalDateTime;

public class AlarmeDTOInsert {

    private  String titulo;
    private String descricao;
    private LocalDateTime alarme;

    public AlarmeDTOInsert(String titulo, String descricao, LocalDateTime alarme) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.alarme = alarme;
    }

    public AlarmeDTOInsert() {
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

    public LocalDateTime getAlarme() {
        return alarme;
    }

    public void setAlarme(LocalDateTime alarme) {
        this.alarme = alarme;
    }
}
