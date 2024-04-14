package com.example.appkhachhang.Model;

import java.io.Serializable;

public class SanPham implements Serializable {
    String _id;
    String tenDienThoai;
    String kichThuoc;
    String congNgheManHinh;
    String camera;
    String cpu;
    String pin;
    String heDieuHanh;
    String doPhanGiai;
    String namSanXuat;
    String thoiGianBaoHanh;
    String moTaThem;
    String hinhAnh;
    HangSanXuat maHangSX;
    UuDai maUuDai;
    Store maCuaHang;

    public SanPham() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTenDienThoai() {
        return tenDienThoai;
    }

    public void setTenDienThoai(String tenDienThoai) {
        this.tenDienThoai = tenDienThoai;
    }

    public String getCamera() {
        return camera;
    }

    public void setCamera(String camera) {
        this.camera = camera;
    }

    public String getKichThuoc() {
        return kichThuoc;
    }

    public void setKichThuoc(String kichThuoc) {
        this.kichThuoc = kichThuoc;
    }

    public String getCpu() {
        return cpu;
    }

    public void setCpu(String cpu) {
        this.cpu = cpu;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getHeDieuHanh() {
        return heDieuHanh;
    }

    public void setHeDieuHanh(String heDieuHanh) {
        this.heDieuHanh = heDieuHanh;
    }

    public String getNamSanXuat() {
        return namSanXuat;
    }

    public void setNamSanXuat(String namSanXuat) {
        this.namSanXuat = namSanXuat;
    }

    public String getCongNgheManHinh() {
        return congNgheManHinh;
    }

    public void setCongNgheManHinh(String congNgheManHinh) {
        this.congNgheManHinh = congNgheManHinh;
    }

    public String getDoPhanGiai() {
        return doPhanGiai;
    }

    public void setDoPhanGiai(String doPhanGiai) {
        this.doPhanGiai = doPhanGiai;
    }

    public String getHinhAnh() {
        return hinhAnh;
    }

    public void setHinhAnh(String hinhAnh) {
        this.hinhAnh = hinhAnh;
    }

    public String getThoiGianBaoHanh() {
        return thoiGianBaoHanh;
    }

    public void setThoiGianBaoHanh(String thoiGianBaoHanh) {
        this.thoiGianBaoHanh = thoiGianBaoHanh;
    }

    public String getMoTaThem() {
        return moTaThem;
    }

    public void setMoTaThem(String moTaThem) {
        this.moTaThem = moTaThem;
    }

    public HangSanXuat getMaHangSX() {
        return maHangSX;
    }

    public void setMaHangSX(HangSanXuat maHangSX) {
        this.maHangSX = maHangSX;
    }

    public UuDai getMaUuDai() {
        return maUuDai;
    }

    public void setMaUuDai(UuDai maUuDai) {
        this.maUuDai = maUuDai;
    }

    public Store getMaCuaHang() {
        return maCuaHang;
    }

    public void setMaCuaHang(Store maCuaHang) {
        this.maCuaHang = maCuaHang;
    }
}
