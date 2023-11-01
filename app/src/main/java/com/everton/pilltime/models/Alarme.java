package com.everton.pilltime.models;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class Alarme {


    private Long id;
    private String titulo;
    private String Descricao;
    private Date dtCadastrado;
    private Idoso idoso;
    private List<Remedio> RemediosIdosos;
    private LocalDateTime alarme;
    private boolean statusAlarme;


    public Alarme(Long id, String titulo, String descricao, Date dtCadastrado, Idoso idoso, List<Remedio> remediosIdosos, LocalDateTime alarme, boolean statusAlarme) {
        this.id = id;
        this.titulo = titulo;
        Descricao = descricao;
        this.dtCadastrado = dtCadastrado;
        this.idoso = idoso;
        RemediosIdosos = remediosIdosos;
        this.alarme = alarme;
        this.statusAlarme = statusAlarme;
    }

    public Alarme() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return Descricao;
    }

    public void setDescricao(String descricao) {
        Descricao = descricao;
    }

    public Date getDtCadastrado() {
        return dtCadastrado;
    }

    public void setDtCadastrado(Date dtCadastrado) {
        this.dtCadastrado = dtCadastrado;
    }

    public Idoso getIdoso() {
        return idoso;
    }

    public void setIdoso(Idoso idoso) {
        this.idoso = idoso;
    }

    public List<Remedio> getRemediosIdosos() {
        return RemediosIdosos;
    }

    public void setRemediosIdosos(List<Remedio> remediosIdosos) {
        RemediosIdosos = remediosIdosos;
    }

    public LocalDateTime getAlarme() {
        return alarme;
    }

    public void setAlarme(LocalDateTime alarme) {
        this.alarme = alarme;
    }

    public boolean isStatusAlarme() {
        return statusAlarme;
    }

    public void setStatusAlarme(boolean statusAlarme) {
        this.statusAlarme = statusAlarme;
    }
}

