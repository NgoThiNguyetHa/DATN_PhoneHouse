package com.example.appkhachhang.Model;

import java.io.Serializable;

public class Mau implements Serializable {
    String _id;
    String tenMau;

    public Mau() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTenMau() {
        return tenMau;
    }

    public void setTenMau(String tenMau) {
        this.tenMau = tenMau;
    }
}
