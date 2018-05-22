package com.tec_diseno.ontravel.activities;

import android.app.Activity;
import android.app.KeyguardManager;
import android.content.Context;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.SubMenu;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;

import com.tec_diseno.ontravel.OnTravelApplication;
import com.tec_diseno.ontravel.R;
import com.tec_diseno.ontravel.adapters.ViajeAdapter;
import com.tec_diseno.ontravel.entities.Categoria;
import com.tec_diseno.ontravel.entities.Viaje;
import com.tec_diseno.ontravel.managers.CategoriaManager;
import com.tec_diseno.ontravel.responses.CategoriaResponse;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Random;

import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import butterknife.OnClick;
import butterknife.BindView;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    @BindView(R.id.swipe_main)
    SwipeRefreshLayout swipe;
    @BindView(R.id.recycler_main)
    RecyclerView recyclerView;

    SubMenu subMenuCategorias;
    Context context;
    Activity activity;
    RecyclerView.Adapter adapter;
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
                refreshContent(null);
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
        OnTravelApplication.viajes = new ArrayList<>();

        Random r = new Random();
        int lIndex = r.nextInt(14) + 1;
        for (int i = 1; i <= lIndex; i++)
        {
            String lPrecio = String.format("%d",(long)(i*5));

            OnTravelApplication.viajes.add(new Viaje("Viaje num. " + i,
                    "Ubicaciòn del viaje " + i, lPrecio));
        }


       adapter = new ViajeAdapter(OnTravelApplication.viajes,context,activity);
        recyclerView.setAdapter(adapter);
        swipe.setRefreshing(false);

    }
}
