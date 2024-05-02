package com.example.appkhachhang.Model;


import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SanPhamHot implements Serializable {
    ChiTietDienThoai _id;
    List<DanhGia> danhGias;
    int soLuong;

    public SanPhamHot() {
    }

    public ChiTietDienThoai get_id() {
        return _id;
    }

    public void set_id(ChiTietDienThoai _id) {
        this._id = _id;
    }

    public List<DanhGia> getDanhGia() {
        return danhGias;
    }

    public void setDanhGia(List<DanhGia> danhGias) {
        this.danhGias = danhGias;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }

    @Override
    public String toString() {
        return "SanPhamHot{" +
                "_id=" + _id +
                ", danhGias=" + danhGias +
                ", soLuong=" + soLuong +
                '}';
    }
}
