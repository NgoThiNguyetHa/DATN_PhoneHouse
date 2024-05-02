package com.example.appkhachhang.Model;

public class Notification {
  private String _id;
  private String noiDung;
  private String thoiGian;
  private String trangThai;
  private String maTaiKhoan;
  private String phanQuyen;
  private HoaDon maHoaDon;

  public Notification(String trangThai) {
    this.trangThai = trangThai;
  }

  public String get_id() {
    return _id;
  }

  public void set_id(String _id) {
    this._id = _id;
  }

  public String getNoiDung() {
    return noiDung;
  }

  public void setNoiDung(String noiDung) {
    this.noiDung = noiDung;
  }

  public String getThoiGian() {
    return thoiGian;
  }

  public void setThoiGian(String thoiGian) {
    this.thoiGian = thoiGian;
  }

  public String getTrangThai() {
    return trangThai;
  }

  public void setTrangThai(String trangThai) {
    this.trangThai = trangThai;
  }

  public String getMaTaiKhoan() {
    return maTaiKhoan;
  }

  public void setMaTaiKhoan(String maTaiKhoan) {
    this.maTaiKhoan = maTaiKhoan;
  }

  public String getPhanQuyen() {
    return phanQuyen;
  }

  public void setPhanQuyen(String phanQuyen) {
    this.phanQuyen = phanQuyen;
  }

  public HoaDon getMaHoaDon() {
    return maHoaDon;
  }

  public void setMaHoaDon(HoaDon maHoaDon) {
    this.maHoaDon = maHoaDon;
  }
}
