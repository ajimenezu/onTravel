package com.tec_diseno.ontravel.api;

import com.tec_diseno.ontravel.entities.BaseResult;
import com.tec_diseno.ontravel.responses.CategoriaResponse;
import com.tec_diseno.ontravel.responses.PaseoResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface RestInterface {

    @GET("categorias")
    Call<CategoriaResponse> getCategorias();

    @GET("paseos/reduced/{id}")
    Call<PaseoResponse> getPaseosCategoria(@Path("id") int idCategoria);

    @GET("paseos/image/{id}")
    Call<PaseoResponse> getImagenPaseo(@Path("id") int idPaseo);

    @GET("paseos/detail/{id}")
    Call<PaseoResponse> getPaseo(@Path("id") int idPaseo);

    @POST("compras")
    @FormUrlEncoded
    Call<BaseResult> ComprarPaseo(@Field("email") String email,
                                  @Field("paseo_id") int idPaseo,
                                  @Field("cantidad") int cantidad);

}
