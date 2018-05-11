package com.tec_diseno.ontravel;

import android.app.Application;
import android.content.Context;

import com.tec_diseno.ontravel.responses.CategoriaResponse;

public class OnTravelApplication extends Application {

    private static Context mContext;
    public static CategoriaResponse categorias;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = this;
    }

    public static Context getContext() {
        return mContext;
    }
}
