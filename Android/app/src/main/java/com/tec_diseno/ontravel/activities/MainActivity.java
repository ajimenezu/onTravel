package com.tec_diseno.ontravel.activities;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.SubMenu;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.tec_diseno.ontravel.OnTravelApplication;
import com.tec_diseno.ontravel.R;
import com.tec_diseno.ontravel.adapters.PaseoAdapter;
import com.tec_diseno.ontravel.entities.Categoria;
import com.tec_diseno.ontravel.entities.Paseo;
import com.tec_diseno.ontravel.managers.PaseoManager;
import com.tec_diseno.ontravel.responses.PaseoResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import butterknife.ButterKnife;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Callback<PaseoResponse> {

    @BindView(R.id.swipe_main)
    SwipeRefreshLayout swipe;
    @BindView(R.id.recycler_main)
    RecyclerView recyclerView;

    SubMenu subMenuCategorias;
    Context context;
    Activity activity;
    RecyclerView.Adapter adapter;
    Categoria categoriaSeleccionada;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        activity = this;
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView.setHasFixedSize(true);
        linearLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(linearLayoutManager);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        Collections.sort(OnTravelApplication.categorias, new Comparator<Categoria>() {
            @Override
            public int compare(Categoria o1, Categoria o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });

        Menu menuNavegacion = navigationView.getMenu();
        subMenuCategorias = menuNavegacion.addSubMenu("Categorías");

        int index = 0;
        for (Categoria categoria : OnTravelApplication.categorias) {
            //subMenuCategorias.add(categoria.getName());
            index++;
            MenuItem item = subMenuCategorias.add(categoria.getName()).setIcon(R.drawable.ic_send);
            categoria.setMenuItem(item);
        }

        navigationView.invalidate();

        swipe.setColorSchemeColors(ContextCompat.getColor(context, R.color.colorPrimaryDark),
                ContextCompat.getColor(context, R.color.colorAccent));
        swipe.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refreshContent(categoriaSeleccionada);
            }
        });


    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else
            finish();

    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        MenuItem itemCategoria = subMenuCategorias.findItem(id);

        if (itemCategoria != null) {
            for (Categoria categoria : OnTravelApplication.categorias) {
                if (categoria.getMenuItem() == item) {
                    this.categoriaSeleccionada = categoria;
                    getSupportActionBar().setTitle(categoria.getName());
                    refreshContent(categoria);
                    break;
                }
            }
        } else {


        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void refreshContent(Categoria categoria) {
        swipe.setRefreshing(true);

        if (categoria != null) {
            PaseoManager.getPaseosCategoria(categoria.getId(), this);
        }

    }

    @Override
    public void onResponse(Call<PaseoResponse> call, Response<PaseoResponse> response) {
      if(response.isSuccessful())
      {
          adapter = new PaseoAdapter(response.body().getListPaseos(), context, activity);
          recyclerView.setAdapter(adapter);
          swipe.setRefreshing(false);
      }
      else
      {
          Snackbar.make(findViewById(R.id.splash_content), "Problemas cargando los paseos de la categoría seleccionada",
                  Snackbar.LENGTH_LONG).show();
      }
    }

    @Override
    public void onFailure(Call<PaseoResponse> call, Throwable t) {
        Snackbar.make(findViewById(R.id.drawer_layout), "Problemas cargando los paseos de la categoría seleccionada",
                Snackbar.LENGTH_LONG).show();
    }
}
