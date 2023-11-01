package com.everton.pilltime.dto;

import java.util.Date;

public class RemedioDTO {

    private String nome;
    private String marcaRemedio;
    private String dosagem;
    private String formaFarmaceutico;
    private Date dataValidade;
    private String observacoes;


    public RemedioDTO(String nome, String marcaRemedio, String dosagem, String formaFarmaceutico, Date dataValidade, String observacoes) {
        this.nome = nome;
        this.marcaRemedio = marcaRemedio;
        this.dosagem = dosagem;
        this.formaFarmaceutico = formaFarmaceutico;
        this.dataValidade = dataValidade;
        this.observacoes = observacoes;
    }

    public RemedioDTO() {
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
}
