package com.tec_diseno.ontravel.responses;

import android.graphics.drawable.Drawable;
import android.media.Image;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tec_diseno.ontravel.entities.Paseo;

import java.util.ArrayList;
import java.util.stream.Stream;

public class PaseoResponse {

    @SerializedName("paseos")
    @Expose
    ArrayList<Paseo> listPaseos = null;

    @SerializedName("imagen")
    @Expose
    String imagen;

    @SerializedName("paseo")
    @Expose
    Paseo paseo = null;

    public ArrayList<Paseo> getListPaseos() {
        return listPaseos;
    }

    public void setListPaseos(ArrayList<Paseo> listPaseos) {
        this.listPaseos = listPaseos;
    }

    public String getImagen() {
        return imagen;
    }

    public void setImagen(String imagen) {
        this.imagen = imagen;
    }

    public Paseo getPaseo() {
        return paseo;
    }

    public void setPaseo(Paseo paseo) {
        this.paseo = paseo;
    }
}
