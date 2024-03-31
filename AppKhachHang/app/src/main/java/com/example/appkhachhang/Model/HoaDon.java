package com.example.appkhachhang.Model;

public class HoaDon {
    String _id;

    String tongTien;
    String ngayTao;
    String trangThaiNhanHang;
    String phuongThucThanhToan;
    DiaChiNhanHang maDiaChiNhanHang;
    User maKhachHang;
    CuaHang maCuaHang;

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

    public DiaChiNhanHang getMaDiaChiNhanHang() {
        return maDiaChiNhanHang;
    }

    public void setMaDiaChiNhanHang(DiaChiNhanHang maDiaChiNhanHang) {
        this.maDiaChiNhanHang = maDiaChiNhanHang;
    }

    public User getMaKhachHang() {
        return maKhachHang;
    }

    public void setMaKhachHang(User maKhachHang) {
        this.maKhachHang = maKhachHang;
    }

    public CuaHang getMaCuaHang() {
        return maCuaHang;
    }

    public void setMaCuaHang(CuaHang maCuaHang) {
        this.maCuaHang = maCuaHang;
    }
}
