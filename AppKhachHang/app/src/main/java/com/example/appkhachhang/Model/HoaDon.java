package com.example.appkhachhang.Model;

public class HoaDon {
    private String _id;
    private String tongTien;
    private String ngayTao;
    private String trangThaiNhanHang;
    private String phuongThucThanhToan;
    private AddressDelivery maDiaChiNhanHang;
    private User maKhachHang;
    private Store maCuaHang;

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTongTien() {
        return tongTien;
    }

    public void setTongTien(String tongTien) {
        this.tongTien = tongTien;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public String getTrangThaiNhanHang() {
        return trangThaiNhanHang;
    }

    public void setTrangThaiNhanHang(String trangThaiNhanHang) {
        this.trangThaiNhanHang = trangThaiNhanHang;
    }

    public String getPhuongThucThanhToan() {
        return phuongThucThanhToan;
    }

    public void setPhuongThucThanhToan(String phuongThucThanhToan) {
        this.phuongThucThanhToan = phuongThucThanhToan;
    }

    public AddressDelivery getMaDiaChiNhanHang() {
        return maDiaChiNhanHang;
    }

    public void setMaDiaChiNhanHang(AddressDelivery maDiaChiNhanHang) {
        this.maDiaChiNhanHang = maDiaChiNhanHang;
    }

    public User getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(User maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public Store getMaCuaHang() {
        return maCuaHang;
    }

    public void setMaCuaHang(Store maCuaHang) {
        this.maCuaHang = maCuaHang;
    }
}
