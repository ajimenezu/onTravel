package com.tec_diseno.ontravel.api;

import com.tec_diseno.ontravel.responses.CategoriaResponse;
import com.tec_diseno.ontravel.responses.PaseoResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestInterface {

    @GET("categorias")
    Call<CategoriaResponse> getCategorias();

    @GET("paseos/reduced/{id}")
    Call<PaseoResponse> getPaseosCategoria(@Path("id") int idCategoria);

    @GET("paseos/image/{id}")
    Call<PaseoResponse> getImagenPaseo(@Path("id") int idPaseo);

}
