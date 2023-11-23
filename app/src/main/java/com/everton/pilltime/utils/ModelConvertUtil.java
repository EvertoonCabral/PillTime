package com.everton.pilltime.utils;

import com.everton.pilltime.dto.EnderecoDTO;
import com.everton.pilltime.dto.IdosoDTO;
import com.everton.pilltime.dto.RemedioDTO;
import com.everton.pilltime.models.Endereco;
import com.everton.pilltime.models.Idoso;
import com.everton.pilltime.models.Remedio;
import com.everton.pilltime.models.TipoUsuario;

public class ModelConvertUtil {

    public static Remedio converterParaModeloRemedio(RemedioDTO remedioDTO) {
        if (remedioDTO == null) {
            return null;
        }

        Remedio remedio = new Remedio();
        remedio.setNome(remedioDTO.getNome());
        remedio.setMarcaRemedio(remedioDTO.getMarcaRemedio());
        remedio.setDosagem(remedioDTO.getDosagem());
        remedio.setFormaFarmaceutico(remedioDTO.getFormaFarmaceutico());
        remedio.setDataValidade(remedioDTO.getDataValidade());
        remedio.setObservacoes(remedioDTO.getObservacoes());
        remedio.setStAtivo(true);


        return remedio;
    }


    public static Idoso converterParaModeloIdoso(IdosoDTO idosoDTO) {
        if (idosoDTO == null) {
            return null;
        }

        Idoso idoso = new Idoso();
        idoso.setNome(idosoDTO.getNome());
        idoso.setEmail(idosoDTO.getEmail());
        idoso.setCpf(idosoDTO.getCpf());
        idoso.setTelefone(idosoDTO.getTelefone());
        idoso.setDataNascimento(idosoDTO.getDataNascimento());
        idoso.setEndereco(converterParaModeloEndereco(idosoDTO.getEndereco()));
        idoso.setObservacao(idosoDTO.getObservacao());
        idoso.setTipoUsuario(TipoUsuario.I);
        idoso.setStAtivo(true);
        return idoso;
    }

    public static Endereco converterParaModeloEndereco(EnderecoDTO enderecoDTO) {
        if (enderecoDTO == null) {
            return null;
        }

        Endereco endereco = new Endereco();
        endereco.setRua(enderecoDTO.getRua());
        endereco.setCidade(enderecoDTO.getCidade());
        endereco.setEstado(enderecoDTO.getEstado());
        endereco.setComplemento(enderecoDTO.getComplemento());
        endereco.setNumeroResidencia(enderecoDTO.getNumeroResidencia());
        endereco.isStAtivo();

        return endereco;
    }




}
