package com.everton.pilltime.models;

import com.everton.pilltime.dto.EnderecoDTO;

import java.util.Date;

public class Endereco {

        private Long id;

        private String estado;

        private String cidade;
        private String bairro;
        private String rua;
        private int numeroResidencia;
        private String complemento;
        private Date dataCadastro;

        private boolean stAtivo;

        public Endereco(Long id, String estado, String cidade, String bairro, String rua, int numeroResidencia, String complemento, Date dataCadastro, boolean stAtivo) {
            this.id = id;
            this.estado = estado;
            this.cidade = cidade;
            this.bairro = bairro;
            this.rua = rua;
            this.numeroResidencia = numeroResidencia;
            this.complemento = complemento;
            this.dataCadastro = dataCadastro;
            this.stAtivo = stAtivo;
        }


        public Endereco() {
        }

        public Long getId() {
            return id;
        }

        public void setId(Long id) {
            this.id = id;
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

        public Date getDataCadastro() {
            return dataCadastro;
        }

        public void setDataCadastro(Date dataCadastro) {
            this.dataCadastro = dataCadastro;
        }

        public boolean isStAtivo() {
            return stAtivo;
        }

        public void setStAtivo(boolean stAtivo) {
            this.stAtivo = stAtivo;
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

    @Override
    public String toString() {
        return "Endereco{" +
                "id=" + id +
                ", estado='" + estado + '\'' +
                ", cidade='" + cidade + '\'' +
                ", bairro='" + bairro + '\'' +
                ", rua='" + rua + '\'' +
                ", numeroResidencia=" + numeroResidencia +
                ", complemento='" + complemento + '\'' +
                ", dataCadastro=" + dataCadastro +
                ", stAtivo=" + stAtivo +
                '}';
    }
}


