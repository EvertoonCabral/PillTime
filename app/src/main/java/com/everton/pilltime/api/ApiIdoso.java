package com.everton.pilltime.api;

import com.everton.pilltime.dto.AlarmeDTOInsert;
import com.everton.pilltime.dto.IdosoDTO;
import com.everton.pilltime.models.Idoso;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiIdoso {

    // Registrar um novo idoso
    @Headers("Content-Type: application/json")
    @POST("idoso")
    Call<IdosoDTO> registrarIdoso(@Header("Authorization") String token, @Body IdosoDTO idosoDTO);

    // Editar um idoso
    @Headers("Content-Type: application/json")
    @PUT("idoso")
    Call<Idoso> editarIdoso(@Header("Authorization") String token, @Body IdosoDTO idosoDTO);

    // Deletar um idoso
    @DELETE("idoso/{id}")
    Call<Void> deletarIdoso(@Header("Authorization") String token, @Path("id") Long id);

    // Obter todos os idosos
    @GET("idoso")
    Call<List<Idoso>> obterTodosIdosos(@Header("Authorization") String token);

    // Obter um idoso pelo ID
    @GET("idoso/{id}")
    Call<Idoso> obterIdosoPorId(@Header("Authorization") String token, @Path("id") Long id);

    // Obter idosos pelo nome
    @GET("idoso/filter")
    Call<List<Idoso>> obterIdososPorNome(@Header("Authorization") String token, @Query("nome") String nome);

    // Obter alarmes de um idoso específico
    @GET("idoso/{id}/alarmes")
    Call<List<AlarmeDTOInsert>> obterAlarmesDeIdoso(@Header("Authorization") String token, @Path("id") Long id);


}
