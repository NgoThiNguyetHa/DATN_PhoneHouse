package com.example.appkhachhang.Model;

public class ChiTietDienThoai{
    String _id;
    Number soLuong;
    Number giaTien;
    SanPham maDienThoai;
    Mau maMau;
    DungLuong maDungLuong;
    Ram maRam;


    public ChiTietDienThoai() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public Number getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Number soLuong) {
        this.soLuong = soLuong;
    }

    public Number getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(Number giaTien) {
        this.giaTien = giaTien;
    }

    public SanPham getMaDienThoai() {
        return maDienThoai;
    }

    public void setMaDienThoai(SanPham maDienThoai) {
        this.maDienThoai = maDienThoai;
    }

    public Mau getMaMau() {
        return maMau;
    }

    public void setMaMau(Mau maMau) {
        this.maMau = maMau;
    }

    public DungLuong getMaDungLuong() {
        return maDungLuong;
    }

    public void setMaDungLuong(DungLuong maDungLuong) {
        this.maDungLuong = maDungLuong;
    }

    public Ram getMaRam() {
        return maRam;
    }

    public void setMaRam(Ram maRam) {
        this.maRam = maRam;
    }
}

