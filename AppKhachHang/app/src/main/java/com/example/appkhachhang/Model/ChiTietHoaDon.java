package com.example.appkhachhang.Model;

import java.io.Serializable;

public class ChiTietHoaDon implements Serializable {
    String _id;
    String soLuong;
    String giaTien;
    HoaDon maHoaDon;
    ChiTietDienThoai maChiTietDienThoai;

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

    public HoaDon getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(HoaDon maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public ChiTietDienThoai getMaChiTietDienThoai() {
        return maChiTietDienThoai;
    }

    public void setMaChiTietDienThoai(ChiTietDienThoai maChiTietDienThoai) {
        this.maChiTietDienThoai = maChiTietDienThoai;
    }
}
