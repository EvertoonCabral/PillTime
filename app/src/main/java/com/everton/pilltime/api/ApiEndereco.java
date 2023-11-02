package com.everton.pilltime.api;

import com.everton.pilltime.dto.EnderecoDTO;
import com.everton.pilltime.models.Endereco;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiEndereco {


    @Headers("Content-Type: application/json")
    @POST("endereco")
    Call<EnderecoDTO> REGISTER_ENDERECO(@Header("Authorization") String token, @Body Endereco endereco);

    @GET("endereco")
    Call<List<EnderecoDTO>> GET_ALL_ENDERECO(@Header("Authorization") String authorization);

    @GET("endereco/{id}")
    Call<EnderecoDTO> GET_ENDERECO(@Header("Authorization") String authorization, @Path("id") Long id);

    @DELETE("endereco/{id}")
    Call<EnderecoDTO> DELETAR_ENDERECO(@Header("Authorization") String authorization, @Path("id") Long id);

}
