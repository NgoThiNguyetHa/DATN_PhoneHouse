package com.example.appcuahang.model;

public class DungLuong {
    private Number boNho;


    private String _id;


    public DungLuong(String _id) {
        this._id = _id;
    }

    public DungLuong(Number boNho) {
        this.boNho = boNho;
    }

    public Number getBoNho() {
        return boNho;
    }

    public void setBoNho(Number boNho) {
        this.boNho = boNho;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @Override
    public String toString() {
        return boNho.toString() ;
    }
}
