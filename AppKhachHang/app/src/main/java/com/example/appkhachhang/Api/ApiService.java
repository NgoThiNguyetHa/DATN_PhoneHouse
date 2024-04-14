package com.example.appkhachhang.Api;

import com.example.appkhachhang.Model.ChiTietGioHang;
import com.example.appkhachhang.Model.GioHang;
import com.example.appkhachhang.Model.HoaDon;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;


public interface ApiService {

    @GET("/chitietgiohangs/getChiTietGioHangTheoKhachHang/{id}")
    Call<List<ChiTietGioHang>> getListGioHang(@Path("id") String id);

    @POST("/chitietgiohangs/addChiTietGioHang/{idKhachHang}")
    Call<ChiTietGioHang> addGioHang(@Body ChiTietGioHang chiTietGioHang, @Path("idKhachHang") String idKhachHang);

    @DELETE("/chitietgiohangs/deleteChiTietGioHang/{id}")
    Call<ChiTietGioHang> deleteGioHang(@Path("id") String id);

    //lấy list danh sách hóa đơn theo trạng thái
    @GET("hoadons/getHoaDonTheoTrangThai/{trangThaiNhanHang}")
    Call<List<HoaDon>> getHoaDonByTrangThai(@Path("trangThaiNhanHang") String trangThaiNhanHang);

    //lấy list danh sách hóa đơn theo trạng thái của cửa hàng
    @GET("hoadons/getHoaDonTheoTrangThai-KH/{trangThaiNhanHang}/{maKhachHang}")
    Call<List<HoaDon>> getHoaDonTrangThai(@Path("trangThaiNhanHang") String trangThaiNhanHang,
                                          @Path("maKhachHang") String maCuaHang);

    @GET("hoadons/getHoaDonTheoChiTiet")
    Call<List<HoaDon>> getAllHoaDon();

    //thống kê khách hàng theo ngày
    @GET("thongke/thongKeSoLuongKhachHang/{id}/{ngayTao}")
    Call<String> getSoLuongKhachHangTheoNgay(@Path("id") String id);

    @POST("/hoadons/addHoaDon")
    Call<HoaDon> addHoaDon(@Body HoaDon hoaDon);

    @POST("/chitiethoadons/addChiTietHoaDon")
    Call<List<ChiTietHoaDon>> addChiTietHoaDon(@Body List<ChiTietHoaDon> chiTietHoaDons);

    @GET("chitiethoadons/getChiTietHoaDonTheoLichSuMuaHang/{maKhachHang}")
    Call<List<ChiTietHoaDon>> getCTHDTheoLichSu(@Path("maKhachHang") String maKhachHang);
}

