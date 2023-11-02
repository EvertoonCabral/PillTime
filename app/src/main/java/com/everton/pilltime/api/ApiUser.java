package com.everton.pilltime.api;

import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface ApiUser {

    @Headers("Content-Type: application/json")
    @POST("auth/login")
    Call<LoginResponseDTO> LOGIN_CALL(@Body AuthenticationDTO authenticationDTO);
    @GET("usuario")
    Call<List<UsuarioDTO>> GET_ALL_USUARIO(@Header("Authorization") String authorization);

    @GET("usuario/{id}")
    Call<UsuarioDTO> GET_USUARIO(@Header("Authorization") String authorization, @Path("id") Long id);

    @Headers("Content-Type: application/json")
    @PUT("usuario")
    Call<UsuarioDTO> ALTERAR_USUARIO(@Header("Authorization") String token, @Body UsuarioDTO usuarioEditDTO);

    @DELETE("usuario/{id}")
    Call<UsuarioDTO> DELETE_USUARIO(@Header("Authorization") String authorization, @Path("id") Long id);

}