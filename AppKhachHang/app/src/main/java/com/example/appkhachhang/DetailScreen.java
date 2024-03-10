package com.example.appkhachhang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appkhachhang.Model.ChiTietDienThoai;
import com.squareup.picasso.Picasso;

public class DetailScreen extends AppCompatActivity {
    ImageView imgAnhChiTiet;
    TextView tv_tenDienThoai, tv_giaChiTiet, tv_soLuong, tv_moTa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_screen);
        Intent intent = getIntent();
        String soLuong = intent.getStringExtra("soLuong");
        String giaTien = intent.getStringExtra("giaTien");
        String tenDienThoai = intent.getStringExtra("tenDienThoai");
        String kichThuoc = intent.getStringExtra("kichThuoc");
        String congNgheManHinh = intent.getStringExtra("congNgheManHinh");
        String camera = intent.getStringExtra("camera");
        String cpu = intent.getStringExtra("cpu");
        String pin = intent.getStringExtra("pin");
        String heDieuHanh = intent.getStringExtra("heDieuHanh");
        String doPhanGiai = intent.getStringExtra("doPhanGiai");
        String namSanXuat = intent.getStringExtra("namSanXuat");
        String thoiGianBaoHanh = intent.getStringExtra("thoiGianBaoHanh");
        String moTaThem = intent.getStringExtra("moTaThem");
        String hinhAnh = intent.getStringExtra("hinhAnh");
        String hangSanXuat = intent.getStringExtra("hangSanXuat");
        String uuDai = intent.getStringExtra("uuDai");
        Log.d("abcabc", "onCreate: " + tenDienThoai);
    }
}