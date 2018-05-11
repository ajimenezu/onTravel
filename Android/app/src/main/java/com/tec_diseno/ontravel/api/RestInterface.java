package com.tec_diseno.ontravel.api;

import com.tec_diseno.ontravel.responses.CategoriaResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RestInterface {

    @GET("categorias")
    Call<CategoriaResponse> getCategorias();

}
