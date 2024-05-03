package com.example.appkhachhang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.appkhachhang.Adapter.HistoryBuyAdapter;
import com.example.appkhachhang.Api.ApiRetrofit;
import com.example.appkhachhang.Api.ApiService;
import com.example.appkhachhang.Interface.OnItemClickListenerDanhGia;
import com.example.appkhachhang.Model.ChiTietHoaDon;
import com.example.appkhachhang.untils.MySharedPreferences;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HistoryBuy extends AppCompatActivity {
    RecyclerView rc_HistoryBuy;
    LinearLayoutManager manager;
    MySharedPreferences mySharedPreferences;

    HistoryBuyAdapter adapter;
    List<ChiTietHoaDon> list = new ArrayList<>();
    Toolbar toolbar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_buy);
        rc_HistoryBuy = findViewById(R.id.rc_HistoryBuy);
        toolbar = findViewById(R.id.historyBuy_toolBar);
        toolbar.setTitle("Lịch sử mua hàng");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        toolbar.setTitleTextAppearance(this, R.style.ToolbarTitleText);
//        Drawable customBackIcon = getResources().getDrawable(R.drawable.icon_back_toolbar);
        Drawable originalDrawable = getResources().getDrawable(R.drawable.icon_back_toolbar);
        Drawable customBackIcon = resizeDrawable(originalDrawable, 24, 24);
        getSupportActionBar().setHomeAsUpIndicator(customBackIcon);

        getData();



    }

    public void getCTHDTheoLichSu(String maKhachHang) {
        ApiService apiService = ApiRetrofit.getApiService();
        Call<List<ChiTietHoaDon>> call = apiService.getCTHDTheoLichSu(maKhachHang);
        call.enqueue(new Callback<List<ChiTietHoaDon>>() {
            @Override
            public void onResponse(Call<List<ChiTietHoaDon>> call, Response<List<ChiTietHoaDon>> response) {
                if (response.isSuccessful()) {
                    List<ChiTietHoaDon> chiTietHoaDonList = response.body();
                    adapter = new HistoryBuyAdapter(getApplicationContext(), chiTietHoaDonList, new OnItemClickListenerDanhGia() {
                        @Override
                        public void onItemClickDanhGia(ChiTietHoaDon chiTietHoaDon) {
                            Bundle bundle = new Bundle();
                            bundle.putSerializable("DanhGia", chiTietHoaDon);
                            Intent intent = new Intent(HistoryBuy.this, FeedbackScreen.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });
                    rc_HistoryBuy.setAdapter(adapter);
//                    Log.e("zzzzz", "onResponse: " + response.body());
                } else {
                    Toast.makeText(HistoryBuy.this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ChiTietHoaDon>> call, Throwable t) {

            }
        });
    }

    private void getData() {
        list = new ArrayList<>();
        manager = new LinearLayoutManager(getApplicationContext());
        rc_HistoryBuy.setLayoutManager(manager);

        mySharedPreferences = new MySharedPreferences(getApplicationContext());
        getCTHDTheoLichSu(mySharedPreferences.getUserId());
    }
    private Drawable resizeDrawable(Drawable drawable, int width, int height) {
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        return new BitmapDrawable(getResources(), resizedBitmap);
    }
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}