package com.example.appkhachhang.Model;

import java.io.Serializable;

public class DanhGia implements Serializable {
    private String _id;
    private String noiDung;
    private String hinhAnh;
    private int diemDanhGia;
    private String ngayTao;
    private User idKhachHang;
    private ChiTietDienThoai idChiTietDienThoai;

    public DanhGia(String noiDung, String hinhAnh, int diemDanhGia, String ngayTao, User idKhachHang, ChiTietDienThoai idChiTietDienThoai) {
        this.noiDung = noiDung;
        this.hinhAnh = hinhAnh;
        this.diemDanhGia = diemDanhGia;
        this.ngayTao = ngayTao;
        this.idKhachHang = idKhachHang;
        this.idChiTietDienThoai = idChiTietDienThoai;
    }

    public DanhGia(String _id) {
        this._id = _id;
    }

    public String get_id() {
        return _id;
    }


    public void set_id(String _id) {
        this._id = _id;
    }

    public DanhGia() {
    }

    public String getNoiDung() {
        return noiDung;
    }

    public void setNoiDung(String noiDung) {
        this.noiDung = noiDung;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public int getDiemDanhGia() {
        return diemDanhGia;
    }

    public void setDiemDanhGia(int diemDanhGia) {
        this.diemDanhGia = diemDanhGia;
    }

    public String getNgayTao() {
        return ngayTao;
    }

    public void setNgayTao(String ngayTao) {
        this.ngayTao = ngayTao;
    }

    public User getIdKhachHang() {
        return idKhachHang;
    }

    public void setIdKhachHang(User idKhachHang) {
        this.idKhachHang = idKhachHang;
    }

    public ChiTietDienThoai getIdChiTietDienThoai() {
        return idChiTietDienThoai;
    }

    public void setIdChiTietDienThoai(ChiTietDienThoai idChiTietDienThoai) {
        this.idChiTietDienThoai = idChiTietDienThoai;
    }
}
