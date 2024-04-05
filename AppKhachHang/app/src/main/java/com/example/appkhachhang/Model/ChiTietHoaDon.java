package com.example.appkhachhang.Model;

import java.io.Serializable;

public class ChiTietHoaDon implements Serializable {
    private int soLuong;
    private String giaTien;
    private String _id;
    private HoaDon maHoaDon;
    private ChiTietDienThoai maChiTietDienThoai;

    public ChiTietHoaDon(int soLuong, String giaTien, HoaDon maHoaDon, ChiTietDienThoai maChiTietDienThoai) {
        this.soLuong = soLuong;
        this.giaTien = giaTien;
        this.maHoaDon = maHoaDon;
        this.maChiTietDienThoai = maChiTietDienThoai;
    }

    public ChiTietHoaDon() {
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    public String getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(String giaTien) {
        this.giaTien = giaTien;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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
