package com.example.appkhachhang.Model;

import java.io.Serializable;

public class ChiTietDienThoai implements Serializable {
    String _id;
    int soLuong;
    int giaTien;
    SanPham maDienThoai;
    Mau maMau;
    DungLuong maDungLuong;
    Ram maRam;

    private String hinhAnh;

    public ChiTietDienThoai() {
    }

    public ChiTietDienThoai(String _id) {
        this._id = _id;
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

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }
}

