package com.example.appcuahang.model;

public class DungLuong {
    private String boNho;
    private int giaTien;

    private String _id;


    public DungLuong() {
    }

    public DungLuong(String boNho, int giaTien) {
        this.boNho = boNho;
        this.giaTien = giaTien;
    }

    public String getBoNho() {
        return boNho;
    }

    public void setBoNho(String boNho) {
        this.boNho = boNho;
    }

    public int getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(int giaTien) {
        this.giaTien = giaTien;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }
}
