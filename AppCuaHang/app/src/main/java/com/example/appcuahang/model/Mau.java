package com.example.appcuahang.model;

public class Mau {
    private String tenMau;
    private String _id;

    public Mau() {
    }

    public Mau(String _id, String tenMau) {
        this._id = _id;
        this.tenMau = tenMau;
    }

    public String getTenMau() {
        return tenMau;
    }

    public void setTenMau(String tenMau) {
        this.tenMau = tenMau;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @Override
    public String toString() {
        return tenMau ;
    }
}
