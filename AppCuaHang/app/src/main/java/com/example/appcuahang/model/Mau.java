package com.example.appcuahang.model;

public class Mau {
    private String tenMau;
    private int giaTien;
    private String _id;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Mau(String _id) {
        this._id = _id;
    }

    public Mau(String tenMau, String giaTien) {
    }


    public Mau(String tenMau, int giaTien) {
        this.tenMau = tenMau;
        this.giaTien = giaTien;
    }

    public String getTenMau() {
        return tenMau;
    }

    public void setTenMau(String tenMau) {
        this.tenMau = tenMau;
    }

    public int getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(int giaTien) {
        this.giaTien = giaTien;
    }
}
