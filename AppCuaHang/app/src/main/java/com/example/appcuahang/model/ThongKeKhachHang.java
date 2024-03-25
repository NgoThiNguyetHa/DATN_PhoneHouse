package com.example.appcuahang.model;

import com.google.gson.annotations.SerializedName;

public class ThongKeKhachHang {

    @SerializedName("total")
    private int soLuongHoaDon;

    @SerializedName("uniqueMaKhachHang")
    private int soLuongKhachHang;

    public int getSoLuongHoaDon() {
        return soLuongHoaDon;
    }

    public void setSoLuongHoaDon(int soLuongHoaDon) {
        this.soLuongHoaDon = soLuongHoaDon;
    }

    public int getSoLuongKhachHang() {
        return soLuongKhachHang;
    }

    public void setSoLuongKhachHang(int soLuongKhachHang) {
        this.soLuongKhachHang = soLuongKhachHang;
    }
}
