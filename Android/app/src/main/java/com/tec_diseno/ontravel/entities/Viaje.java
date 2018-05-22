package com.tec_diseno.ontravel.entities;

public class Viaje {
    private String nombre;
    private String ubicacion;
    private String precio;

    public Viaje(String nombre, String ubicacion, String precio) {
        this.nombre = nombre;
        this.ubicacion = ubicacion;
        this.precio = precio;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }

    public String getPrecio() {
        return precio;
    }

    public void setPrecio(String precio) {
        this.precio = precio;
    }


}
