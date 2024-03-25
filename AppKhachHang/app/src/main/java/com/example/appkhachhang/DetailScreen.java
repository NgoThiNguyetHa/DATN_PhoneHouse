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
    TextView tv_tenDienThoai, tv_giaChiTiet, tv_soLuong, tv_moTa, tv_danhGia;
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
        String mau = intent.getStringExtra("mau");
        String ram = intent.getStringExtra("ram");
        String dungLuong = intent.getStringExtra("dungLuong");
        Picasso.get().load(hinhAnh).into(imgAnhChiTiet);
        tv_tenDienThoai.setText(tenDienThoai);
        tv_giaChiTiet.setText(giaTien + "đ");
        tv_danhGia.setText("5 | ");
        tv_soLuong.setText("Kho: " +  soLuong);
        tv_moTa.setText("\nHãng sản xuất: "+ hangSanXuat
                +"\nMàu: " + mau +
                "\nRam: " + ram +
                "\nDung lượng: " + dungLuong +
                "\nKích thước: " + kichThuoc +
                "\nCông nghệ màn hình: " + congNgheManHinh
                + "\nCamera: " + camera
                + "\nCPU: " + cpu
                + "\nPin: " + pin
                + "\nHệ điều hành: " + heDieuHanh
                + "\nĐộ phân giải: " + doPhanGiai
                + "\nNăm sản xuất: " + namSanXuat
                + "\nThời gian bảo hành: " + thoiGianBaoHanh
                + "\n" +  moTaThem);

    }
}