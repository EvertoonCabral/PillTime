package com.everton.pilltime.dto;

import com.everton.pilltime.models.Endereco;

public class EnderecoDTO {


    private String estado;

    private String cidade;

    private String bairro;

    private String rua;

    private int numeroResidencia;

    private String complemento;

    public EnderecoDTO(String estado, String cidade, String bairro, String rua, int numeroResidencia, String complemento) {
        this.estado = estado;
        this.cidade = cidade;
        this.bairro = bairro;
        this.rua = rua;
        this.numeroResidencia = numeroResidencia;
        this.complemento = complemento;
    }

    public EnderecoDTO() {
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCidade() {
        return cidade;
    }

    public void setCidade(String cidade) {
        this.cidade = cidade;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    public String getRua() {
        return rua;
    }

    public void setRua(String rua) {
        this.rua = rua;
    }

    public int getNumeroResidencia() {
        return numeroResidencia;
    }

    public void setNumeroResidencia(int numeroResidencia) {
        this.numeroResidencia = numeroResidencia;
    }

    public String getComplemento() {
        return complemento;
    }

    public void setComplemento(String complemento) {
        this.complemento = complemento;
    }

    public static Endereco fromDTO(EnderecoDTO dto) {
        Endereco endereco = new Endereco();
        endereco.setEstado(dto.getEstado());
        endereco.setCidade(dto.getCidade());
        endereco.setBairro(dto.getBairro());
        endereco.setRua(dto.getRua());
        endereco.setNumeroResidencia(dto.getNumeroResidencia());
        endereco.setComplemento(dto.getComplemento());
        // ID, dataCadastro, stAtivo podem ser configurados aqui se necessário, ou podem ser deixados para serem configurados em outra parte do código.
        return endereco;
    }
    public static EnderecoDTO toDTO(Endereco endereco) {
        return new EnderecoDTO(
                endereco.getEstado(),
                endereco.getCidade(),
                endereco.getBairro(),
                endereco.getRua(),
                endereco.getNumeroResidencia(),
                endereco.getComplemento());
    }


}
