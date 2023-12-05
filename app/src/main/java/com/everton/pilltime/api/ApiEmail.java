package com.everton.pilltime.api;

import com.everton.pilltime.models.EmailRequest;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiEmail {

    public interface ApiEmailService {
        @POST("email/send")
        Call<Void> enviarEmail(@Body EmailRequest emailRequest);
    }


}
