package com.example.appkhachhang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appkhachhang.Model.ChiTietDienThoai;
import com.example.appkhachhang.Model.ChiTietHoaDon;
import com.example.appkhachhang.untils.MySharedPreferences;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FeedbackScreen extends AppCompatActivity {
    ImageView imgDienThoaiDG, img_sao_xam_1, img_sao_xam_2, img_sao_xam_3, img_sao_xam_4, img_sao_xam_5, img_anh_danh_gia;

    TextView bill_item_tvDienThoaiDG, bill_item_tvMauDG, bill_item_tvSoLuongDG, bill_item_tvTongTienDG, edFeedback;

    Button btnFeedback;

    List<ChiTietHoaDon> list;

    MySharedPreferences mySharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_screen);
        imgDienThoaiDG = findViewById(R.id.imgDienThoaiDG);
        img_sao_xam_1 = findViewById(R.id.img_sao_xam_1);
        img_sao_xam_2 = findViewById(R.id.img_sao_xam_2);
        img_sao_xam_3 = findViewById(R.id.img_sao_xam_3);
        img_sao_xam_4 = findViewById(R.id.img_sao_xam_4);
        img_sao_xam_5 = findViewById(R.id.img_sao_xam_5);
        img_anh_danh_gia = findViewById(R.id.img_anh_danh_gia);
        bill_item_tvDienThoaiDG = findViewById(R.id.bill_item_tvDienThoaiDG);
        bill_item_tvMauDG = findViewById(R.id.bill_item_tvMauDG);
        bill_item_tvSoLuongDG = findViewById(R.id.bill_item_tvSoLuongDG);
        edFeedback = findViewById(R.id.edFeedback);
        bill_item_tvTongTienDG = findViewById(R.id.bill_item_tvTongTienDG);
        btnFeedback = findViewById(R.id.btnFeedback);
        mySharedPreferences = new MySharedPreferences(getApplicationContext());


        Bundle bundle = getIntent().getExtras();
        ChiTietHoaDon chiTietHoaDon = (ChiTietHoaDon) bundle.getSerializable("DanhGia");
        bill_item_tvDienThoaiDG.setText(""+ chiTietHoaDon.getMaChiTietDienThoai().getMaDienThoai().getTenDienThoai());
        bill_item_tvMauDG.setText(""+ chiTietHoaDon.getMaChiTietDienThoai().getMaMau().getTenMau());
        bill_item_tvSoLuongDG.setText("x"+ chiTietHoaDon.getSoLuong());
        bill_item_tvTongTienDG.setText("Tổng tiền: "+ chiTietHoaDon.getGiaTien());
        Picasso.get().load(chiTietHoaDon.getMaChiTietDienThoai().getMaDienThoai().getHinhAnh()).into(imgDienThoaiDG);


        img_sao_xam_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_sao_xam_1.setImageResource(R.drawable.rating_sao_vang);
                img_sao_xam_2.setImageResource(R.drawable.rating_star_xam);
                img_sao_xam_3.setImageResource(R.drawable.rating_star_xam);
                img_sao_xam_4.setImageResource(R.drawable.rating_star_xam);
                img_sao_xam_5.setImageResource(R.drawable.rating_star_xam);
            }
        });
        img_sao_xam_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_sao_xam_1.setImageResource(R.drawable.rating_sao_vang);
                img_sao_xam_2.setImageResource(R.drawable.rating_sao_vang);
                img_sao_xam_3.setImageResource(R.drawable.rating_star_xam);
                img_sao_xam_4.setImageResource(R.drawable.rating_star_xam);
                img_sao_xam_5.setImageResource(R.drawable.rating_star_xam);
            }
        });
        img_sao_xam_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_sao_xam_1.setImageResource(R.drawable.rating_sao_vang);
                img_sao_xam_2.setImageResource(R.drawable.rating_sao_vang);
                img_sao_xam_3.setImageResource(R.drawable.rating_sao_vang);
                img_sao_xam_4.setImageResource(R.drawable.rating_star_xam);
                img_sao_xam_5.setImageResource(R.drawable.rating_star_xam);
            }
        });
        img_sao_xam_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_sao_xam_1.setImageResource(R.drawable.rating_sao_vang);
                img_sao_xam_2.setImageResource(R.drawable.rating_sao_vang);
                img_sao_xam_3.setImageResource(R.drawable.rating_sao_vang);
                img_sao_xam_4.setImageResource(R.drawable.rating_sao_vang);
                img_sao_xam_5.setImageResource(R.drawable.rating_star_xam);
            }
        });
        img_sao_xam_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_sao_xam_1.setImageResource(R.drawable.rating_sao_vang);
                img_sao_xam_2.setImageResource(R.drawable.rating_sao_vang);
                img_sao_xam_3.setImageResource(R.drawable.rating_sao_vang);
                img_sao_xam_4.setImageResource(R.drawable.rating_sao_vang);
                img_sao_xam_5.setImageResource(R.drawable.rating_sao_vang);
            }
        });


    }
}