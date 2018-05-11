package com.tec_diseno.ontravel.responses;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.tec_diseno.ontravel.entities.Categoria;

import java.util.List;

public class CategoriaResponse {

    @SerializedName("categorias")
    @Expose
    List<Categoria> listCategoria = null;

    public List<Categoria> getListCategoria() {
        return listCategoria;
    }

    public void setListCategoria(List<Categoria> listCategoria) {
        this.listCategoria = listCategoria;
    }
}
