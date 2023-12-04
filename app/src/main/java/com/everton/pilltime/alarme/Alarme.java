package com.everton.pilltime.alarme;

import com.everton.pilltime.models.Idoso;
import com.everton.pilltime.models.Remedio;
import java.time.LocalDateTime;

public class Alarme {

    private Long id;
    private String titulo;
    private String descricao;
    private LocalDateTime horarioAlarme;
    private Idoso idoso;
    private Remedio remedio;
    private boolean statusAlarme;

    private Long idFoto;


    public Alarme(Long id, String titulo, String descricao, LocalDateTime horarioAlarme, Idoso idoso, Remedio remedio, boolean statusAlarme, Long idFoto) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.horarioAlarme = horarioAlarme;
        this.idoso = idoso;
        this.remedio = remedio;
        this.statusAlarme = statusAlarme;
        this.idFoto = idFoto;
    }

    public Alarme() {
    }

    public LocalDateTime getHorarioAlarme() {
        return horarioAlarme;
    }

    public void setHorarioAlarme(LocalDateTime horarioAlarme) {
        this.horarioAlarme = horarioAlarme;
    }

    public Remedio getRemedio() {
        return remedio;
    }

    public void setRemedio(Remedio remedio) {
        this.remedio = remedio;
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
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Idoso getIdoso() {
        return idoso;
    }

    public void setIdoso(Idoso idoso) {
        this.idoso = idoso;
    }

    public boolean isStatusAlarme() {
        return statusAlarme;
    }

    public void setStatusAlarme(boolean statusAlarme) {
        this.statusAlarme = statusAlarme;
    }

    public Long getIdFoto() {
        return idFoto;
    }

    public void setIdFoto(Long idFoto) {
        this.idFoto = idFoto;
    }
}
