package com.example.appkhachhang.Model;

import java.util.List;

public class Root {
    private ChiTietDienThoai chiTietDienThoai;
    private List<DanhGia> danhGias;

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
}
