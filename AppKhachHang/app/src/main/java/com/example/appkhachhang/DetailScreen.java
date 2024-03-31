package com.example.appkhachhang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appkhachhang.Api.ApiRetrofit;
import com.example.appkhachhang.Api.ApiService;
import com.example.appkhachhang.Api.ChiTietSanPham_API;
import com.example.appkhachhang.Model.ChiTietDienThoai;
import com.example.appkhachhang.Model.ChiTietGioHang;
import com.example.appkhachhang.Model.GioHang;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DetailScreen extends AppCompatActivity {
    ImageView imgAnhChiTiet;
    TextView tv_tenDienThoai, tv_giaChiTiet, tv_soLuong, tv_moTa, tv_danhGia, tvSoLuongGioHang;
    ChiTietDienThoai chiTietDienThoai;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_screen);
        imgAnhChiTiet = findViewById(R.id.imgAnhChiTiet);
        tv_tenDienThoai = findViewById(R.id.tv_tenSPChiTiet);
        tv_giaChiTiet = findViewById(R.id.tv_giaChiTiet);
        tv_soLuong = findViewById(R.id.tv_soLuongChiTiet);
        tv_moTa = findViewById(R.id.tv_moTaChiTiet);
        tv_danhGia = findViewById(R.id.tv_danhGia);
        SharedPreferences prefs = getSharedPreferences("user_info", MODE_PRIVATE);
        String idKhachHang = prefs.getString("idKhachHang", "abc");
        SharedPreferences preferences = getSharedPreferences("chiTiet", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = preferences.getString("chiTietDienThoai", null);
        chiTietDienThoai = gson.fromJson(json, ChiTietDienThoai.class);

        Picasso.get().load(chiTietDienThoai.getMaDienThoai().getHinhAnh()).into(imgAnhChiTiet);
        tv_tenDienThoai.setText(chiTietDienThoai.getMaDienThoai().getTenDienThoai());
        tv_giaChiTiet.setText(chiTietDienThoai.getGiaTien() + "đ");
        tv_danhGia.setText("5 | ");
        tv_soLuong.setText("Kho: " +  chiTietDienThoai.getSoLuong());
        tv_moTa.setText("\nHãng sản xuất: "+ chiTietDienThoai.getMaDienThoai().getMaHangSX().getTenHang()
                +"\nMàu: " + chiTietDienThoai.getMaMau().getTenMau() +
                "\nRam: " + chiTietDienThoai.getMaRam().getRAM() +
                "\nDung lượng: " + chiTietDienThoai.getMaDungLuong().getBoNho() +
                "\nKích thước: " + chiTietDienThoai.getMaDienThoai().getKichThuoc() +
                "\nCông nghệ màn hình: " + chiTietDienThoai.getMaDienThoai().getCongNgheManHinh()
                + "\nCamera: " + chiTietDienThoai.getMaDienThoai().getCamera()
                + "\nCPU: " + chiTietDienThoai.getMaDienThoai().getCpu()
                + "\nPin: " + chiTietDienThoai.getMaDienThoai().getPin()
                + "\nHệ điều hành: " + chiTietDienThoai.getMaDienThoai().getHeDieuHanh()
                + "\nĐộ phân giải: " + chiTietDienThoai.getMaDienThoai().getDoPhanGiai()
                + "\nNăm sản xuất: " + chiTietDienThoai.getMaDienThoai().getNamSanXuat()
                + "\nThời gian bảo hành: " + chiTietDienThoai.getMaDienThoai().getThoiGianBaoHanh()
                + "\n" +  chiTietDienThoai.getMaDienThoai().getMoTaThem());


        findViewById(R.id.btnAddGioHang).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ChiTietGioHang chiTietGioHang = new ChiTietGioHang();
                chiTietGioHang.setMaChiTietDienThoai(chiTietDienThoai);
                chiTietGioHang.setSoLuong("1");
                chiTietGioHang.setGiaTien(chiTietGioHang.getGiaTien());
                ApiRetrofit.getApiService().addGioHang(chiTietGioHang, idKhachHang).enqueue(new Callback<ChiTietGioHang>() {
                    @Override
                    public void onResponse(Call<ChiTietGioHang> call, Response<ChiTietGioHang> response) {
                        if (response.body()!=null){
                            Toast.makeText(DetailScreen.this, "Thêm thành công", Toast.LENGTH_SHORT).show();
                        } else {
                            handleErrorResponse(response);
                        }
                    }

                    @Override
                    public void onFailure(Call<ChiTietGioHang> call, Throwable t) {
                        Log.d("error", "onFailure: " + t.getMessage());
                    }
                });
            }
        });
    }
    private void handleErrorResponse(Response<ChiTietGioHang> response) {
        try {
            String errorBody = response.errorBody().string();
            Log.e("ERROR_RESPONSE", errorBody);
            // Display error message to the user
            Toast.makeText(DetailScreen.this, "Lỗi từ server: " + errorBody, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}