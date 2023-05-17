package com.everton.pilltime.models;

import com.everton.pilltime.models.enums.EnumSexo;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Idoso {

    private String nome;
    private String email;
    private String senha;
    private Date dataNascimento;
    private String cpf;

    private EnumSexo sexo;
    private Cuidador resposavel;
    private String observacao;

    private List<Remedio> listaRemedio = new ArrayList<>();

    private Endereco endereco;


    public Idoso(String nome) {

    }


    public Idoso(String nome, String email, String senha, Date dataNascimento, String cpf, Cuidador resposavel, String observacao, List<Remedio> listaRemedio, Endereco endereco,EnumSexo sexo) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.resposavel = resposavel;
        this.observacao = observacao;
        this.listaRemedio = listaRemedio;
        this.endereco = endereco;
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

    public Cuidador getResposavel() {
        return resposavel;
    }

    public void setResposavel(Cuidador resposavel) {
        this.resposavel = resposavel;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public List<Remedio> getListaRemedio() {
        return listaRemedio;
    }

    public void setListaRemedio(List<Remedio> listaRemedio) {
        this.listaRemedio = listaRemedio;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public EnumSexo getSexo() {
        return sexo;
    }

    public void setSexo(EnumSexo sexo) {
        this.sexo = sexo;
    }

    @Override
    public String toString() {
        return "Idoso{" +
                "nome='" + nome + '\'' +
                ", email='" + email + '\'' +
                ", senha='" + senha + '\'' +
                ", dataNascimento=" + dataNascimento +
                ", cpf='" + cpf + '\'' +
                ", resposavel=" + resposavel +
                ", observações ='" + observacao + '\'' +
                ", Lista de Remedios=" + listaRemedio +
                ", endereço=" + endereco +
                ", sexo=" + sexo +
                '}';
    }
}
