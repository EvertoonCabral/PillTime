package com.everton.pilltime.api;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit {

    public static final String BASE_URL_API = "http://10.0.2.2:8080/";

    // Crie o Gson que será usado pelo Retrofit para a serialização e deserialização de datas
    private static Gson gson = new GsonBuilder()
            .setDateFormat("dd/MM/yyyy")
            .create();

    private static retrofit2.Retrofit retrofit = new retrofit2.Retrofit.Builder()
            .baseUrl(BASE_URL_API)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();


    //LOGIN
    public static ApiUser LOGIN_CALL() {
        return retrofit.create(ApiUser.class);

    }

    //USUARIO
    public static ApiUser REGISTER_USER(){
        return retrofit.create(ApiUser.class);
    }

    public static ApiUser REGISTER_IDOSO(){return  retrofit.create(ApiUser.class);}

    public static ApiUser GET_USUARIO(){

        return retrofit.create(ApiUser.class);}

    public static ApiUser GET_ALL_USUARIO(){
        return retrofit.create(ApiUser.class);
    }

    public static ApiUser DELETAR_USUARIO() { return retrofit.create(ApiUser.class); }



    //REMEDIO

    public static ApiRemedio REGISTER_REMEDIO(){return  retrofit.create(ApiRemedio.class);}


    //PESSOA
    public static ApiPessoa GET_PESSOA(){ return retrofit.create(ApiPessoa.class);}
    public static ApiPessoa GET_PESSOA_BY_CPF(){ return retrofit.create(ApiPessoa.class);}




    //CUIDADOR
    public static ApiCuidador GET_CUIDADOR(){ return retrofit.create(ApiCuidador.class);}
    public static ApiCuidador POST_REMEDIO_TO_CUIDADOR(){return  retrofit.create(ApiCuidador.class);}
    public static ApiCuidador GET_ALL_REMEDIO_CUIDADOR (){return  retrofit.create(ApiCuidador.class);}

    //IDOSO
    public static ApiIdoso GET_ALL_IDOSOS (){return  retrofit.create(ApiIdoso.class);}
    public static ApiIdoso GET_IDOSO_BY_ID (){return  retrofit.create(ApiIdoso.class);}


}
