package com.example.appkhachhang.Model;

import java.util.List;

public class SearchResponse {
    private List<Root> dienThoais;
    private List<Store> cuaHangs;

    public List<Root> getDienThoais() {
        return dienThoais;
    }

    public void setDienThoais(List<Root> dienThoais) {
        this.dienThoais = dienThoais;
    }

    public List<Store> getCuaHangs() {
        return cuaHangs;
    }

    public void setCuaHangs(List<Store> cuaHangs) {
        this.cuaHangs = cuaHangs;
    }

    public SearchResponse() {
    }
}
