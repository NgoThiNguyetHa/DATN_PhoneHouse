package com.example.appkhachhang.Model;

public class UuDai {
    String _id;
    String giamGia;

    String thoiGian;
    String trangThai;

    public UuDai() {
    }

    public UuDai(String _id, String giamGia, String thoiGian, String trangThai) {
        this._id = _id;
        this.giamGia = giamGia;
        this.thoiGian = thoiGian;
        this.trangThai = trangThai;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getGiamGia() {
        return giamGia;
    }

    public void setGiamGia(String giamGia) {
        this.giamGia = giamGia;
    }

    public String getThoiGian() {
        return thoiGian;
    }

    public void setThoiGian(String thoiGian) {
        this.thoiGian = thoiGian;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
}
