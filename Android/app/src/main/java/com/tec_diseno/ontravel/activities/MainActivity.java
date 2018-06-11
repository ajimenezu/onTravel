package com.tec_diseno.ontravel.activities;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.tec_diseno.ontravel.OnTravelApplication;
import com.tec_diseno.ontravel.R;
import com.tec_diseno.ontravel.adapters.PaseoAdapter;
import com.tec_diseno.ontravel.entities.Categoria;
import com.tec_diseno.ontravel.entities.Paseo;
import com.tec_diseno.ontravel.managers.PaseoManager;
import com.tec_diseno.ontravel.responses.PaseoResponse;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Random;

import butterknife.ButterKnife;

import butterknife.BindView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, Callback<PaseoResponse> {

    @BindView(R.id.swipe_main)
    SwipeRefreshLayout swipe;
    @BindView(R.id.recycler_main)
    RecyclerView recyclerView;

    TextView txtNombreUsuario;
    TextView txtCorreoUsuario;
    LinearLayout fotoUsuario;
    Menu menuNavegacion;
    DrawerLayout drawer;

    SubMenu subMenuCategorias;
    Context context;
    Activity activity;
    RecyclerView.Adapter adapter;
    Categoria categoriaSeleccionada;
    private LinearLayoutManager linearLayoutManager;

    GoogleSignInClient mGoogleSignInClient;
    private static final String TAG = "SignInActivity";
    private static final int RC_SIGN_IN = 9001;

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

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        View headerView = navigationView.getHeaderView(0);

        this.txtCorreoUsuario = (TextView) headerView.findViewById(R.id.txtCorreoUsuario);
        this.txtNombreUsuario = (TextView) headerView.findViewById(R.id.txtNombreUsuario);
        this.fotoUsuario = (LinearLayout) headerView.findViewById(R.id.fotoUsuario);

        Collections.sort(OnTravelApplication.categorias, new Comparator<Categoria>() {
            @Override
            public int compare(Categoria o1, Categoria o2) {
                return o1.getName().compareToIgnoreCase(o2.getName());
            }
        });

        menuNavegacion = navigationView.getMenu();
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



        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        // Build a GoogleSignInClient with the options specified by gso.
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


    }

    @Override
    protected void onStart() {
        super.onStart();
        OnTravelApplication.account = GoogleSignIn.getLastSignedInAccount(this);
        updateUI(OnTravelApplication.account);
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
           if(id == R.id.actionLogin)
           {
               signIn();
           }
           else if (id == R.id.actionLogOut) {
               signOut();
           }

        }

        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void refreshContent(Categoria categoria) {
        swipe.setRefreshing(true);
        recyclerView.setAdapter(null);
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

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent,RC_SIGN_IN);
    }

    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // [START_EXCLUDE]
                        OnTravelApplication.account = null;
                        updateUI(null);
                        // [END_EXCLUDE]
                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // Result returned from launching the Intent from GoogleSignInClient.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            // The Task returned from this call is always completed, no need to attach
            // a listener.
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
        else
            OnTravelApplication.account = null;

    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try {
            OnTravelApplication.account = completedTask.getResult(ApiException.class);
            updateUI(OnTravelApplication.account);
        } catch (ApiException e) {
            // The ApiException status code indicates the detailed failure reason.
            // Please refer to the GoogleSignInStatusCodes class reference for more information.
            OnTravelApplication.account = null;
            updateUI(null);

        }
    }

    private void updateUI(@Nullable GoogleSignInAccount account) {
        OnTravelApplication.account = account;
        menuNavegacion.findItem(R.id.actionLogin).setVisible(account == null);
        menuNavegacion.findItem(R.id.actionLogOut).setVisible(account != null);

        this.txtCorreoUsuario.setText(account != null ? account.getEmail() : "");
        this.txtNombreUsuario.setText(account != null ? account.getDisplayName(): "");
        if(account != null) {
            new DownloadImageTask(this.fotoUsuario)
                    .execute(account.getPhotoUrl().toString());
        }
        else
            this.fotoUsuario.setBackground(getResources().getDrawable( R.drawable.ic_account_circle_black_24dp ));
    }

    private class DownloadImageTask extends AsyncTask<String, Void, Drawable> {
        LinearLayout bmImage;

        public DownloadImageTask(LinearLayout bmImage) {
            this.bmImage = bmImage;
        }

        protected Drawable doInBackground(String... urls) {
            String urldisplay = urls[0];
            Bitmap mIcon11 = null;
            Drawable drawable = null;
            try {
                InputStream in = new java.net.URL(urldisplay).openStream();
                mIcon11 = BitmapFactory.decodeStream(in);
                drawable = new BitmapDrawable(context.getResources(), mIcon11);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
                e.printStackTrace();
            }
            return drawable;
        }

        protected void onPostExecute(Drawable result) {
            bmImage.setBackground(result);
        }
    }
}
