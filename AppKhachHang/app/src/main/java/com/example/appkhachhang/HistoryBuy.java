package com.example.appkhachhang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history_buy);
        rc_HistoryBuy = findViewById(R.id.rc_HistoryBuy);

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

}