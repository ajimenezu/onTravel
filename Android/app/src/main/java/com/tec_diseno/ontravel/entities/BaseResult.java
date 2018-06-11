package com.tec_diseno.ontravel.entities;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BaseResult {
    @SerializedName("status")
    @Expose
    private String value;

    public BaseResult(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }


}
