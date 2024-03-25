package com.example.appcuahang.model;

public class DungLuong {
    private String boNho;


    private String _id;


    public DungLuong(String _id) {
        this._id = _id;
    }

    public String getBoNho() {
        return boNho;
    }

    public void setBoNho(String boNho) {
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
        return boNho ;
    }
}
