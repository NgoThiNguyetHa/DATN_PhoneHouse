package com.example.appcuahang.model;

public class ChiTietHoaDon {
    private String _id;
    private DetailPhone maChiTietDienThoai;
    private HoaDon maHoaDon;
    private String giaTien;
    private Number soLuong;

    public ChiTietHoaDon() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public DetailPhone getMaChiTietDienThoai() {
        return maChiTietDienThoai;
    }

    public void setMaChiTietDienThoai(DetailPhone maChiTietDienThoai) {
        this.maChiTietDienThoai = maChiTietDienThoai;
    }

    public HoaDon getMaHoaDon() {
        return maHoaDon;
    }

    public void setMaHoaDon(HoaDon maHoaDon) {
        this.maHoaDon = maHoaDon;
    }

    public String getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(String giaTien) {
        this.giaTien = giaTien;
    }

    public Number getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Number soLuong) {
        this.soLuong = soLuong;
    }
}
