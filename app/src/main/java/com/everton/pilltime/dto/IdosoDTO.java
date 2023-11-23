package com.everton.pilltime.dto;

import com.everton.pilltime.models.TipoUsuario;
import com.everton.pilltime.user.UserRole;

import java.util.Date;

public class IdosoDTO {

    private String nome;
    private String email;
    private String login;

    private String senha;

    private String cpf;

    private String telefone;
    private Date dataNascimento;
    private String observacao;
    private EnderecoDTO endereco;
    private UserRole role;

    private TipoUsuario tipoUsuario = TipoUsuario.I;

    private String cpfCuidador;


    public IdosoDTO(String nome, String email, Date dataNascimento, String cpf, String telefone, EnderecoDTO endereco, String observacao, String login, String senha, UserRole role, TipoUsuario tipoUsuario, String cpfCuidador) {
        this.nome = nome;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.cpf = cpf;
        this.telefone = telefone;
        this.endereco = endereco;
        this.observacao = observacao;
        this.login = login;
        this.senha = senha;
        this.role = role;
        this.tipoUsuario = tipoUsuario;
        this.cpfCuidador = cpfCuidador;
    }

    public IdosoDTO() {
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
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public EnderecoDTO getEndereco() {
        return endereco;
    }

    public void setEndereco(EnderecoDTO endereco) {
        this.endereco = endereco;
    }

    public String getObservacao() {
        return observacao;
    }

    public void setObservacao(String observacao) {
        this.observacao = observacao;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public UserRole getRole() {
        return role;
    }

    public void setRole(UserRole role) {
        this.role = role;
    }

    public TipoUsuario getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(TipoUsuario tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
    }

    public String getCpfCuidador() {
        return cpfCuidador;
    }

    public void setCpfCuidador(String cpfCuidador) {
        this.cpfCuidador = cpfCuidador;
    }

    @Override
    public String toString() {
        return nome +" - "+ cpf;

    }
}
