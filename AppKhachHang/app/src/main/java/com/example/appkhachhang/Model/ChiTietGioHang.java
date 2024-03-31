package com.example.appkhachhang.Model;

public class ChiTietGioHang {
    private String _id;
    private String soLuong;
    private String giaTien;
    private ChiTietDienThoai maChiTietDienThoai;
    private GioHang maGioHang;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
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
}
