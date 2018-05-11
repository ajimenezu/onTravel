package com.tec_diseno.ontravel.activities;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


import com.tec_diseno.ontravel.OnTravelApplication;
import com.tec_diseno.ontravel.R;
import com.tec_diseno.ontravel.entities.Categoria;
import com.tec_diseno.ontravel.managers.CategoriaManager;
import com.tec_diseno.ontravel.responses.CategoriaResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SplashActivity extends AppCompatActivity implements Callback<CategoriaResponse> {

    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;

        setContentView(R.layout.activity_splash);
        CargarCategorias();
    }

    public void CargarCategorias()
    {
        CategoriaManager.getCategorias(this);
    }

    @Override
    public void onResponse(Call<CategoriaResponse> call, Response<CategoriaResponse> response) {
        if(response.isSuccessful())
        {
            OnTravelApplication.categorias = response.body();
            Intent i = new Intent(this, MainActivity.class);
            startActivity(i);
        }
        else
            Snackbar.make(findViewById(R.id.splash_content), "Problemas cargando datos",
                    Snackbar.LENGTH_LONG).setAction("REINTENTAR", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CargarCategorias();
                }
            }).show();

    }

    @Override
    public void onFailure(Call<CategoriaResponse> call, Throwable t) {
        Snackbar.make(findViewById(R.id.splash_content), "No hay comunicación con el servidor de datos",
                Snackbar.LENGTH_LONG).setAction("REINTENTAR", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CargarCategorias();
            }
        }).show();
    }
}
