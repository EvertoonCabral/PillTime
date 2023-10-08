package com.everton.pilltime.models;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Idoso {

    private Long id;
    private String nome;
    private String email;
    private String senha;
    private Date dataNascimento;
    private String cpf;
    private Cuidador cuidador;
    private String telefone;
    private String observacao;
    private Endereco endereco;


    private List<Alarme> listaAlarme = new ArrayList<>();


    public Idoso(Long id, String nome, String email, String senha, Date dataNascimento, String cpf, Cuidador cuidador, String telefone, String observacao, Endereco endereco, List<Alarme> listaAlarme) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.cuidador = cuidador;
        this.telefone = telefone;
        this.observacao = observacao;
        this.endereco = endereco;
        this.listaAlarme = listaAlarme;
    }

    public Idoso() {

    }




}
