package com.everton.pilltime.models;


import com.everton.pilltime.alarme.Alarme;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Idoso extends Pessoa{



    private List<Alarme> alarmesIdoso= new ArrayList<>();

    private Cuidador cuidador;
    private String observacao;

    private List<Alarme> listaAlarme = new ArrayList<>();


    public Idoso() {

    }


    public Idoso(Long id, String nome, String email, Date dataNascimento, String cpf, String telefone, Endereco endereco, boolean stAtivo, Date dataCadastro, TipoUsuario tipoUsuario, List<Alarme> alarmesIdoso, Cuidador cuidador, String observacao, List<Alarme> listaAlarme) {
        super(id, nome, email, dataNascimento, cpf, telefone, endereco, stAtivo, dataCadastro, tipoUsuario);
        this.alarmesIdoso = alarmesIdoso;
        this.cuidador = cuidador;
        this.observacao = observacao;
        this.listaAlarme = listaAlarme;
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

    public List<Alarme> getListaAlarme() {
        return listaAlarme;
    }

    public void setListaAlarme(List<Alarme> listaAlarme) {
        this.listaAlarme = listaAlarme;
    }
}
