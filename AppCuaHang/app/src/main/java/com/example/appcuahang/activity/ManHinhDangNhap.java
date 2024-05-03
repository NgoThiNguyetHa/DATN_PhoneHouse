package com.example.appcuahang.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Patterns;
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
import com.google.android.material.textfield.TextInputLayout;

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

    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityManHinhDangNhapBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        loginUser();
    }

    private void loginUser() {
        sharedPreferences = new MySharedPreferences(ManHinhDangNhap.this);
        binding.edTenDangNhap.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (!Patterns.EMAIL_ADDRESS.matcher(s).matches()){
                    binding.textTenDangNhap.setError("Yêu cầu nhập đúng định dạng email!!");
                    binding.textTenDangNhap.setHelperText("");
                }else{
                    binding.textTenDangNhap.setHelperText("Email hợp lệ");
                    binding.textTenDangNhap.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        binding.edMatKhau.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < 6){
                    binding.textMatKhau.setError("Yêu cầu mật khẩu tối thiểu 6 kí tự!!");
                    binding.textMatKhau.setHelperText("");
                }else{
                    binding.textMatKhau.setHelperText("Mật khẩu hợp lệ");
                    binding.textMatKhau.setError("");
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        binding.btnDangNhap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (binding.edTenDangNhap.getText().toString().isEmpty()){
                    binding.textTenDangNhap.setError("Yêu cầu không được để trống!!");
                    return;
                }else if (binding.edMatKhau.getText().toString().isEmpty()){
                    binding.textMatKhau.setError("Yêu cầu không được để trống!!");
                    return;
                }
                if (checkValidate()){
                    String email = binding.edTenDangNhap.getText().toString().trim();
                    String password = binding.edMatKhau.getText().toString().trim();
//                    progressDialog = new ProgressDialog(getApplicationContext());
//                    progressDialog.setMessage("Vui Lòng Chờ...");
//                    progressDialog.setCancelable(false);
//                    progressDialog.show();
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
                                Toast.makeText(ManHinhDangNhap.this, "Tài khoản hoặc mật khẩu không đúng", Toast.LENGTH_SHORT).show();
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
            }
        });
    }

//    private boolean checkValidate(){
//        String strEmail = binding.edTenDangNhap.getText().toString();
//        String strPass = binding.edMatKhau.getText().toString();
//        if (strEmail.isEmpty()){
//            binding.textTenDangNhap.setError("Yêu cầu không được để trống!!");
//            return false;
//        }else if (strPass.isEmpty()){
//            binding.textMatKhau.setError("Yêu cầu không được để trống!!");
//            return false;
//        }
//        return true;
//    }

    private boolean checkValidate() {
        if (binding.textTenDangNhap.getError() != null || binding.textMatKhau.getError() != null) {
            // Nếu có lỗi ở email hoặc mật khẩu, không thực hiện đăng nhập
            return false;
        }
        return true;
    }
}