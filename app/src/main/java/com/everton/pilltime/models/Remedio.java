package com.everton.pilltime.models;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Remedio {

    private Long id;
    private String nome;
    private String marca;
    private String massaVolume;
    private String dosagem;
    private String formaFarmaceutico;
    private Date dataValidade;
    private String obs;



    public Remedio(Long id,String marca, String nome, String massaVolume, String dosagem, String formaFarmaceutico, Date dataValidade, String obs) {
        this.id = id;
        this.nome = nome;
        this.massaVolume = massaVolume;
        this.dosagem = dosagem;
        this.formaFarmaceutico = formaFarmaceutico;
        this.dataValidade = dataValidade;
        this.obs = obs;
        this.marca = marca;
    }

    // Novo construtor que aceita String e converte para Date
    public Remedio(String nome, String marca, String dataValidadeString) {
        this.nome = nome;
        this.marca = marca;
        try {
            this.dataValidade = new SimpleDateFormat("dd/MM/yyyy").parse(dataValidadeString);
        } catch (ParseException e) {
            e.printStackTrace();
            this.dataValidade = null;
        }
    }


    public Remedio() {
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

    public String getMassaVolume() {
        return massaVolume;
    }

    public void setMassaVolume(String massaVolume) {
        this.massaVolume = massaVolume;
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

    public String getObs() {
        return obs;
    }

    public void setObs(String obs) {
        this.obs = obs;
    }

    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }
}
