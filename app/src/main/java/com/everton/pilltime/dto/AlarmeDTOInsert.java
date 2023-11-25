package com.everton.pilltime.dto;

import com.everton.pilltime.models.Remedio;

import java.time.LocalDateTime;

public class AlarmeDTOInsert {

    private  String titulo;
    private String descricao;
    private String alarme;

    private RemedioDTO remedioalarme;

    public AlarmeDTOInsert(String titulo, String descricao, String alarme, RemedioDTO remedioalarme) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.alarme = alarme;
        this.remedioalarme = remedioalarme;
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

    public String getAlarme() {
        return alarme;
    }

    public void setAlarme(String alarme) {
        this.alarme = alarme;
    }

    public RemedioDTO getRemedioalarme() {
        return remedioalarme;
    }

    public void setRemedioalarme(RemedioDTO remedioalarme) {
        this.remedioalarme = remedioalarme;
    }

    @Override
    public String toString() {
        return "AlarmeDTOInsert{" +
                "titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", alarme=" + alarme +
                ", remedioalarme=" + remedioalarme +
                '}';
    }
}
