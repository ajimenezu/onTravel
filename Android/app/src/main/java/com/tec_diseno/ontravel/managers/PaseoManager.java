package com.tec_diseno.ontravel.managers;

import com.tec_diseno.ontravel.api.RestClient;
import com.tec_diseno.ontravel.entities.BaseResult;
import com.tec_diseno.ontravel.responses.PaseoResponse;

import retrofit2.Callback;

public class PaseoManager {

    public static void getPaseosCategoria(int idCategoria,Callback<PaseoResponse> callback) {

        RestClient.getsInstance().getService().getPaseosCategoria(idCategoria).enqueue(callback);
    }

    public static void getImagenPaseo(int idPaseo,Callback<PaseoResponse> callback) {

        RestClient.getsInstance().getService().getImagenPaseo(idPaseo).enqueue(callback);
    }

    public static void getPaseo(int idPaseo, Callback<PaseoResponse> callback){
        RestClient.getsInstance().getService().getPaseo(idPaseo).enqueue(callback);
    }

    public static void postComparPaseo(String email, int idPaseo, int cantidad, Callback<BaseResult> callback){
        RestClient.getsInstance().getService().ComprarPaseo(email,idPaseo,cantidad).enqueue(callback);
    }
}
