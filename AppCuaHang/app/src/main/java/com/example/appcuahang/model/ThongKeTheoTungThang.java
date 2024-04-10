package com.example.appcuahang.model;

public class ThongKeTheoTungThang {
    private int _id;
    private double tongTien;

    public ThongKeTheoTungThang(int _id, double tongTien) {
        this._id = _id;
        this.tongTien = tongTien;
    }

    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public double getTongTien() {
        return tongTien;
    }

    public void setTongTien(double tongTien) {
        this.tongTien = tongTien;
    }
}
