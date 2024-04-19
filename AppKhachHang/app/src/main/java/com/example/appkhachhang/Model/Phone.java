package com.example.appkhachhang.Model;

import java.io.Serializable;

public class Phone implements Serializable {
    private String tenDienThoai;
    private String kichThuoc;
    private String congNgheManHinh;
    private String camera;
    private String cpu ;
    private String pin ;
    private String heDieuHanh;
    private String doPhanGiai;
    private String namSanXuat;
    private String thoiGianBaoHanh;
    private String moTaThem;
    private HangSanXuat maHangSX;
    private String hinhAnh;
    private UuDai maUuDai;
    private Store maCuaHang;
    private String _id;

    public Phone() {
    }

    public Phone(String tenDienThoai, String kichThuoc, String congNgheManHinh, String camera, String cpu, String pin, String heDieuHanh, String doPhanGiai, String namSanXuat, String thoiGianBaoHanh, String moTaThem, HangSanXuat maHangSX, String hinhAnh, UuDai maUuDai , Store maCuaHang) {
        this.tenDienThoai = tenDienThoai;
        this.kichThuoc = kichThuoc;
        this.congNgheManHinh = congNgheManHinh;
        this.camera = camera;
        this.cpu = cpu;
        this.pin = pin;
        this.heDieuHanh = heDieuHanh;
        this.doPhanGiai = doPhanGiai;
        this.namSanXuat = namSanXuat;
        this.thoiGianBaoHanh = thoiGianBaoHanh;
        this.moTaThem = moTaThem;
        this.maHangSX = maHangSX;
        this.hinhAnh = hinhAnh;
        this.maUuDai = maUuDai;
        this.maCuaHang = maCuaHang;
    }

    public Phone(String _id) {
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

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
