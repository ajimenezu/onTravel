package com.tec_diseno.ontravel;

import android.app.Application;
import android.content.Context;

import com.tec_diseno.ontravel.entities.Categoria;
import com.tec_diseno.ontravel.entities.Paseo;

import java.util.ArrayList;

public class OnTravelApplication extends Application {

    private static Context mContext;
    public static ArrayList<Categoria> categorias;
    public static Paseo paseo;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext() {
        return mContext;
    }
}
