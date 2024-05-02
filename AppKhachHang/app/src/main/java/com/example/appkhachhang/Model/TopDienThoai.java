package com.example.appkhachhang.Model;

import java.util.List;

public class TopDienThoai {
    private ChiTietDienThoai chiTietDienThoai;
    private int soLuongBan;
    private List<DanhGia> danhGias;
    private int tbDiemDanhGia;

    public ChiTietDienThoai getChiTietDienThoai() {
        return chiTietDienThoai;
    }

    public void setChiTietDienThoai(ChiTietDienThoai chiTietDienThoai) {
        this.chiTietDienThoai = chiTietDienThoai;
    }

    public List<DanhGia> getDanhGias() {
        return danhGias;
    }

    public void setDanhGias(List<DanhGia> danhGias) {
        this.danhGias = danhGias;
    }

    public int getSoLuongBan() {
        return soLuongBan;
    }

    public void setSoLuongBan(int soLuongBan) {
        this.soLuongBan = soLuongBan;
    }

    public int getTbDiemDanhGia() {
        return tbDiemDanhGia;
    }

    public void setTbDiemDanhGia(int tbDiemDanhGia) {
        this.tbDiemDanhGia = tbDiemDanhGia;
    }

    @Override
    public String toString() {
        return "TopDienThoai{" +
                "chiTietDienThoai=" + chiTietDienThoai +
                ", soLuongBan=" + soLuongBan +
                ", danhGias=" + danhGias +
                ", tbDiemDanhGia=" + tbDiemDanhGia +
                '}';
    }
}
