package com.example.appkhachhang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class DetailScreen extends AppCompatActivity {
    ImageView imgAnhChiTiet;
    TextView tv_tenDienThoai, tv_giaChiTiet, tv_soLuong, tv_moTa;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_screen);
        imgAnhChiTiet = findViewById(R.id.imgChiTiet);
        tv_moTa = findViewById(R.id.tv_moTaChiTiet);
        tv_soLuong = findViewById(R.id.tv_SoLuongChiTiet);
        tv_giaChiTiet = findViewById(R.id.tv_giaChiTiet);
        tv_tenDienThoai = findViewById(R.id.tv_tenDienThoaiChiTiet);
        Intent intent = getIntent();
        String tenDienThoai = intent.getStringExtra("tenDienThoai");
        String giaChiTiet = intent.getStringExtra("giaTien");
        String soLuong = intent.getStringExtra("soLuong");
        String moTa = intent.getStringExtra("moTaThem");
        String fullCoverImgUrl = intent.getStringExtra("anh");
        Picasso.get().load(fullCoverImgUrl).into(imgAnhChiTiet);
        tv_tenDienThoai.setText(tenDienThoai);
        tv_giaChiTiet.setText(giaChiTiet+"đ");
        tv_soLuong.setText("Kho: "+soLuong);
        tv_moTa.setText(moTa);
    }
}