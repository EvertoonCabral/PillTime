package com.everton.pilltime.models;


import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Idoso extends Pessoa{

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

    public Idoso(String nome, Date dataNascimento, String cpf, String telefone) {
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.telefone = telefone;
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

    public Cuidador getCuidador() {
        return cuidador;
    }

    public void setCuidador(Cuidador cuidador) {
        this.cuidador = cuidador;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public List<Alarme> getListaAlarme() {
        return listaAlarme;
    }

    public void setListaAlarme(List<Alarme> listaAlarme) {
        this.listaAlarme = listaAlarme;
    }

    public int getIdade() {
        Calendar hoje = Calendar.getInstance();
        Calendar nascimento = Calendar.getInstance();
        nascimento.setTime(dataNascimento);
        int idade = hoje.get(Calendar.YEAR) - nascimento.get(Calendar.YEAR);
        if (hoje.get(Calendar.MONTH) < nascimento.get(Calendar.MONTH) ||
                (hoje.get(Calendar.MONTH) == nascimento.get(Calendar.MONTH) && hoje.get(Calendar.DAY_OF_MONTH) < nascimento.get(Calendar.DAY_OF_MONTH))) {
            idade--;
        }
        return idade;
    }


}
