package com.example.appcuahang.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.appcuahang.MainActivity;
import com.example.appcuahang.R;
import com.example.appcuahang.api.ApiRetrofit;
import com.example.appcuahang.api.ApiService;
import com.example.appcuahang.databinding.ActivityManHinhDangNhapBinding;
import com.example.appcuahang.model.LoginResponse;
import com.example.appcuahang.model.Store;
import com.example.appcuahang.untils.MySharedPreferences;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ManHinhDangNhap extends AppCompatActivity {
    ActivityManHinhDangNhapBinding binding;
    MySharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityManHinhDangNhapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loginUser();
    }

    private void loginUser() {
        sharedPreferences = new MySharedPreferences(ManHinhDangNhap.this);
        binding.btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.edTenDangNhap.getText().toString().trim();
                String password = binding.edMatKhau.getText().toString().trim();
                ApiService apiService = ApiRetrofit.getApiService();
                Call<LoginResponse> call = apiService.DangNhap(email,password);

                call.enqueue(new Callback<LoginResponse>() {
                    @Override
                    public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                        if (response.isSuccessful()) {
                            LoginResponse loginResponse = response.body();
                            if (loginResponse != null && loginResponse.getCuaHang() != null) {
                                String cuaHangId = loginResponse.getCuaHang().get_id();
                                String userName = loginResponse.getCuaHang().getUsername();
                                String email = loginResponse.getCuaHang().getEmail();
                                String passWord = loginResponse.getCuaHang().getPassword();
                                String phone = loginResponse.getCuaHang().getSdt();
                                String address = loginResponse.getCuaHang().getDiaChi();
                                sharedPreferences.saveUserData(cuaHangId,userName,email,passWord,phone,address);
                                Intent intent = new Intent(ManHinhDangNhap.this, MainActivity.class);
                                startActivity(intent);
                                finish();
                            } else{
                                Toast.makeText(ManHinhDangNhap.this, ""+loginResponse.getSuccessMessage(), Toast.LENGTH_SHORT).show();
                            }
                        } else {
                            Toast.makeText(ManHinhDangNhap.this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<LoginResponse> call, Throwable t) {
                        // Handle the case where the network request fails
                        Toast.makeText(ManHinhDangNhap.this, ""+t.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.e("err",t.getMessage());
                    }
                });
            }
        });
    }
}