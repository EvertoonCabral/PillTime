package com.everton.pilltime.models;

import java.util.Date;

public abstract class Pessoa {

    private Long id;


    private String nome;

    private String email;

    private java.util.Date dataNascimento;

    private String cpf;

    private String Telefone;


    private Endereco endereco;

    private boolean stAtivo;

    private String observacao;


    private Date dataCadastro;

    private TipoUsuario tipoUsuario;


    public Pessoa(Long id, String nome, String email, Date dataNascimento, String cpf, String telefone, Endereco endereco, boolean stAtivo, String observacao, Date dataCadastro, TipoUsuario tipoUsuario) {
        this.id = id;
        this.nome = nome;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        Telefone = telefone;
        this.endereco = endereco;
        this.stAtivo = stAtivo;
        this.observacao = observacao;
        this.dataCadastro = dataCadastro;
        this.tipoUsuario = tipoUsuario;
    }

    public Pessoa() {
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(Date dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getTelefone() {
        return Telefone;
    }

    public void setTelefone(String telefone) {
        Telefone = telefone;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public boolean isStAtivo() {
        return stAtivo;
    }

    public void setStAtivo(boolean stAtivo) {
        this.stAtivo = stAtivo;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }
}
