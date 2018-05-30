package com.tec_diseno.ontravel.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tec_diseno.ontravel.OnTravelApplication;
import com.tec_diseno.ontravel.R;
import com.tec_diseno.ontravel.activities.PaseoActivity;
import com.tec_diseno.ontravel.entities.Paseo;
import com.tec_diseno.ontravel.managers.PaseoManager;
import com.tec_diseno.ontravel.responses.PaseoResponse;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.widget.ImageView;

public class PaseoAdapter extends RecyclerView.Adapter<PaseoAdapter.ViewHolder> {

    ArrayList<Paseo> paseos;
    Context context;
    Activity activity;

    public PaseoAdapter(ArrayList<Paseo> paseos, Context context, Activity activity) {
        this.paseos = paseos;
        this.context = context;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_tavel, parent, false);

        ViewHolder vh = new ViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
       // final ViewHolder hol = holder;
        final Paseo paseo = paseos.get(position);
       holder.nombre.setText( paseo.getName());
       holder.ubicacion.setText(paseo.getLocalizacion());
       holder.precio.setText("$" + paseo.getCosto());


        PaseoManager.getImagenPaseo(paseos.get(position).getId(), new Callback<PaseoResponse>() {
            @Override
            public void onResponse(Call<PaseoResponse> call, Response<PaseoResponse> response) {
                if(response.isSuccessful())
                {
                    String lImagen = response.body().getImagen();
                    if(lImagen != null) {

                        lImagen = lImagen.substring(lImagen.indexOf(",") > 0 ? lImagen.indexOf(",") + 1 : 0,lImagen.length());
                        byte[] decodedString = Base64.decode(lImagen, Base64.DEFAULT);
                        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
                        Drawable drawable = new BitmapDrawable(context.getResources(), decodedByte);

                        paseo.setImagen(drawable);
                        holder.layout.setBackground(paseo.getImagen());

                    }
                    else {
                        holder.sinImagen.setVisibility(View.VISIBLE);
                    }
                }
                else
                {
                    holder.sinImagen.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<PaseoResponse> call, Throwable t) {
                holder.sinImagen.setVisibility(View.VISIBLE);
            }
        });

        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    OnTravelApplication.paseo = paseos.get(position);
                    Intent i = new Intent(context, PaseoActivity.class);
                    //i.putExtra("evento",events.get(position));
                    activity.startActivity(i);
                } catch (Exception e) {
                    //Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return paseos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.paseo_nombre)
        TextView nombre;
        @BindView(R.id.paseo_ubicacion)
        TextView ubicacion;
        @BindView(R.id.paseo_precio)
        TextView precio;
        @BindView(R.id.layoutItemPaseo)
        RelativeLayout layout;
        @BindView(R.id.imgNoPicture)
        ImageView sinImagen;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
