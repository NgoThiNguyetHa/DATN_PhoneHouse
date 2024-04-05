package com.example.appkhachhang.Model;

public class DanhGia {
    private String noiDung;
    private String hinhAnh;
    private Number diemDanhGia;
    private String ngayTao;
    private User idKhachHang;
    private ChiTietDienThoai idChiTietDienThoai;

    public DanhGia(String noiDung, String hinhAnh, Number diemDanhGia, String ngayTao, User idKhachHang, ChiTietDienThoai idChiTietDienThoai) {
        this.noiDung = noiDung;
        this.hinhAnh = hinhAnh;
        this.diemDanhGia = diemDanhGia;
        this.ngayTao = ngayTao;
        this.idKhachHang = idKhachHang;
        this.idChiTietDienThoai = idChiTietDienThoai;
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

    public Number getDiemDanhGia() {
        return diemDanhGia;
    }

    public void setDiemDanhGia(Number diemDanhGia) {
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
