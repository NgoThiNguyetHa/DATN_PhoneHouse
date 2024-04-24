package com.example.appkhachhang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.appkhachhang.Api.User_API;
import com.example.appkhachhang.Model.User;
import com.example.appkhachhang.untils.MySharedPreferences;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InformationScreen extends AppCompatActivity {
    EditText edHotenHS, edSdtHS, edDiachiHS;
    Button btnSaveHS;

    Toolbar toolbar;
    MySharedPreferences mySharedPreferences;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_information_screen);
        edHotenHS = findViewById(R.id.edHotenHS);
        edSdtHS = findViewById(R.id.edSdtHS);
        edDiachiHS = findViewById(R.id.edDiaChiHS);
        btnSaveHS = findViewById(R.id.btnSaveHS);
        toolbar = findViewById(R.id.information_toolBar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        toolbar.setTitleTextAppearance(this, R.style.ToolbarTitleText);
        toolbar.setTitle("Thông tin người dùng");



        mySharedPreferences = new MySharedPreferences(getApplicationContext());
        edHotenHS.setText(mySharedPreferences.getUserName());
        edSdtHS.setText(mySharedPreferences.getPhone());
        edDiachiHS.setText(mySharedPreferences.getAddress());



        btnSaveHS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               String hoTen = edHotenHS.getText().toString().trim();
               String sdt = edSdtHS.getText().toString().trim();
               String diaChi = edDiachiHS.getText().toString().trim();
                User_API.userApi.editKhachHang(mySharedPreferences.getUserId(), new User(mySharedPreferences.getUserId(),diaChi,hoTen,mySharedPreferences.getPassword(),mySharedPreferences.getEmail(),sdt)).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response!= null){
                            User user = response.body();
                            MySharedPreferences sharedPreferences = new MySharedPreferences(getApplicationContext());
                            sharedPreferences.saveUserData( user.get_id(), user.getUsername(), user.getEmail(), user.getPassword(), user.getSdt() , user.getDiaChi());
                            Toast.makeText(InformationScreen.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });

            }
        });
    }
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}