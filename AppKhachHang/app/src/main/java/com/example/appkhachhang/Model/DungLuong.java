package com.example.appkhachhang.Model;

import java.io.Serializable;

public class DungLuong implements Serializable {
    String _id;
    String boNho;

    public DungLuong() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getBoNho() {
        return boNho;
    }

    public void setBoNho(String boNho) {
        this.boNho = boNho;
    }
}
