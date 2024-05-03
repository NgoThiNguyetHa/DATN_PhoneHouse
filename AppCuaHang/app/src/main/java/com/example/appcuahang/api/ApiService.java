package com.example.appcuahang.api;

import com.example.appcuahang.model.Brand;
import com.example.appcuahang.model.ChiTietHoaDon;
import com.example.appcuahang.model.DetailPhone;
import com.example.appcuahang.model.DungLuong;
import com.example.appcuahang.model.HoaDon;
import com.example.appcuahang.model.LoginResponse;
import com.example.appcuahang.model.Mau;
import com.example.appcuahang.model.Notification;
import com.example.appcuahang.model.Phone;
import com.example.appcuahang.model.Ram;
import com.example.appcuahang.model.Rating;
import com.example.appcuahang.model.Store;
import com.example.appcuahang.model.ThongKeDoanhThu;
import com.example.appcuahang.model.ThongKeDonHuy;
import com.example.appcuahang.model.ThongKeHoaDon;
import com.example.appcuahang.model.ThongKeKhachHang;
import com.example.appcuahang.model.ThongKeSanPham;
import com.example.appcuahang.model.ThongKeTheoTungThang;
import com.example.appcuahang.model.Top10sanPham;
import com.example.appcuahang.model.UuDai;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    //lấy list danh sách hãng sản xuất
    @GET("hangsanxuats/getHangSanXuat")
    Call<List<Brand>> getHangSanXuat();

    //lấy list danh sách hãng sản xuất theo cửa hàng
    @GET("hangsanxuats/getHangSanXuatTheoCuaHang/{maCuaHang}")
    Call<List<Brand>> getHangSanXuatByCuaHang(@Path("maCuaHang") String id);

    //thêm hãng sản xuất
    @POST("hangsanxuats/addHangSanXuat")
    Call<Brand> postHangSanXuat(@Body Brand hangSanXuat);

    //cập nhật hãng sản xuất
    @PUT("hangsanxuats/updateHangSanXuat/{id}")
    Call<Brand> putHangSanXuat(@Path("id") String id,
                            @Body Brand hangSanXuat);

    //lấy list danh sách hóa đơn theo trạng thái
    @GET("hoadons/getHoaDonTheoTrangThai/{trangThaiNhanHang}")
    Call<List<HoaDon>> getHoaDonByTrangThai(@Path("trangThaiNhanHang") String trangThaiNhanHang);

    //lấy list danh sách hóa đơn theo trạng thái của cửa hàng
    @GET("hoadons/getHoaDonTheoTrangThai/{trangThaiNhanHang}/{maCuaHang}")
    Call<List<HoaDon>> getHoaDonTrangThai(@Path("trangThaiNhanHang") String trangThaiNhanHang,
                                            @Path("maCuaHang") String maCuaHang);


    @GET("hoadons/getHoaDonTheoChiTiet")
    Call<List<HoaDon>> getAllHoaDon();

    //đăng nhập login
    @FormUrlEncoded
    @POST("cuahangs/dangNhapCuaHang")
    Call<LoginResponse> DangNhap(@Field("email") String email,
                                 @Field("password") String password);

    //thay đổi thông tin - đổi mật khẩu
    @PUT("cuahangs/updateCuaHang/{id}")
    Call<Store> putCuaHang(@Path("id") String id,
                               @Body Store store);

    //thống kê khách hàng theo ngày
    @GET("thongke/thongKeSoLuongKhachHang/{id}/{ngayTao}")
    Call<String> getSoLuongKhachHangTheoNgay(@Path("id") String id,
                                              @Path("ngayTao") String ngayTao);

    //thống kê hóa đơn theo trạng thái - maCuaHang - ngày hiện tại
    @GET("thongke/soLuongHoaDon/{trangThaiNhanHang}/{maCuaHang}/{ngayTao}")
    Call<List<ThongKeHoaDon>> getCountByStatusAndDate(
            @Path("trangThaiNhanHang") String status,
            @Path("maCuaHang") String maCuaHang,
            @Path("ngayTao") String ngayTao
    );
    //thống kê số lượng khách hàng có trong hóa đơn - maCuaHang - ngày hiện tại
    @GET("thongke/soLuongKhachHang/{maCuaHang}/{ngayTao}")
    Call<ThongKeKhachHang> getSoLuongKhachHang(
            @Path("maCuaHang") String maCuaHang,
            @Path("ngayTao") String ngayTao
    );

    @GET("thongke/soLuongSanPham/{maCuaHang}/{ngayTao}")
    Call<ThongKeSanPham> getSoLuongSanPham(
            @Path("maCuaHang") String maCuaHang,
            @Path("ngayTao") String ngayTao
    );

    @GET("thongke/thongKeTongTien/{trangThaiNhanHang}/{maCuaHang}/{ngayTao}")
    Call<ThongKeDoanhThu> getDoanhThuHomNay(
            @Path("trangThaiNhanHang") String trangThaiNhanHang,
            @Path("maCuaHang") String maCuaHang,
            @Path("ngayTao") String ngayTao
    );

    //top10 theo ngày
    @GET("thongke/top10sanpham/{tuNgay}/{denNgay}/{maCuaHang}")
    Call<List<Top10sanPham>> getTop10Product(
            @Path("tuNgay") String tuNgay,
            @Path("denNgay") String denNgay,
            @Path("maCuaHang") String maCuaHang
    );

    //doanh thu theo ngày

    @GET("thongke/doanhThuTongTien")
    Call<ThongKeDoanhThu> getDoanhThuTheoNgay(@Query("startDate") String tuNgay,
                                              @Query("endDate") String denNgay,
                                              @Query("maCuaHang") String maCuaHang,
                                              @Query("trangThaiNhanHang") String trangThai);

    // get điện thoại
    @GET("dienthoais/getDienthoaiTheoCuaHang/{maCuaHang}")
    Call<List<Phone>> getDienThoaiTheoCuaHang(@Path("maCuaHang") String idCuaHang);

    //post điện thoại
    @POST("dienthoais/addDienThoai")
    Call<Phone> addDienThoai(@Body Phone phone);

    //cập nhật điện thoại
    @PUT("dienthoais/updateDienThoai/{id}")
    Call<Phone> putDienThoai(@Path("id") String id,
                               @Body Phone phone);

    //spinner
    @GET("rams/getRam")
    Call<List<Ram>> getRamSpinner();

    @GET("maus/getMau")
    Call<List<Mau>> getMauSpinner();

    @GET("dungluongs/getDungLuong")
    Call<List<DungLuong>> getDungLuongSpinner();

    //thêm chi tiết

    @POST("chitietdienthoais/addChiTiet")
    Call<DetailPhone> addChiTietDienThoai(@Body DetailPhone detailPhone);
    @PUT("chitietdienthoais/updateChiTiet/{id}")
    Call<DetailPhone> putChiTietDienThoai(@Path("id") String id,
                             @Body DetailPhone detailPhone);

    //lấy chi tiết
    @GET("chitietdienthoais/getChiTietTheoDienThoai/{maDienThoai}")
    Call<List<DetailPhone>> getChiTiet(@Path("maDienThoai") String id);


    //lay danh sach uu dai cua hang
    @GET("uudais/getUuDai-Active/{id}")
    Call<List<UuDai>> getUuDaiCuaHang(@Path("id") String id);

    @PUT("dienthoais/updateMaUuDaiDienThoai/{id}")
    Call<Phone> putUuDaiDienThoai(@Path("id") String id,
                                 @Body UuDai maUuDai);

    //thong ke theo từng tháng
    @GET("/thongke/thongke1/{nam}/{maCuaHang}")
    Call<List<ThongKeTheoTungThang>> getThongKeTheoNam(@Path("nam") int nam,
                                                       @Path("maCuaHang") String maCuaHang);
    //get danh gia
    @GET("danhgias/getDanhGiaTheoDienThoai/{id}")
    Call<List<Rating>> getDanhGiaTheoDienThoai(@Path("id") String id);
    //get theo cua hang
    @GET("danhgias/getDanhGiaTheoCuaHang/{id}")
    Call<List<Rating>> getDanhGiaTheoCuaHang(@Path("id") String id);
    //xoa binh luan
    @DELETE("danhgias/deleteDanhGia/{id}")
    Call<Rating> deleteDanhGia(@Path("id") String id);

    @GET("chitiethoadons/getChiTietHoaDonTheoHoaDon/{id}")
    Call<List<ChiTietHoaDon>> getChiTietHoaDon(@Path("id") String id);

    //thống kê số lương - tổng tiền hủy
    @GET("thongke/tongDonHuy/{maCuaHang}")
    Call<ThongKeDonHuy> getSoLuongTongTienDonHuy(@Path("maCuaHang") String id);

    @GET("thongke/top-products/{maCuaHang}")
    Call<List<Top10sanPham>> getTop10SP(@Path("maCuaHang") String id,
                                        @Query("day") String day );

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
}
