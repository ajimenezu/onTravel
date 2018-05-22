package com.tec_diseno.ontravel.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tec_diseno.ontravel.entities.Categoria;

import java.util.ArrayList;
import java.util.List;

public class CategoriaResponse {

    @SerializedName("categorias")
    @Expose
    ArrayList<Categoria> listCategoria = null;

    public ArrayList<Categoria> getListCategoria() {
        return listCategoria;
    }

    public void setListCategoria(ArrayList<Categoria> listCategoria) {
        this.listCategoria = listCategoria;
    }
}
