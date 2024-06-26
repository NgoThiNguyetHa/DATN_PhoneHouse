package com.example.appkhachhang.Model;

import java.io.Serializable;

public class GioHang implements Serializable {
    private String _id;
    private String soLuong;
    private String tongTien;
    private User maKhachHang;

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

    public String getTongTien() {
        return tongTien;
    }

    public void setTongTien(String tongTien) {
        this.tongTien = tongTien;
    }

    public User getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(User maKhachHang) {
        this.maKhachHang = maKhachHang;
    }
}
