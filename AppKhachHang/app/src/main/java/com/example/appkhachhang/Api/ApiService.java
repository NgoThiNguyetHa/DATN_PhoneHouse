package com.example.appkhachhang.Api;

import com.example.appkhachhang.Model.ChiTietGioHang;
import com.example.appkhachhang.Model.ChiTietHoaDon;
import com.example.appkhachhang.Model.DanhGia;
import com.example.appkhachhang.Model.HoaDon;
import com.example.appkhachhang.Model.Notification;
import com.example.appkhachhang.Model.Root;
import com.example.appkhachhang.Model.SanPhamHot;
import com.example.appkhachhang.Model.ThongTinDonHang;
import com.example.appkhachhang.Model.TopDienThoai;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
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

    @PUT("/chitietgiohangs/updateChiTietGioHang/{id}")
    Call<ChiTietGioHang> updateGioHang(@Path("id") String id, @Body ChiTietGioHang chiTietGioHang);
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
    Call<String> addChiTietHoaDon(@Body List<ChiTietHoaDon> chiTietHoaDons, @Path("id") String id);

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
    @GET("chitietdienthoais/filterChiTietDienThoai")
    Call<List<Root>> getFilterGiaTien(@Query("GiaMin") int min, @Query("GiaMax") int max, @Query("maHangSanXuat") String maHangSanXuat);

    //filter bộ nhớ
    @GET("chitietdienthoais/filterChiTietDienThoai")
    Call<List<Root>> getFilterBoNho(@Query("boNho") String boNho, @Query("maHangSanXuat") String maHangSanXuat);

    //filter dung lượng ram
    @GET("chitietdienthoais/filterChiTietDienThoai")
    Call<List<Root>> getFilterDlRam(@Query("Ram") String Ram, @Query("maHangSanXuat") String maHangSanXuat);

    //sắp xếp giá cao - thấp
    @GET("chitietdienthoais/filterChiTietDienThoai")
    Call<List<Root>> getSortDown(@Query("sortByPrice") String sortByPrice, @Query("maHangSanXuat") String maHangSanXuat );
    //sắp xếp giá thấp - cao
    @GET("chitietdienthoais/filterChiTietDienThoai")
    Call<List<Root>> getSortUp(@Query("sortByPrice") String sortByPrice, @Query("maHangSanXuat") String maHangSanXuat );

    //ưu đãi hot
    @GET("chitietdienthoais/filterChiTietDienThoai")
    Call<List<Root>> getUuDaiHot(@Query("uuDaiHot") String uuDaiHot,@Query("maHangSanXuat") String maHangSanXuat);

    @GET("chitietdienthoais/filterChiTietDienThoai")
    Call<List<Root>> getBoLocFilter(@Query("Ram") String Ram , @Query("boNho") String boNho, @Query("maHangSanXuat") String maHangSanXuat);

    //get list đánh giá theo chi tiết
    @GET("danhgias/getDanhGia/{id}")
    Call<List<DanhGia>> getListDanhGiaTheoChiTiet(@Path("id") String id);

    @GET("chitietdienthoais/filterChiTietDienThoai")
    Call<List<Root>> getDanhGiaFilter(@Query("sortDanhGia") String sortDanhGia,@Query("id") String id);


    //reload 1 list
    @PUT("/chitietgiohangs/updateLoadListChiTietGioHang/{id}")
    Call<List<ChiTietGioHang>> updateListChiTietGioHang(@Path("id") String id,@Body List<ChiTietGioHang> chiTietGioHang);

    @GET("chitietdienthoais/filterDienThoaiHotNhat")
    Call<List<SanPhamHot>> getFilterGiaTienSPHot(@Query("GiaMin") int min, @Query("GiaMax") int max);

    @GET("chitietdienthoais/filterDienThoaiHotNhat")
    Call<List<SanPhamHot>> getFilterGiaTienSPHot(@Query("GiaMin") int min, @Query("GiaMax") int max, @Query("maHangSanXuat") String maHangSanXuat);

    //filter bộ nhớ
    @GET("chitietdienthoais/filterDienThoaiHotNhat")
    Call<List<SanPhamHot>> getFilterBoNhoSPHot(@Query("boNho") String boNho, @Query("maHangSanXuat") String maHangSanXuat);

    //filter dung lượng ram
    @GET("chitietdienthoais/filterDienThoaiHotNhat")
    Call<List<SanPhamHot>> getFilterDlRamSPHot(@Query("Ram") String Ram, @Query("maHangSanXuat") String maHangSanXuat);

    //sắp xếp giá cao - thấp
    @GET("chitietdienthoais/filterDienThoaiHotNhat")
    Call<List<SanPhamHot>> getSortDownSPHot(@Query("sortByPrice") String sortByPrice, @Query("maHangSanXuat") String maHangSanXuat );
    //sắp xếp giá thấp - cao
    @GET("chitietdienthoais/filterDienThoaiHotNhat")
    Call<List<SanPhamHot>> getSortUpSPHot(@Query("sortByPrice") String sortByPrice, @Query("maHangSanXuat") String maHangSanXuat );

    //ưu đãi hot
    @GET("chitietdienthoais/filterDienThoaiHotNhat")
    Call<List<SanPhamHot>> getUuDaiHotSPHot(@Query("uuDaiHot") String uuDaiHot,@Query("maHangSanXuat") String maHangSanXuat);

    @GET("chitietdienthoais/filterDienThoaiHotNhat")
    Call<List<SanPhamHot>> getBoLocFilterSPHot(@Query("Ram") String Ram , @Query("boNho") String boNho, @Query("maHangSanXuat") String maHangSanXuat);

    //get list đánh giá theo chi tiết
    @GET("danhgias/getDanhGia/{id}")
    Call<List<DanhGia>> getListDanhGiaTheoChiTietSPHot(@Path("id") String id);

    @GET("chitietdienthoais/filterDienThoaiHotNhat")
    Call<List<SanPhamHot>> getDanhGiaFilterSPHot(@Query("sortDanhGia") String sortDanhGia,@Query("id") String id);

    //get thông báo
    @GET("thongbao/getThongBao/{phanQuyen}/{id}")
    Call<List<Notification>> getThongBao(@Path("phanQuyen")String phanQuyen,
                                                    @Path("id")String id);

    //update trạng thái đọc của thông báo
    @PUT("thongbao/updateTrangThaiThongBao/{id}")
    Call<String> updateThongBao(@Path("id") String id, @Body Notification notification);

    //đọc tất cả thông báo
    @PUT("thongbao/updateAll/{phanQuyen}/{id}")
    Call<String> updateAllThongBao(@Path("phanQuyen")String phanQuyen,
                                @Path("id")String id);


    //get top 10 ban chay
    @GET("/thongke/topDienThoai/{maCuaHang}")
    Call<List<TopDienThoai>> getTopDienThoai_CuaHang(@Path("maCuaHang") String id);
    //get khuyen mai

    //get all dien thoai theo maCuaHang
    @GET("chitietdienthoais/getChiTietDienThoaiTheoCuaHang/{maCuaHang}")
    Call<List<Root>> getAllDienThoai_CuaHang(@Path("maCuaHang") String id);

    //get chi tiet dien thoai khuyen mai
    @GET("chitietdienthoais/getChiTietUuDai/{id}")
    Call<List<Root>> getChiTietDienThoaiKhuyenMai(@Path("id") String id);

    @GET("danhgias/getDanhGiaTheoCuaHang/{id}")
    Call<List<DanhGia>> getDanhGiaTheoCuaHang(@Path("id") String id);
    //get so luot danh gia
    @GET("danhgias/getDiemDanhGiaTheoCuaHang/{id}/{diemDanhGia}")
    Call<List<DanhGia>> getSoLuotDanhGiaTheoCuaHang(@Path("id") String id, @Path("diemDanhGia") int diem);

    //sắp xếp giá cao - thấp cua hang
    @GET("chitietdienthoais/sapxepGiaCao-Thap/{maCuaHang}")
    Call<List<Root>> getSortDownTheoCuaHang(@Path("maCuaHang")String id);
    //sắp xếp giá thấp - cao cua hang
    @GET("chitietdienthoais/sapxepGiaThap-Cao/{maCuaHang}")
    Call<List<Root>> getSortUTheoCuaHang(@Path("maCuaHang")String id);
    //get danh gia hinh anh
    @GET("danhgias/getDanhGiaHinhAnhTheoCuaHang/{id}")
    Call<List<DanhGia>> getDanhGiaHinhAnhTheoCuaHang(@Path("id") String id);

    //sap xep giaTien cao - thap cuaHang
    @GET("chitietdienthoais/getSapXepTangChiTietDienThoaiTheoCuaHang/{id}")
    Call<List<Root>> getSapXepGiaTangCuaHang(@Path("id") String id);
    //sap xep giaTien thap - cao cuaHang
    @GET("chitietdienthoais/getSapXepGiamChiTietDienThoaiTheoCuaHang/{id}")
    Call<List<Root>> getSapXepGiaGiamCuaHang(@Path("id") String id);
}

