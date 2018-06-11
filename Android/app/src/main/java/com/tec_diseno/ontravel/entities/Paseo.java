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

    @SerializedName("fecha")
    @Expose
    private String fecha;

    @SerializedName("localizacion")
    @Expose
    private String localizacion;

    @SerializedName("costo")
    @Expose
    private String costo;

    @SerializedName("imagen")
    @Expose
    private Drawable imagen;

    @SerializedName("latitud")
    @Expose
    private String latitud;

    @SerializedName("longitud")
    @Expose
    private String longitud;

    @SerializedName("telefono")
    @Expose
    private String telefono;

    @SerializedName("website")
    @Expose
    private String website;

    @SerializedName("descripcion")
    @Expose
    private String descripcion;

    @SerializedName("categoria_id")
    @Expose
    private int categoria_id;

    @SerializedName("cupo")
    @Expose
    private int cupo;

    public Paseo(int id, String nombre, String ubicacion, String precio) {
        this.id = id;
        this.name = nombre;
        this.localizacion = ubicacion;
        this.costo = precio;
    }

    public Paseo(int id, String name, String fecha, String localizacion,
                 String costo, Drawable imagen, String latitud, String longitud, String telefono,
                 String website, String descripcion, int categoria_id, int cupo) {
        this.id = id;
        this.name = name;
        this.fecha = fecha;
        this.localizacion = localizacion;
        this.costo = costo;
        this.imagen = imagen;
        this.latitud = latitud;
        this.longitud = longitud;
        this.telefono = telefono;
        this.website = website;
        this.descripcion = descripcion;
        this.categoria_id = categoria_id;
        this.cupo = cupo;
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

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getLatitud() {
        return latitud;
    }

    public void setLatitud(String latitud) {
        this.latitud = latitud;
    }

    public String getLongitud() {
        return longitud;
    }

    public void setLongitud(String longitud) {
        this.longitud = longitud;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getCategoria_id() {
        return categoria_id;
    }

    public void setCategoria_id(int categoria_id) {
        this.categoria_id = categoria_id;
    }

    public int getCupo() {
        return cupo;
    }

    public void setCupo(int cupo) {
        this.cupo = cupo;
    }
}
