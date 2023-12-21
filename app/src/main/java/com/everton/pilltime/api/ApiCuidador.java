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

    @POST("cuidador")
    Call<Cuidador> insertCuidador(@Body CuidadorDTO cuidadorDto);

    @PUT("cuidador")
    Call<CuidadorDTO> editCuidador(@Body Cuidador cuidador);

    @DELETE("cuidador/{id}")
    Call<Void> deleteCuidador(@Path("id") Long id);

    @GET("cuidador")
    Call<List<CuidadorDTO>> findAllCuidadores();

    @GET("cuidador/{id}")
    Call<CuidadorDTO> GET_CUIDADOR(@Header("Authorization") String token, @Path("id") Long id);

    // Em ApiCuidador
    @GET("cuidador/full/{id}")
    Call<Cuidador> GET_CUIDADOR_FULL_BY_ID(@Header("Authorization") String token, @Path("id") Long id);


    @GET("cuidador/filter")
    Call<List<Cuidador>> findCuidadorByFilters(@Query("nome") String nome);

    @GET("cuidador/{cuidadorId}/idosos")
    Call<List<IdosoDTO>> GET_ALL_IDOSOS_CUIDADOR(@Header("Authorization") String token, @Path("cuidadorId") Long cuidadorId);

    // Adicionar remédio ao cuidador
    @POST("cuidador/{cuidadorId}/adicionar-remedio")
    Call<String> POST_REMEDIO_TO_CUIDADOR ( @Header("Authorization") String token, @Path("cuidadorId")Long cuidadorId, @Body RemedioDTO remedioDTO);

    // Listar todos os remédios de um cuidador específico
    @GET("cuidador/{cuidadorId}/remedios")
    Call<List<RemedioDTO>> GET_ALL_REMEDIO_CUIDADOR( @Header("Authorization") String token, @Path("cuidadorId") Long cuidadorId);


    // Adicionar um alarme à lista de alarmes de um idoso
    @POST("cuidador/{cuidadorId}/idoso/alarme")
    Call<String> POST_ALARME_TO_IDOSO_LIST(@Header("Authorization") String token,
                                           @Path("cuidadorId") Long cuidadorId,
                                           @Query("cpfIdoso") String cpfIdoso,
                                           @Body AlarmeDTOInsert alarmeDtoInsert);


    // Dentro da interface ApiCuidador

    @PUT("cuidador/{cuidadorId}/remedio")
    Call<Void> updateRemedioByNome(
            @Header("Authorization") String token,
            @Path("cuidadorId") Long cuidadorId,
            @Query("nomeRemedio") String nomeRemedio,
            @Body RemedioDTO remedioDTO
    );




}
