package com.example.appcuahang.model;

import java.io.Serializable;

public class AddressDelivery implements Serializable {

    private String _id;
    private String sdt;
    private String tenNguoiNhan;
    private String diaChi;
    private Client khachHang;


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getTenNguoiNhan() {
        return tenNguoiNhan;
    }

    public void setTenNguoiNhan(String tenNguoiNhan) {
        this.tenNguoiNhan = tenNguoiNhan;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public Client getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(Client khachHang) {
        this.khachHang = khachHang;
    }
}
