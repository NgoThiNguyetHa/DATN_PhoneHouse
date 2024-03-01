package com.example.appkhachhang.Model;

public class HangSanXuat {
    String tenHang;
    Object maCuaHang;

    String hinhAnh;

    public HangSanXuat() {
    }

    public String getAnh() {
        return hinhAnh;
    }

    public void setAnh(String anh) {
        this.hinhAnh = anh;
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
}
