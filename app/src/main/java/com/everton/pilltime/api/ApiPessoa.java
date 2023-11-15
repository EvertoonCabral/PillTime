package com.everton.pilltime.api;

import com.everton.pilltime.dto.PessoaDTO;
import com.everton.pilltime.dto.PessoaDTOGet;
import com.everton.pilltime.models.Pessoa;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiPessoa {

    @POST("pessoa")
    Call<Pessoa> inserirPessoa(@Header("Authorization") String token , @Body Pessoa pessoa);

    @PUT("pessoa")
    Call<PessoaDTO> editarPessoa(@Header("Authorization") String token, @Body Pessoa pessoa);

    @DELETE("pessoa/{id}")
    Call<Void> deletarPessoa(@Header("Authorization") String token, @Path("id") Long id);

    @GET("pessoa")
    Call<List<Pessoa>> listarPessoas(@Header("Authorization") String token);

    @GET("pessoa/id/{id}")
    Call<PessoaDTOGet> GET_PESSOA(@Header("Authorization") String token, @Path("id") Long id);

    @GET("pessoa/filter")
    Call<List<Pessoa>> buscarPessoaPorNome(@Header("Authorization") String token, @Query("nome") String nome);
}


