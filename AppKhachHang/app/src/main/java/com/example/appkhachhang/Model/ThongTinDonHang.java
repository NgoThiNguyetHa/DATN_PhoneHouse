package com.example.appkhachhang.Model;

public class ThongTinDonHang {
    private String _id;
    private int soLuong;
    private String giaTien;
    private HoaDon maHoaDon;
    private ChiTietDienThoai maChiTietDienThoai;

    public ThongTinDonHang(int soLuong, String giaTien, HoaDon maHoaDon, ChiTietDienThoai maChiTietDienThoai) {
        soLuong = soLuong;
        this.giaTien = giaTien;
        this.maHoaDon = maHoaDon;
        this.maChiTietDienThoai = maChiTietDienThoai;
    }

    public ThongTinDonHang(HoaDon maHoaDon) {
        this.maHoaDon = maHoaDon;
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
        soLuong = soLuong;
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
