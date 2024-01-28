package com.example.appcuahang.model;

public class LoginResponse {
    private String successMessage;
    private Store cuaHang;

    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public Store getCuaHang() {
        return cuaHang;
    }

    public void setCuaHang(Store cuaHang) {
        this.cuaHang = cuaHang;
    }
}
