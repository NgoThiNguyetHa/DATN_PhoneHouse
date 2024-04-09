package com.example.appcuahang.model;

public class Rating {
    private String _id;
    private String noiDung;
    private String hinhAnh;
    private int diemDanhGia;
    private String ngayTao;
    private Client idKhachHang;
    private DetailPhone idChiTietDienThoai;

    public Rating(String _id, String noiDung, String hinhAnh, int diemDanhGia, String ngayTao, Client idKhachHang, DetailPhone idChiTietDienThoai) {
        this._id = _id;
        this.noiDung = noiDung;
        this.hinhAnh = hinhAnh;
        this.diemDanhGia = diemDanhGia;
        this.ngayTao = ngayTao;
        this.idKhachHang = idKhachHang;
        this.idChiTietDienThoai = idChiTietDienThoai;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
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

    public Client getIdKhachHang() {
        return idKhachHang;
    }

    public void setIdKhachHang(Client idKhachHang) {
        this.idKhachHang = idKhachHang;
    }
}
