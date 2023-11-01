package com.everton.pilltime.dto;

import java.util.Date;

public class CuidadorDTO {

    private String nome;

    private String email;
    private String Senha;

    private Date dataNascimento;

    private String cpf;
    private String Telefone;
    private EnderecoDTO endereco;
    private boolean stAtivo;

    public CuidadorDTO(String nome, String email, String senha, Date dataNascimento, String cpf, String telefone, EnderecoDTO endereco, boolean stAtivo) {
        this.nome = nome;
        this.email = email;
        Senha = senha;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        Telefone = telefone;
        this.endereco = endereco;
        this.stAtivo = stAtivo;
    }

    public CuidadorDTO() {
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

    public String getSenha() {
        return Senha;
    }

    public void setSenha(String senha) {
        Senha = senha;
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

    public EnderecoDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoDTO endereco) {
        this.endereco = endereco;
    }

    public boolean isStAtivo() {
        return stAtivo;
    }

    public void setStAtivo(boolean stAtivo) {
        this.stAtivo = stAtivo;
    }
}
