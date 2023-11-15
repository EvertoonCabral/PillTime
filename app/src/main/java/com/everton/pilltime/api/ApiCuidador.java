package com.everton.pilltime.api;

import com.everton.pilltime.dto.AlarmeDTOInsert;
import com.everton.pilltime.dto.CuidadorDTO;
import com.everton.pilltime.dto.IdosoDTO;
import com.everton.pilltime.dto.RemedioDTO;
import com.everton.pilltime.models.Cuidador;

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

public interface ApiCuidador {

    // Adicionar um cuidador
    @POST("cuidador")
    Call<Cuidador> insertCuidador(@Body CuidadorDTO cuidadorDto);

    // Editar um cuidador
    @PUT("cuidador")
    Call<CuidadorDTO> editCuidador(@Body Cuidador cuidador);

    // Deletar um cuidador
    @DELETE("cuidador/{id}")
    Call<Void> deleteCuidador(@Path("id") Long id);

    // Retorna uma lista de cuidadores
    @GET("cuidador")
    Call<List<CuidadorDTO>> findAllCuidadores();

    // Retorna um cuidador pelo ID
    @GET("cuidador/{id}")
    Call<CuidadorDTO> GET_CUIDADOR(@Header("Authorization") String token, @Path("id") Long id);

    // Obter um cuidador pelo nome
    @GET("cuidador/filter")
    Call<List<Cuidador>> findCuidadorByFilters(@Query("nome") String nome);

    // Retorna todos os Idosos da Lista do Cuidador
    @GET("cuidador/{cuidadorId}/idosos")
    Call<List<IdosoDTO>> getIdososByCuidador(@Path("cuidadorId") Long cuidadorId);

    // Adicionar remédio ao cuidador
    @POST("cuidador/{cuidadorId}/adicionar-remedio")
    Call<String> POST_REMEDIO_TO_CUIDADOR ( @Header("Authorization") String token, @Path("cuidadorId")Long cuidadorId, @Body RemedioDTO remedioDTO);

    // Listar todos os remédios de um cuidador específico
    @GET("cuidador/{cuidadorId}/remedios")
    Call<List<RemedioDTO>> listRemediosByCuidador(@Path("cuidadorId") Long cuidadorId);


    // Adicionar um alarme à lista de alarmes de um idoso
    @POST("cuidador/{cuidadorId}/idoso/{idosoId}/alarme")
    Call<String> addAlarmeToIdoso(@Path("cuidadorId") Long cuidadorId, @Path("idosoId") Long idosoId, @Body AlarmeDTOInsert alarmeDtoInsert);


}
