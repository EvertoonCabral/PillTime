package com.everton.pilltime.models;


import com.everton.pilltime.alarme.Alarme;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Idoso extends Pessoa{



    private List<Alarme> alarmesIdoso= new ArrayList<>();

    private Cuidador cuidador;
    private String observacao;




    public Idoso() {

    }


    public Idoso(Long id, String nome, String email, Date dataNascimento, String cpf, String telefone, Endereco endereco, boolean stAtivo, Date dataCadastro, TipoUsuario tipoUsuario, List<Alarme> alarmesIdoso, Cuidador cuidador, String observacao) {
        super(id, nome, email, dataNascimento, cpf, telefone, endereco, stAtivo, dataCadastro, tipoUsuario);
        this.alarmesIdoso = alarmesIdoso;
        this.cuidador = cuidador;
        this.observacao = observacao;
    }

    public Idoso(List<Alarme> alarmesIdoso, Cuidador cuidador, String observacao) {
        this.alarmesIdoso = alarmesIdoso;
        this.cuidador = cuidador;
        this.observacao = observacao;
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

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
