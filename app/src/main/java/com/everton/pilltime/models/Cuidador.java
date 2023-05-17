package com.everton.pilltime.models;

import com.everton.pilltime.models.enums.EnumSexo;

import java.util.ArrayList;
import java.util.List;

public class Cuidador {

    private String nome;
    private String email;
    private String cpf;

    private EnumSexo sexo;
    private String senha;
    private Endereco endereco;
    private List<Remedio> listaRemedios = new ArrayList<>();
    private List<Idoso> listaIdosos = new ArrayList<>();

    public Cuidador(String nome) {

    }

    public Cuidador(String nome, String email, String cpf, String senha, Endereco endereco, List<Remedio> listaRemedios, List<Idoso> listaIdosos, EnumSexo sexo) {
        this.nome = nome;
        this.email = email;
        this.cpf = cpf;
        this.senha = senha;
        this.endereco = endereco;
        this.listaRemedios = listaRemedios;
        this.listaIdosos = listaIdosos;
        this.sexo = sexo;
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

    public EnumSexo getSexo() {
        return sexo;
    }

    public void setSexo(EnumSexo sexo) {
        this.sexo = sexo;
    }

    @Override
    public String toString() {
        return "Cuidador{" +
                "nome='" + nome + '\'' +
                ", sexo=" + sexo + '\'' +
                ", email='" + email + '\'' +
                ", cpf='" + cpf + '\'' +
                ", senha='" + senha + '\'' +
                ", endereço=" + endereco +
                ", lista de remedios=" + listaRemedios +
                ", lista de Responsabilidade=" + listaIdosos +
                '}';
    }
}
