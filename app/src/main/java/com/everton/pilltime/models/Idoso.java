package com.everton.pilltime.models;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Idoso extends Pessoa{


    private Long id;
    private List<Alarme> alarmesIdoso= new ArrayList<>();

    private Cuidador cuidador;
    private String observacao;

    private List<Alarme> listaAlarme = new ArrayList<>();


    public Idoso() {

    }

    public Idoso(Long id, String nome, String email, Date dataNascimento, String cpf, String telefone, Endereco endereco, boolean stAtivo, String observacao, Date dataCadastro, TipoUsuario tipoUsuario, Long id1, List<Alarme> alarmesIdoso, Cuidador cuidador, String observacao1, List<Alarme> listaAlarme) {
        super(id, nome, email, dataNascimento, cpf, telefone, endereco, stAtivo, observacao, dataCadastro, tipoUsuario);
        this.id = id1;
        this.alarmesIdoso = alarmesIdoso;
        this.cuidador = cuidador;
        this.observacao = observacao1;
        this.listaAlarme = listaAlarme;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public List<Alarme> getAlarmesIdoso() {
        return alarmesIdoso;
    }

    public void setAlarmesIdoso(List<Alarme> alarmesIdoso) {
        this.alarmesIdoso = alarmesIdoso;
    }

    public Cuidador getCuidador() {
        return cuidador;
    }

    public void setCuidador(Cuidador cuidador) {
        this.cuidador = cuidador;
    }

    @Override
    public String getObservacao() {
        return observacao;
    }

    @Override
    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public List<Alarme> getListaAlarme() {
        return listaAlarme;
    }

    public void setListaAlarme(List<Alarme> listaAlarme) {
        this.listaAlarme = listaAlarme;
    }
}
