package com.everton.pilltime.api;

import com.everton.pilltime.dto.AlarmeDTO;
import com.everton.pilltime.alarme.Alarme;

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

public interface ApiAlarme {

    // Registrar um novo alarme
    @Headers("Content-Type: application/json")
    @POST("alarme")
    Call<AlarmeDTO> registrarAlarme(@Header("Authorization") String token, @Body AlarmeDTO alarmeDTO);

    // Editar um alarme
    @Headers("Content-Type: application/json")
    @PUT("alarme")
    Call<Alarme> editarAlarme(@Header("Authorization") String token, @Body AlarmeDTO alarmeDTO);

    // Deletar um alarme
    @DELETE("alarme/{id}")
    Call<Void> deletarAlarme(@Header("Authorization") String token, @Path("id") Long id);

    // Obter todos os alarmes
    @GET("alarme")
    Call<List<AlarmeDTO>> obterTodosAlarmes(@Header("Authorization") String token);

    // Obter um alarme pelo ID
    @GET("alarme/{id}")
    Call<Alarme> obterAlarmePorId(@Header("Authorization") String token, @Path("id") Long id);


}



