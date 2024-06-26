package com.example.appkhachhang.Api;

import com.example.appkhachhang.Model.ChiTietDienThoai;
import com.example.appkhachhang.Model.ChiTietGioHang;
import com.example.appkhachhang.Model.ChiTietHoaDon;
import com.example.appkhachhang.Model.GioHang;
import com.example.appkhachhang.Model.HoaDon;
import com.example.appkhachhang.Model.ListPhone;
import com.example.appkhachhang.Model.Root;
import com.example.appkhachhang.Model.ThongTinDonHang;
import com.example.appkhachhang.Model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;


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

    @POST("/chitiethoadons/addChiTietHoaDon/{id}")
    Call<List<ChiTietHoaDon>> addChiTietHoaDon(@Body List<ChiTietHoaDon> chiTietHoaDons, @Path("id") String id);

    @GET("chitiethoadons/getChiTietHoaDonTheoLichSuMuaHang/{maKhachHang}")
    Call<List<ChiTietHoaDon>> getCTHDTheoLichSu(@Path("maKhachHang") String maKhachHang);

    //chi tiết hóa đơn
    @GET("chitiethoadons/getChiTietHoaDonTheoHoaDon/{id}")
    Call<List<ThongTinDonHang>> getThongTinDonHang(@Path("id") String id);

    //update chi tiết
    @PUT("hoadons/updateHoaDon/{id}")
    Call<HoaDon> updateHoaDon(@Path("id") String id, @Body HoaDon hoaDon);

    //get điện thoại theo hãng
//    @GET("chitietdienthoais/getChiTietTheoHangSanXuat/{id}")
//    Call<List<ListPhone>> getAllChiTietTheoHang(@Path("id") String id);

    @GET("chitietdienthoais/getChiTietTheoHangSanXuatDG/{id}")
    Call<List<Root>> getAllChiTietTheoHang(@Path("id") String id);

//    //filter giá tiền
//    @GET("chitietdienthoais/filterGiaChiTietDienThoai")
//    Call<List<ListPhone>> getFilterGiaTien(@Query("GiaMin") int min, @Query("GiaMax") int max);
//
//    //filter bộ nhớ
//    @GET("chitietdienthoais/filterBoNhoChiTietDienThoai")
//    Call<List<ListPhone>> getFilterBoNho(@Query("boNho") String boNho);
//
//    //filter dung lượng ram
//    @GET("chitietdienthoais/filterRamChiTietDienThoai")
//    Call<List<ListPhone>> getFilterDlRam(@Query("Ram") String Ram);
//
//    //sắp xếp giá cao - thấp
//    @GET("chitietdienthoais/sapxepGiaCao-Thap")
//    Call<List<ListPhone>> getSortDown();
//    //sắp xếp giá thấp - cao
//    @GET("chitietdienthoais/sapxepGiaThap-Cao")
//    Call<List<ListPhone>> getSortUp();
//
//    //ưu đãi hot
//    @GET("chitietdienthoais/uuDaiHot")
//    Call<List<ListPhone>> getUuDaiHot();

    //filter giá tiền
    @GET("chitietdienthoais/filterGiaChiTietDienThoai")
    Call<List<Root>> getFilterGiaTien(@Query("GiaMin") int min, @Query("GiaMax") int max);

    //filter bộ nhớ
    @GET("chitietdienthoais/filterBoNhoChiTietDienThoai")
    Call<List<Root>> getFilterBoNho(@Query("boNho") String boNho);

    //filter dung lượng ram
    @GET("chitietdienthoais/filterRamChiTietDienThoai")
    Call<List<Root>> getFilterDlRam(@Query("Ram") String Ram);

    //sắp xếp giá cao - thấp
    @GET("chitietdienthoais/sapxepGiaCao-Thap")
    Call<List<Root>> getSortDown();
    //sắp xếp giá thấp - cao
    @GET("chitietdienthoais/sapxepGiaThap-Cao")
    Call<List<Root>> getSortUp();

    //ưu đãi hot
    @GET("chitietdienthoais/uuDaiHot")
    Call<List<Root>> getUuDaiHot();

    @GET("chitietdienthoais/filterDienThoai")
    Call<List<Root>> getBoLocFilter(@Query("Ram") String Ram , @Query("boNho") String boNho);
}

