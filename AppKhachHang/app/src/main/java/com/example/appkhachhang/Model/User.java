package com.example.appkhachhang.Model;

import java.io.Serializable;

public class User implements Serializable {
    String _id;
    String diaChi;
    String username;
    String password;
    String email;
    String sdt;

    public User(String _id, String diaChi, String username, String password, String email, String sdt) {
        this._id = _id;
        this.diaChi = diaChi;
        this.username = username;
        this.password = password;
        this.email = email;
        this.sdt = sdt;
    }

    public User(String _id) {
        this._id = _id;
    }

    public User() {
    }

    public String get_id() {
        return _id;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }
}
