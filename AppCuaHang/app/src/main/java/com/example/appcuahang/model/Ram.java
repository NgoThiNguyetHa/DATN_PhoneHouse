package com.example.appcuahang.model;

public class Ram {
    private String RAM;
    private int giaTien;

    public Ram() {
    }

    public Ram(String RAM, int giaTien) {
        this.RAM = RAM;
        this.giaTien = giaTien;
    }

    public String getRAM() {
        return RAM;
    }

    public void setRAM(String RAM) {
        this.RAM = RAM;
    }

    public int getGiaTien() {
        return giaTien;
    }

    public void setGiaTien(int giaTien) {
        this.giaTien = giaTien;
    }
}
