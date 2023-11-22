package com.everton.pilltime.dto;

import com.everton.pilltime.models.Endereco;
import com.everton.pilltime.models.TipoUsuario;

import java.util.Date;

public class PessoaDTO {

    private String nome;
    private String email;
    private Date dataNascimento;
    private String cpf;
    private String Telefone;

    private Endereco endereco;

    private String observacao;


    public PessoaDTO(String nome, String email, Date dataNascimento, String cpf, String telefone, Endereco endereco, String observacao) {
        this.nome = nome;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        Telefone = telefone;
        this.endereco = endereco;
        this.observacao = observacao;
    }

    public PessoaDTO() {
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

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }
}
