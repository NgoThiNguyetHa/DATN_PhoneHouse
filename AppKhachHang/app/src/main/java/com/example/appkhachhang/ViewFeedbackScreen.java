package com.example.appkhachhang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.appkhachhang.Adapter.ViewFeedbackAdapter;
import com.example.appkhachhang.Api.ApiRetrofit;
import com.example.appkhachhang.Api.FeedbackAPI;
import com.example.appkhachhang.Interface.OnItemClickListenerDanhGia;
import com.example.appkhachhang.Model.ChiTietHoaDon;
import com.example.appkhachhang.Model.DanhGia;
import com.example.appkhachhang.untils.MySharedPreferences;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewFeedbackScreen extends AppCompatActivity {
    RecyclerView rc_Feedback;
    LinearLayoutManager manager;
    MySharedPreferences mySharedPreferences;
    ViewFeedbackAdapter adapter;

    List<DanhGia> list = new ArrayList<>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_feedback_screen);
        rc_Feedback = findViewById(R.id.rc_ViewFeedback);

        getData();
    }

    private void getData() {
        list = new ArrayList<>();
        manager = new LinearLayoutManager(getApplicationContext());
        rc_Feedback.setLayoutManager(manager);

        mySharedPreferences = new MySharedPreferences(getApplicationContext());
        getDanhGiaTheoKhachHang(mySharedPreferences.getUserId());
    }

    private void getDanhGiaTheoKhachHang(String userId) {
        FeedbackAPI feedbackAPI = ApiRetrofit.getFeedbackAPI();
        Call<List<DanhGia>> call = feedbackAPI.getDanhGia(userId);
        call.enqueue(new Callback<List<DanhGia>>() {
            @Override
            public void onResponse(Call<List<DanhGia>> call, Response<List<DanhGia>> response) {
                if (response.isSuccessful()){
                    List<DanhGia> danhGia = response.body();
                    adapter = new ViewFeedbackAdapter(getApplicationContext(), danhGia, new OnItemClickListenerDanhGia() {
                        @Override
                        public void onItemClickDanhGia(ChiTietHoaDon chiTietHoaDon) {

                        }
                    });
                    rc_Feedback.setAdapter(adapter);
                    Log.e("zzzz", "onResponse: " + response );
                }
            }

            @Override
            public void onFailure(Call<List<DanhGia>> call, Throwable t) {

            }
        });
    }
}