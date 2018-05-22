package com.tec_diseno.ontravel.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.tec_diseno.ontravel.R;
import com.tec_diseno.ontravel.entities.Viaje;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class ViajeAdapter extends RecyclerView.Adapter<ViajeAdapter.ViewHolder> {

    ArrayList<Viaje> viajes;
    Context context;
    Activity activity;

    public ViajeAdapter(ArrayList<Viaje> viajes, Context context, Activity activity) {
        this.viajes = viajes;
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
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
       holder.nombre.setText( viajes.get(position).getNombre());
       holder.ubicacion.setText(viajes.get(position).getUbicacion());
       holder.precio.setText("$" + viajes.get(position).getPrecio());
    }

    @Override
    public int getItemCount() {
        return viajes.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        @BindView(R.id.paseo_nombre)
        TextView nombre;
        @BindView(R.id.paseo_ubicacion)
        TextView ubicacion;
        @BindView(R.id.paseo_precio)
        TextView precio;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this,itemView);
        }
    }
}
