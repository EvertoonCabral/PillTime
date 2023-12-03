package com.everton.pilltime.dto;


public class AlarmeDTOInsert {

    private  String titulo;
    private String descricao;
    private String alarme;
    private  Long idFoto;


    public AlarmeDTOInsert(String titulo, String descricao, String alarme, Long idFoto) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.alarme = alarme;
        this.idFoto = idFoto;
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


    public Long getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(Long idFoto) {
        this.idFoto = idFoto;
    }

    @Override
    public String toString() {
        return "AlarmeDTOInsert{" +
                "titulo='" + titulo + '\'' +
                ", descricao='" + descricao + '\'' +
                ", alarme='" + alarme + '\'' +
                ", idFoto=" + idFoto +
                '}';
    }
}
