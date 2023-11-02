package com.everton.pilltime.api;

import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit {

    public static final String BASE_URL_API = "http://10.0.2.2:8080/"

    private static retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
            .baseUrl(BASE_URL_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    //LOGIN
    public static ApiUser LOGIN_CALL() {
        return retrofit.create(ApiUser.class);

    }

    //USUARIO
    public static ApiUser REGISTER_USER(){
        return retrofit.create(ApiUser.class);
    }

    public static ApiUser GET_USUARIO(){

        return retrofit.create(ApiUser.class);}

    public static ApiUser GET_ALL_USUARIO(){
        return retrofit.create(ApiUser.class);
    }

    public static ApiUser DELETAR_USUARIO() { return retrofit.create(ApiUser.class); }



}
