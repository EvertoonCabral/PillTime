package com.everton.pilltime.api;

import com.everton.pilltime.dto.EnderecoDTO;
import com.everton.pilltime.dto.RemedioDTO;
import com.everton.pilltime.models.Endereco;
import com.everton.pilltime.models.Remedio;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiRemedio {

    @Headers("Content-Type: application/json")
    @POST("remedio")
    Call<RemedioDTO> REGISTER_REMEDIO(@Header("Authorization") String token, @Body RemedioDTO remedioDTO);

    @GET("remedio")
    Call<List<RemedioDTO>> GET_ALL_REMEDIO(@Header("Authorization") String authorization);

    @GET("remedio/{id}")
    Call<RemedioDTO> GET_REMEDIO(@Header("Authorization") String authorization, @Path("id") Long id);

    @DELETE("remedio/{id}")
    Call<RemedioDTO> DELETAR_REMEDIO(@Header("Authorization") String authorization, @Path("id") Long id);

}
