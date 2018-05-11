package com.tec_diseno.ontravel.managers;

import com.tec_diseno.ontravel.api.RestClient;
import com.tec_diseno.ontravel.responses.CategoriaResponse;

import retrofit2.Callback;

public class CategoriaManager {

    public static void getCategorias(Callback<CategoriaResponse> callback) {

        RestClient.getsInstance().getService().getCategorias().enqueue(callback);
    }
}
