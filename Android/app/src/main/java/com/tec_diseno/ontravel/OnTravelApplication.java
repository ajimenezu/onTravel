package com.tec_diseno.ontravel;

import android.app.Application;
import android.content.Context;

import com.tec_diseno.ontravel.entities.Categoria;
import com.tec_diseno.ontravel.entities.Viaje;
import com.tec_diseno.ontravel.responses.CategoriaResponse;

import java.util.ArrayList;
import java.util.List;

public class OnTravelApplication extends Application {

    private static Context mContext;
    public static ArrayList<Categoria> categorias;
    public static ArrayList<Viaje> viajes;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext() {
        return mContext;
    }
}
