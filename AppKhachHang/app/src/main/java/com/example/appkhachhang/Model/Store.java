package com.example.appkhachhang.Model;

import java.io.Serializable;

public class Store implements Serializable {
    private String _id;
    private String diaChi;
    private String username;
    private String password;
    private String sdt;
    private String phanQuyen;
    private String trangThai;
    private String email;

    public String get_id() {
        return _id;
    }

    public Store(String password, String email) {
        this.password = password;
        this.email = email;
    }

    public Store(String password) {
        this.password = password;
    }

    public Store(String diaChi, String username, String sdt, String email) {
        this.diaChi = diaChi;
        this.username = username;
        this.sdt = sdt;
        this.email = email;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getPhanQuyen() {
        return phanQuyen;
    }

    public void setPhanQuyen(String phanQuyen) {
        this.phanQuyen = phanQuyen;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
