package com.example.appcuahang.model;

public class Ram {
    private Number RAM;
    private String _id;

    public Ram() {
    }

    public Ram(String _id) {
        this._id = _id;
    }

    public Ram(Number RAM) {
        this.RAM = RAM;
    }

    public Number getRAM() {
        return RAM;
    }

    public void setRAM(Number RAM) {
        this.RAM = RAM;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    @Override
    public String toString() {
        return RAM.toString() ;
    }
}
