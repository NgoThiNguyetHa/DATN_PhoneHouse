package com.example.appcuahang.model;

public class Top10sanPham {
    private String tenDienThoai;
    private int giaTien;
    private int soLuong;

    public Top10sanPham(String tenDienThoai, int giaTien, int soLuong) {
        this.tenDienThoai = tenDienThoai;
        this.giaTien = giaTien;
        this.soLuong = soLuong;
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

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
