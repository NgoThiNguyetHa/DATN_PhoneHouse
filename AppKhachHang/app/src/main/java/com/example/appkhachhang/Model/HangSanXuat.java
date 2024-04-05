package com.example.appkhachhang.Model;

import java.io.Serializable;

public class HangSanXuat implements Serializable {
    String tenHang;
    Object maCuaHang;

    String hinhAnh;

    public HangSanXuat() {
    }

    public HangSanXuat(String tenHang, Object maCuaHang, String hinhAnh) {
        this.tenHang = tenHang;
        this.maCuaHang = maCuaHang;
        this.hinhAnh = hinhAnh;
    }

    public String getTenHang() {
        return tenHang;
    }

    public void setTenHang(String tenHang) {
        this.tenHang = tenHang;
    }

    public Object getMaCuaHang() {
        return maCuaHang;
    }

    public void setMaCuaHang(Object maCuaHang) {
        this.maCuaHang = maCuaHang;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}
