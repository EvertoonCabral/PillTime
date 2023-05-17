package com.everton.pilltime.models;

public class Remedio {

    private int id;
    private String nome;
    private Double massaVolume;
    private Double quantidade;
    private Double valor;
    private String obs;

    public Remedio(int id, String nome, Double massaVolume, Double quantidade, Double valor, String obs) {
        this.id = id;
        this.nome = nome;
        this.massaVolume = massaVolume;
        this.quantidade = quantidade;
        this.valor = valor;
        this.obs = obs;
    }

    public Remedio() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getMassaVolume() {
        return massaVolume;
    }

    public void setMassaVolume(Double massaVolume) {
        this.massaVolume = massaVolume;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    @Override
    public String toString() {
        return "Remedio{" +
                "identificador=" + id +
                ", nome='" + nome + '\'' +
                ", Massa ou Volume=" + massaVolume +
                ", quantidade=" + quantidade +
                ", valor=" + valor +
                ", observações='" + obs + '\'' +
                '}';
    }
}
