package com.everton.pilltime.models;

import com.everton.pilltime.models.enums.EnumtipoResidencia;

public class Endereco {

    private String estado;
    private String cidade;
    private String bairro;
    private String rua;
    private int numeroResidencia;
    private EnumtipoResidencia tipo;
    private String obs;

    public Endereco() {
    }

    public Endereco(String estado, String cidade, String bairro, String rua, int numeroResidencia, EnumtipoResidencia tipo, String obs) {
        this.estado = estado;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.numeroResidencia = numeroResidencia;
        this.tipo = tipo;
        this.obs = obs;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public int getNumeroResidencia() {
        return numeroResidencia;
    }

    public void setNumeroResidencia(int numeroResidencia) {
        this.numeroResidencia = numeroResidencia;
    }

    public EnumtipoResidencia getTipo() {
        return tipo;
    }

    public void setTipo(EnumtipoResidencia tipo) {
        this.tipo = tipo;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    @Override
    public String toString() {
        return "Endereco{" +
                "estado='" + estado + '\'' +
                ", cidade='" + cidade + '\'' +
                ", bairro='" + bairro + '\'' +
                ", rua='" + rua + '\'' +
                ", numeroResidencia=" + numeroResidencia +
                ", tipo=" + tipo +
                ", obs='" + obs + '\'' +
                '}';
    }
}
