package com.example.appcuahang.model;

public class Top10sanPham {
    private String tenDienThoai;
    private int giaTien;
    private String hinhAnh;
    private int soLuongBan;

    public Top10sanPham(String tenDienThoai, int giaTien, int soLuongBan) {
        this.tenDienThoai = tenDienThoai;
        this.giaTien = giaTien;
        this.soLuongBan = soLuongBan;
    }

    public String getTenDienThoai() {
        return tenDienThoai;
    }

    public void setTenDienThoai(String tenDienThoai) {
        this.tenDienThoai = tenDienThoai;
    }

    public int getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(int giaTien) {
        this.giaTien = giaTien;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public int getSoLuongBan() {
        return soLuongBan;
    }

    public void setSoLuongBan(int soLuongBan) {
        this.soLuongBan = soLuongBan;
    }
}
