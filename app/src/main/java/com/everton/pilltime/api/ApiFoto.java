package com.everton.pilltime.api;

import com.everton.pilltime.dto.AlarmeDTO;
import com.everton.pilltime.dto.ResponseFotoDTO;
import com.everton.pilltime.models.Foto;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface ApiFoto {

    @Multipart
    @POST("/fotos/upload")
    Call<ResponseFotoDTO> UPLOAD_FOTO(
            @Header("Authorization") String token,
            @Part MultipartBody.Part file
    );

    @GET("/fotos/{id}")
    Call<Foto> getFotoById(@Header("Authorization") String token, @Path("id") Long id);

    @GET("/fotos")
    Call<List<Foto>> getAllFotos();

    @Multipart
    @PUT("/fotos/{id}")
    Call<Foto> updateFoto(@Path("id") Long id,
                          @Part MultipartBody.Part file);

    @DELETE("/fotos/{id}")
    Call<Void> deleteFoto(@Path("id") Long id);






}
