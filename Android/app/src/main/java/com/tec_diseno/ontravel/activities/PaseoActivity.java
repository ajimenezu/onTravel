package com.tec_diseno.ontravel.activities;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;

import com.tec_diseno.ontravel.OnTravelApplication;
import com.tec_diseno.ontravel.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PaseoActivity extends AppCompatActivity {

    @BindView(R.id.paseo_app_bar)
    AppBarLayout barLayout;
    @BindView(R.id.img_paseo_sin_imagen)
    ImageView imgSinImagen;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paseo);

        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(OnTravelApplication.paseo.getName());
        getSupportActionBar().setSubtitle(OnTravelApplication.paseo.getLocalizacion());

        CargarImagen();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void CargarImagen()
    {

        Thread thread = new Thread(){

            @Override
            public void run() {
                try {
                // Block this thread for 2 seconds.
                while(OnTravelApplication.paseo.getImagen() == null) {
                    imgSinImagen.setVisibility(View.VISIBLE);
                        sleep(1000);
                }

                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            imgSinImagen.setVisibility(View.INVISIBLE);
                            barLayout.setBackground(OnTravelApplication.paseo.getImagen().getConstantState().newDrawable().mutate());
                        }
                    });

                } catch (InterruptedException e) {
                    e.printStackTrace();
                }

        }};

        thread.start();
    }
}
