package com.example.appkhachhang.Model;

import java.io.Serializable;

public class Ram implements Serializable {
    String _id;
    String RAM;

    public Ram() {
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getRAM() {
        return RAM;
    }

    public void setRAM(String RAM) {
        this.RAM = RAM;
    }
}
