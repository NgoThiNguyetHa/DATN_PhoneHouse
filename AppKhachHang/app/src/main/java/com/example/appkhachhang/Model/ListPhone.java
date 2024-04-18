package com.example.appkhachhang.Model;

import java.util.List;

public class ListPhone {
    private String _id;
    private int soLuong;
    private int giaTien;
    private Phone maDienThoai;
    private Mau maMau;
    private DungLuong maDungLuong;
    private Ram maRam;
    private String hinhAnh;

    public ListPhone(int soLuong, int giaTien, Phone maDienThoai, Mau maMau, DungLuong maDungLuong, Ram maRam, String hinhAnh ) {
        this.soLuong = soLuong;
        this.giaTien = giaTien;
        this.maDienThoai = maDienThoai;
        this.maMau = maMau;
        this.maDungLuong = maDungLuong;
        this.maRam = maRam;
        this.hinhAnh = hinhAnh;
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

    public Phone getMaDienThoai() {
        return maDienThoai;
    }

    public void setMaDienThoai(Phone maDienThoai) {
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
