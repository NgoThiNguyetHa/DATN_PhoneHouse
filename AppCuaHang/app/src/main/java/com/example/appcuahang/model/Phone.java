package com.example.appcuahang.model;

public class Phone {
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
    private String maHangSX;
    private String hinhAnh;
    private String maUuDai;

    public Phone() {
    }

    public Phone(String tenDienThoai, String kichThuoc, String congNgheManHinh, String camera, String cpu, String pin, String heDieuHanh, String doPhanGiai, String namSanXuat, String thoiGianBaoHanh, String moTaThem, String maHangSX, String hinhAnh, String maUuDai) {
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

    public String getCPU() {
        return cpu;
    }

    public void setCPU(String cpu) {
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

    public String getMaHangSX() {
        return maHangSX;
    }

    public void setMaHangSX(String maHangSX) {
        this.maHangSX = maHangSX;
    }

    public String getMaUuDai() {
        return maUuDai;
    }

    public void setMaUuDai(String maUuDai) {
        this.maUuDai = maUuDai;
    }
}
