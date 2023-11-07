package com.everton.pilltime.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Remedio {


    private Long id;

    private String nome;

    private Cuidador cuidador;
    private String marcaRemedio;

    private String dosagem;

    private String formaFarmaceutico;

    private Date dataCadastro;

    private Date dataValidade;
    private String observacoes;

    private boolean stAtivo = true;;

    public Remedio(String nome,String marca,String validade) {

        this.nome = nome;
        this.marcaRemedio = marca;
        this.dosagem = validade;

    }

    public Remedio() {
    }

    public Remedio(Long id, String nome, Cuidador cuidador, String marcaRemedio, String dosagem, String formaFarmaceutico, Date dataCadastro, Date dataValidade, String observacoes, boolean stAtivo) {
        this.id = id;
        this.nome = nome;
        this.cuidador = cuidador;
        this.marcaRemedio = marcaRemedio;
        this.dosagem = dosagem;
        this.formaFarmaceutico = formaFarmaceutico;
        this.dataCadastro = dataCadastro;
        this.dataValidade = dataValidade;
        this.observacoes = observacoes;
        this.stAtivo = stAtivo;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Cuidador getCuidador() {
        return cuidador;
    }

    public void setCuidador(Cuidador cuidador) {
        this.cuidador = cuidador;
    }

    public String getMarcaRemedio() {
        return marcaRemedio;
    }

    public void setMarcaRemedio(String marcaRemedio) {
        this.marcaRemedio = marcaRemedio;
    }

    public String getDosagem() {
        return dosagem;
    }

    public void setDosagem(String dosagem) {
        this.dosagem = dosagem;
    }

    public String getFormaFarmaceutico() {
        return formaFarmaceutico;
    }

    public void setFormaFarmaceutico(String formaFarmaceutico) {
        this.formaFarmaceutico = formaFarmaceutico;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public Date getDataValidade() {
        return dataValidade;
    }

    public void setDataValidade(Date dataValidade) {
        this.dataValidade = dataValidade;
    }

    public String getObservacoes() {
        return observacoes;
    }

    public void setObservacoes(String observacoes) {
        this.observacoes = observacoes;
    }

    public boolean isStAtivo() {
        return stAtivo;
    }

    public void setStAtivo(boolean stAtivo) {
        this.stAtivo = stAtivo;
    }
}
