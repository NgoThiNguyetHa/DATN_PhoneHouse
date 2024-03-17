package com.example.appkhachhang.Model;

public class ChiTietGioHang {
    private int _id;
    private String soLuong;
    private String giaTien;
    private SanPham maDienThoai;
    private String maGioHang;

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public String getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(String soLuong) {
        this.soLuong = soLuong;
    }

    public String getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(String giaTien) {
        this.giaTien = giaTien;
    }

    public SanPham getMaDienThoai() {
        return maDienThoai;
    }

    public void setMaDienThoai(SanPham maDienThoai) {
        this.maDienThoai = maDienThoai;
    }

    public String getMaGioHang() {
        return maGioHang;
    }

    public void setMaGioHang(String maGioHang) {
        this.maGioHang = maGioHang;
    }
}
