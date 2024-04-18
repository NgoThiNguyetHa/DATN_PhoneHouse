package com.example.appkhachhang.Model;

public class DiaChiNhanHang {
    String _id;
    User maKhachHang;
    String sdt;
    String tenNguoiNhan;
    String diaChi;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public User getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(User maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getTenNguoiNhan() {
        return tenNguoiNhan;
    }

    public void setTenNguoiNhan(String tenNguoiNhan) {
        this.tenNguoiNhan = tenNguoiNhan;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }
}
