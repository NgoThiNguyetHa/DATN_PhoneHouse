package com.example.appkhachhang.Model;

public class SanPhamHot {
    ChiTietDienThoai _id;
    Number soLuong;

    public SanPhamHot() {
    }

    public ChiTietDienThoai get_id() {
        return _id;
    }

    public void set_id(ChiTietDienThoai _id) {
        this._id = _id;
    }

    public Number getSoLuong() {
        return soLuong;
    }

    public void setSoLuong(Number soLuong) {
        this.soLuong = soLuong;
    }
}
