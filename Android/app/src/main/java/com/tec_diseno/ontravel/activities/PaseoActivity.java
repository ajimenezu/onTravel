package com.tec_diseno.ontravel.activities;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.NestedScrollView;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.format.DateUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.tec_diseno.ontravel.OnTravelApplication;
import com.tec_diseno.ontravel.R;
import com.tec_diseno.ontravel.entities.BaseResult;
import com.tec_diseno.ontravel.entities.Categoria;
import com.tec_diseno.ontravel.entities.Paseo;
import com.tec_diseno.ontravel.managers.PaseoManager;
import com.tec_diseno.ontravel.responses.PaseoResponse;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PaseoActivity extends AppCompatActivity implements Callback<PaseoResponse>, OnMapReadyCallback {

    Context context;
    Paseo paseo;
    private GoogleMap mMap;

    @BindView(R.id.paseo_app_bar)
    AppBarLayout barLayout;
    @BindView(R.id.img_paseo_sin_imagen)
    ImageView imgSinImagen;
    @BindView(R.id.layoutContenedoInfoPaseo)
    LinearLayout layoutInfo;
    @BindView(R.id.scrollViewPaseo)
    NestedScrollView scrollViewPaseo;


    @BindView(R.id.pgbPaseo)
    ProgressBar pgbPaseo;
    @BindView(R.id.txtNombrePaseo)
    TextView txtNombrePaseo;
    @BindView(R.id.txtCategoriaPaseo)
    TextView txtCategoriaPaseo;
    @BindView(R.id.txtFechaPaseo)
    TextView txtFechaPaseo;
    @BindView(R.id.txtDescripcionPaseo)
    TextView txtDescripcionPaseo;
    @BindView(R.id.txtTelefonoContactoPaseo)
    TextView txtTelefonoContactoPaseo;
    @BindView(R.id.txtEmailContactoPaseo)
    TextView txtEmailContactoPaseo;
    @BindView(R.id.txtPrecioPaseo)
    TextView txtPrecioPaseo;
    @BindView(R.id.txtTotalCostoCompra)
    TextView txtTotalCostoCompra;
    @BindView(R.id.txtCupo)
    EditText txtCupo;


    @BindView(R.id.txtCantPaseosCompra)
    EditText txtCantPaseosCompra;

    @BindView(R.id.mapViewPaseo)
    MapView mapViewPaseo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_paseo);
        context = this;

        ButterKnife.bind(this);

        layoutInfo.setVisibility(View.GONE);

        try{
            mapViewPaseo.onCreate(savedInstanceState);
            mapViewPaseo.getMapAsync(this);
        }
        catch (Exception e)
        {}

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(OnTravelApplication.paseo.getName());
        getSupportActionBar().setSubtitle(OnTravelApplication.paseo.getLocalizacion());

        CargarImagen();
        CargarPaseo();

        this.txtCantPaseosCompra.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                try {
                    int lCantidad = 0;

                    if (s.length() > 0) {
                        lCantidad = Integer.parseInt(s.toString());
                    }

                    double lMontoTotal = (Double.parseDouble(paseo.getCosto().equals("") ? "0" : paseo.getCosto()));
                    lMontoTotal = (lMontoTotal * lCantidad);
                    lMontoTotal = Math.round(lMontoTotal*100);
                    lMontoTotal = lMontoTotal / 100;

                     //lMontoTotal = ((int) ((lCantidad * (Double.parseDouble(paseo.getCosto().equals("") ? "0" : paseo.getCosto()))) * 100)) / 100;

                    txtTotalCostoCompra.setText("Total: $" + lMontoTotal);
                }
                catch (Exception e){
                    txtTotalCostoCompra.setText("Total: $0" );
                }
            }

        });

 //this is important
    }

    @Override
    protected void onResume() {
        super.onResume();
        mapViewPaseo.onResume();
    }

    @Override
    protected void onStart() {
        super.onStart();
        mapViewPaseo.onStart();
    }

    @Override
    protected void onStop() {
        super.onStop();
        mapViewPaseo.onStop();
    }
    @Override
    protected void onPause() {
        mapViewPaseo.onPause();
        super.onPause();
    }
    @Override
    protected void onDestroy() {
        mapViewPaseo.onDestroy();
        super.onDestroy();
    }
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapViewPaseo.onLowMemory();
    }

    private void CargarPaseo()
    {
        layoutInfo.setVisibility(View.GONE);
        pgbPaseo.setVisibility(View.VISIBLE);
        PaseoManager.getPaseo(OnTravelApplication.paseo.getId(),this);
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

    @Override
    public void onResponse(Call<PaseoResponse> call, Response<PaseoResponse> response) {
        if(response.isSuccessful())
        {
            this.paseo = response.body().getPaseo();
            this.pgbPaseo.setVisibility(View.GONE);
            this.layoutInfo.setVisibility(View.VISIBLE);
            this.txtCantPaseosCompra.clearFocus();
            this.txtNombrePaseo.requestFocus();
            this.txtNombrePaseo.setText(paseo.getName());

            for (Categoria categoria: OnTravelApplication.categorias) {
                if (categoria.getId() == paseo.getCategoria_id())
                {
                    this.txtCategoriaPaseo.setText(categoria.getName());
                    break;
                }
            }

            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            try{
                Date lFecha = dateFormat.parse(paseo.getFecha());
                dateFormat = new SimpleDateFormat("EEEE dd MMMM yyyy ");
                this.txtFechaPaseo.setText(dateFormat.format(lFecha));
            }
            catch (ParseException e) {
                this.txtFechaPaseo.setText(paseo.getFecha());
            }
            this.txtDescripcionPaseo.setText(paseo.getDescripcion());
            this.txtTelefonoContactoPaseo.setText(paseo.getTelefono());
            this.txtEmailContactoPaseo.setText(paseo.getWebsite());
            this.txtPrecioPaseo.setText("$" + paseo.getCosto());
            this.txtTotalCostoCompra.setText("Total: $" + paseo.getCosto());
            this.txtCupo.setText(paseo.getCupo()+"");

            this.scrollViewPaseo.scrollTo(0,0);

            EstablecerPuntoGPSPaseo();
        }
        else
            Snackbar.make(findViewById(R.id.splash_content), "Problemas cargando la información",
                    Snackbar.LENGTH_LONG).setAction("REINTENTAR", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    CargarPaseo();
                }
            }).show();
    }

    @Override
    public void onFailure(Call<PaseoResponse> call, Throwable t) {
        Snackbar.make(findViewById(R.id.coordinatorLayoutPaseo), "Problemas cargando la información",
                Snackbar.LENGTH_LONG).setAction("REINTENTAR", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CargarPaseo();
            }
        }).show();
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        EstablecerPuntoGPSPaseo();
    }

    private void EstablecerPuntoGPSPaseo()
    {
        if(mMap != null && paseo != null) {

            if(!paseo.getLatitud().equals("") && !paseo.getLongitud().equals(""))
            {
                LatLng ubicacionPaseo = new LatLng( Double.parseDouble(paseo.getLatitud()),
                        Double.parseDouble(paseo.getLongitud()));

                Marker marker = mMap.addMarker(new MarkerOptions().position(ubicacionPaseo).title(paseo.getLocalizacion()));
                marker.showInfoWindow();
                mMap.getUiSettings().setMyLocationButtonEnabled(false);
                mMap.getUiSettings().setAllGesturesEnabled(true);
                mMap.setIndoorEnabled(true);
                mMap.getUiSettings().setIndoorLevelPickerEnabled(true);
                mMap.getUiSettings().setMapToolbarEnabled(true);
                mMap.getUiSettings().setCompassEnabled(true);
                mMap.getUiSettings().setZoomControlsEnabled(true);
                MapsInitializer.initialize(context);
                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(ubicacionPaseo,16));
            }
        }

    }

    @OnClick(R.id.btnComprar)
    public void Comprar(View view) {
        if(OnTravelApplication.account != null)
        {
            if(!txtCantPaseosCompra.getText().toString().equals("")) {
              int lCantidadDeseada = Integer.parseInt(txtCantPaseosCompra.getText().toString());
              if(!txtCupo.getText().toString().equals(""))
              {
                int lCantidadCupoDisponible = Integer.parseInt(txtCupo.getText().toString());
                if(lCantidadCupoDisponible < lCantidadDeseada)
                {
                    Snackbar.make(findViewById(R.id.coordinatorLayoutPaseo),
                            "La cantidad deseada sobrepasa al cupo disponible",
                            Snackbar.LENGTH_LONG).show();
                    return;
                }
              }

              pgbPaseo.setVisibility(View.VISIBLE);
              layoutInfo.setVisibility(View.GONE);

                PaseoManager.postComparPaseo(OnTravelApplication.account.getEmail(), paseo.getId(), lCantidadDeseada,
                        new Callback<BaseResult>() {
                    @Override
                    public void onResponse(Call<BaseResult> call, Response<BaseResult> response) {

                        if(response.isSuccessful())
                        {
                            Snackbar.make(findViewById(R.id.coordinatorLayoutPaseo),
                                    "Compra realiza de forma exitosa.",
                                    Snackbar.LENGTH_LONG).show();
                        }
                        else
                        {
                            Snackbar.make(findViewById(R.id.coordinatorLayoutPaseo),
                                    response.body().getValue().equals("") ? "Error procesando la compra." : response.body().getValue(),
                                    Snackbar.LENGTH_LONG).show();
                        }

                        CargarPaseo();

                    }

                    @Override
                    public void onFailure(Call<BaseResult> call, Throwable t) {

                        Snackbar.make(findViewById(R.id.coordinatorLayoutPaseo),
                                "Error procesando la compra. Intente de nuevo",
                                Snackbar.LENGTH_LONG).show();

                        CargarPaseo();
                    }
                });

            }
            else {
                Snackbar.make(findViewById(R.id.coordinatorLayoutPaseo),
                        "Debe indicar la cantidad que desea comprar",
                        Snackbar.LENGTH_LONG).show();
                txtCantPaseosCompra.selectAll();
                txtCantPaseosCompra.requestFocus();
            }
        }
        else
        {
            Snackbar.make(findViewById(R.id.coordinatorLayoutPaseo),
                    "Debe hacer login para comprar",
                    Snackbar.LENGTH_LONG).show();
        }
    }
}
