package com.tec_diseno.ontravel.entities;

import android.graphics.drawable.Drawable;
import android.media.Image;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Paseo {
    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("localizacion")
    @Expose
    private String localizacion;

    @SerializedName("costo")
    @Expose
    private String costo;

    @SerializedName("imagen")
    @Expose
    private Drawable imagen;


    public Paseo(int id, String nombre, String ubicacion, String precio) {
        this.id = id;
        this.name = nombre;
        this.localizacion = ubicacion;
        this.costo = precio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocalizacion() {
        return localizacion;
    }

    public void setLocalizacion(String localizacion) {
        this.localizacion = localizacion;
    }

    public String getCosto() {
        return costo;
    }

    public void setCosto(String costo) {
        this.costo = costo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Drawable getImagen() {
        return imagen;
    }

    public void setImagen(Drawable imagen) {
        this.imagen = imagen;
    }
}
