package com.example.appkhachhang.Model;

import java.util.List;

public class Root {
    private ChiTietDienThoai chiTietDienThoai;
    private List<DanhGia> danhGias;

    private float tbDiemDanhGia;

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

    public float getTbDiemDanhGia() {
        return tbDiemDanhGia;
    }

    public void setTbDiemDanhGia(float tbDiemDanhGia) {
        this.tbDiemDanhGia = tbDiemDanhGia;
    }
}
