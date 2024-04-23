package com.example.appkhachhang.Model;

import java.io.Serializable;

public class ChiTietGioHang implements Serializable {
    private String _id;
    private int soLuong;
    private int giaTien;
    private ChiTietDienThoai maChiTietDienThoai;
    private GioHang maGioHang;

    public ChiTietGioHang() {
    }

    public ChiTietGioHang(ChiTietDienThoai maChiTietDienThoai, int soLuong , int giaTien) {
        this.maChiTietDienThoai = maChiTietDienThoai;
        this.soLuong = soLuong;
        this.giaTien = giaTien;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public int getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(int giaTien) {
        this.giaTien = giaTien;
    }

    public ChiTietDienThoai getMaChiTietDienThoai() {
        return maChiTietDienThoai;
    }

    public void setMaChiTietDienThoai(ChiTietDienThoai maChiTietDienThoai) {
        this.maChiTietDienThoai = maChiTietDienThoai;
    }

    public GioHang getMaGioHang() {
        return maGioHang;
    }

    public void setMaGioHang(GioHang maGioHang) {
        this.maGioHang = maGioHang;
    }

    @Override
    public String toString() {
        return "ChiTietGioHang{" +
                "_id='" + _id + '\'' +
                ", soLuong=" + soLuong +
                ", giaTien=" + giaTien +
                ", maChiTietDienThoai=" + maChiTietDienThoai +
                ", maGioHang=" + maGioHang +
                '}';
    }
}
