package com.example.appcuahang.model;

public class Brand {
    private String _id;
    private String tenHang;
    private String hinhAnh;

    public Brand() {
    }

    public Brand(String _id) {
        this._id = _id;
    }

    public Brand(String tenHang, String hinhAnh ) {
        this.tenHang = tenHang;
        this.hinhAnh = hinhAnh;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTenHang() {
        return tenHang;
    }

    public void setTenHang(String tenHang) {
        this.tenHang = tenHang;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    @Override
    public String toString() {
        return tenHang ;
    }
}
