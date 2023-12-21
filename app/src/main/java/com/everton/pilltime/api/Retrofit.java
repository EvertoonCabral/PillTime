package com.everton.pilltime.api;

import com.everton.pilltime.dto.AlarmeDTOInsert;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit {



    public static final String CEP_API = "https://viacep.com.br/";

    private static retrofit2.Retrofit retrofitcep = new retrofit2.Retrofit.Builder()
            .baseUrl(CEP_API)
            .addConverterFactory(GsonConverterFactory.create())
            .build();


    private static Gson gson = new GsonBuilder()
            .setDateFormat("dd/MM/yyyy")
            .create();


    public static final String BASE_URL_API = "http://10.0.2.2:8080/";
    // public static final String BASE_URL_API = "https://f9c8-177-91-39-96.ngrok.io/";
    private static retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
            .baseUrl(BASE_URL_API)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();







    //LOGIN
    public static ApiUser LOGIN_CALL() {
        return retrofit.create(ApiUser.class);

    }



    //CEP
    public static ApiCep API_CEP() {
        return retrofitcep.create(ApiCep.class);
    }

    //USUARIO
    public static ApiUser REGISTER_USER() {
        return retrofit.create(ApiUser.class);
    }

    public static ApiUser REGISTER_IDOSO() {
        return retrofit.create(ApiUser.class);
    }

    public static ApiUser GET_USUARIO() {

        return retrofit.create(ApiUser.class);
    }

    public static ApiUser GET_ALL_USUARIO() {
        return retrofit.create(ApiUser.class);
    }

    public static ApiUser DELETAR_USUARIO() {
        return retrofit.create(ApiUser.class);
    }


    //REMEDIO

    public static ApiRemedio REGISTER_REMEDIO() {
        return retrofit.create(ApiRemedio.class);
    }


    //PESSOA
    public static ApiPessoa GET_PESSOA() {
        return retrofit.create(ApiPessoa.class);
    }

    public static ApiPessoa GET_PESSOA_BY_CPF() {
        return retrofit.create(ApiPessoa.class);
    }

    public static ApiPessoa GET_PESSOA_ENTITY() {
        return retrofit.create(ApiPessoa.class);
    }

    public static ApiAlarme GET_ALARME_IDOSO() {
        return retrofit.create(ApiAlarme.class);
    }


    //CUIDADOR
    public static ApiCuidador GET_CUIDADOR() {
        return retrofit.create(ApiCuidador.class);
    }

    public static ApiCuidador POST_REMEDIO_TO_CUIDADOR() {
        return retrofit.create(ApiCuidador.class);
    }

    public static ApiCuidador GET_ALL_REMEDIO_CUIDADOR() {
        return retrofit.create(ApiCuidador.class);
    }

    public static ApiCuidador POST_ALARME_TO_IDOSO_LIST() {
        return retrofit.create(ApiCuidador.class);
    }
    public static ApiCuidador GET_FULL_CUIDADOR() {
        return retrofit.create(ApiCuidador.class);
    }


    public static ApiCuidador UPDATE_REMEDIO(){return  retrofit.create(ApiCuidador.class);}


    //IDOSO
    public static ApiIdoso GET_ALL_IDOSOS() {
        return retrofit.create(ApiIdoso.class);
    }

    public static ApiIdoso GET_IDOSO_BY_ID() {
        return retrofit.create(ApiIdoso.class);
    }

    public static ApiIdoso GET_IDOSO_BY_CPF() {
        return retrofit.create(ApiIdoso.class);
    }

    public static ApiIdoso get_Idoso_With_Cuidador () {
        return retrofit.create(ApiIdoso.class);
    }


    //FOTO
    public static ApiFoto UPLOAD_FOTO() {
        return retrofit.create(ApiFoto.class);
    }

    public static ApiFoto getFotoById() {
        return retrofit.create(ApiFoto.class);
    }

    //EMAIL
    public static ApiEmail.ApiEmailService GET_EMAIL_SERVICE() {
        return retrofit.create(ApiEmail.ApiEmailService.class);
    }


}
