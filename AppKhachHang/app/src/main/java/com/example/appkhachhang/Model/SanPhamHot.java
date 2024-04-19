package com.example.appkhachhang.Model;

import java.io.Serializable;

public class SanPhamHot implements Serializable {
    ChiTietDienThoai _id;
    int soLuong;

    public SanPhamHot() {
    }

    public ChiTietDienThoai get_id() {
        return _id;
    }

    public void set_id(ChiTietDienThoai _id) {
        this._id = _id;
    }

    public int getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(int soLuong) {
        this.soLuong = soLuong;
    }
}
