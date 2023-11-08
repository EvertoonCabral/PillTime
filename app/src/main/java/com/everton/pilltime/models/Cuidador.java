package com.everton.pilltime.models;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Cuidador extends Pessoa{


    private List<Remedio> listaRemedios = new ArrayList<>();
    private List<Idoso> listaIdosos = new ArrayList<>();

    public Cuidador() {

    }

    public Cuidador(Long id, String nome, String email, Date dataNascimento, String cpf, String telefone, Endereco endereco, boolean stAtivo, String observacao, Date dataCadastro, TipoUsuario tipoUsuario, List<Remedio> listaRemedios, List<Idoso> listaIdosos) {
        super(id, nome, email, dataNascimento, cpf, telefone, endereco, stAtivo, observacao, dataCadastro, tipoUsuario);
        this.listaRemedios = listaRemedios;
        this.listaIdosos = listaIdosos;
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



}
