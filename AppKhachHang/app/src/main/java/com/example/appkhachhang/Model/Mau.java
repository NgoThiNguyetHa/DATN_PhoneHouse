package com.example.appkhachhang.Model;

public class Mau {
    String _id;
    String tenMau;
    Number giaTien;

    public Mau() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTenMau() {
        return tenMau;
    }

    public void setTenMau(String tenMau) {
        this.tenMau = tenMau;
    }

    public Number getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(Number giaTien) {
        this.giaTien = giaTien;
    }
}
