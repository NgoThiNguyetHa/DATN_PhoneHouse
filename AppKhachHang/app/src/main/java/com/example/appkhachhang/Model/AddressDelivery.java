package com.example.appkhachhang.Model;
public class AddressDelivery {
    private String _id;
    private String sdt;
    private String tenNguoiNhan;
    private String diaChi;
    private User maKhachHang;
    public AddressDelivery(String tenNguoiNhan) {
    }
    public AddressDelivery(String sdt, String tenNguoiNhan, String diaChi, User khachHang) {
        this.sdt = sdt;
        this.tenNguoiNhan = tenNguoiNhan;
        this.diaChi = diaChi;
        this.maKhachHang = khachHang;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getSdt() {
        return sdt;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public String getTenNguoiNhan() {
        return tenNguoiNhan;
    }

    public void setTenNguoiNhan(String tenNguoiNhan) {
        this.tenNguoiNhan = tenNguoiNhan;
    }

    public String getDiaChi() {
        return diaChi;
    }

    public void setDiaChi(String diaChi) {
        this.diaChi = diaChi;
    }

    public User getKhachHang() {
        return maKhachHang;
    }

    public void setKhachHang(User khachHang) {
        this.maKhachHang = khachHang;
    }
}
