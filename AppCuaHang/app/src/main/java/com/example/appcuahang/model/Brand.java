package com.example.appcuahang.model;

public class Brand {
    private String _id;
    private String tenHang;
    private String maCuaHang;

    public Brand() {
    }

    public Brand(String tenHang, String maCuaHang) {
        this.tenHang = tenHang;
        this.maCuaHang = maCuaHang;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTenHang() {
        return tenHang;
    }

    public void setTenHang(String tenHang) {
        this.tenHang = tenHang;
    }

    public String getMaCuaHang() {
        return maCuaHang;
    }

    public void setMaCuaHang(String maCuaHang) {
        this.maCuaHang = maCuaHang;
    }
}
