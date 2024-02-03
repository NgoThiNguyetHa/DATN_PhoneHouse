package com.example.appkhachhang.Model;

public class SanPham {
    String _id;
    String tenDienThoai;
    Number giaTien;
    Number soLuong;
    String anh;
    Mau maMau;
    Ram maRam;
    DungLuong maDungLuong;
    HangSanXuat maHangSX;
    UuDai maUuDai;
    ChiTietDienThoai maChiTiet;

    public SanPham() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTenDienThoai() {
        return tenDienThoai;
    }

    public void setTenDienThoai(String tenDienThoai) {
        this.tenDienThoai = tenDienThoai;
    }

    public Number getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(Number giaTien) {
        this.giaTien = giaTien;
    }

    public Number getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Number soLuong) {
        this.soLuong = soLuong;
    }

    public String getAnh() {
        return anh;
    }

    public void setAnh(String anh) {
        this.anh = anh;
    }

    public Mau getMaMau() {
        return maMau;
    }

    public void setMaMau(Mau maMau) {
        this.maMau = maMau;
    }

    public Ram getMaRam() {
        return maRam;
    }

    public void setMaRam(Ram maRam) {
        this.maRam = maRam;
    }

    public DungLuong getMaDungLuong() {
        return maDungLuong;
    }

    public void setMaDungLuong(DungLuong maDungLuong) {
        this.maDungLuong = maDungLuong;
    }

    public HangSanXuat getMaHangSX() {
        return maHangSX;
    }

    public void setMaHangSX(HangSanXuat maHangSX) {
        this.maHangSX = maHangSX;
    }

    public UuDai getMaUuDai() {
        return maUuDai;
    }

    public void setMaUuDai(UuDai maUuDai) {
        this.maUuDai = maUuDai;
    }

    public ChiTietDienThoai getMaChiTiet() {
        return maChiTiet;
    }

    public void setMaChiTiet(ChiTietDienThoai maChiTiet) {
        this.maChiTiet = maChiTiet;
    }
}
