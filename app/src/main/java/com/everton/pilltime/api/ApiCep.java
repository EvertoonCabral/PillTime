package com.everton.pilltime.api;

import com.everton.pilltime.models.CEP;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface ApiCep {


    @GET("ws/{cep}/json/")
    Call<CEP> consultarCEP(@Path("cep") String cep);


}
