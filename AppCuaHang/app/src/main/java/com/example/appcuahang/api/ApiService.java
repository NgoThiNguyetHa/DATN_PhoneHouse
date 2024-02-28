package com.example.appcuahang.api;

import com.example.appcuahang.model.Brand;
import com.example.appcuahang.model.HoaDon;
import com.example.appcuahang.model.LoginResponse;
import com.example.appcuahang.model.Store;
import com.example.appcuahang.model.ThongKeDoanhThu;
import com.example.appcuahang.model.ThongKeHoaDon;
import com.example.appcuahang.model.ThongKeKhachHang;
import com.example.appcuahang.model.ThongKeSanPham;
import com.example.appcuahang.model.Top10sanPham;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
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
}
