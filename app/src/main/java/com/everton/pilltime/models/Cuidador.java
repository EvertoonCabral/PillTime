package com.everton.pilltime.models;


import java.util.ArrayList;
import java.util.List;

public class Cuidador extends Pessoa{

    private String nome;
    private String email;
    private String senha;
    private String cpf;
    private Endereco endereco;
    private String telefone;
    private List<Remedio> listaRemedios = new ArrayList<>();
    private List<Idoso> listaIdosos = new ArrayList<>();

    public Cuidador() {

    }

    public Cuidador(String nome, String email, String senha, String cpf, Endereco endereco, String telefone, List<Remedio> listaRemedios, List<Idoso> listaIdosos) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.cpf = cpf;
        this.endereco = endereco;
        this.telefone = telefone;
        this.listaRemedios = listaRemedios;
        this.listaIdosos = listaIdosos;
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
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<Remedio> getListaRemedios() {
        return listaRemedios;
    }

    public void setListaRemedios(List<Remedio> listaRemedios) {
        this.listaRemedios = listaRemedios;
    }

    public List<Idoso> getListaIdosos() {
        return listaIdosos;
    }

    public void setListaIdosos(List<Idoso> listaIdosos) {
        this.listaIdosos = listaIdosos;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

}
