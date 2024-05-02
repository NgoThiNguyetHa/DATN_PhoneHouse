package com.example.appkhachhang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
        toolbar.setTitle("Thông tin người dùng");
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        toolbar.setTitleTextAppearance(this, R.style.ToolbarTitleText);
//        Drawable customBackIcon = getResources().getDrawable(R.drawable.icon_back_toolbar);
        Drawable originalDrawable = getResources().getDrawable(R.drawable.icon_back_toolbar);
        Drawable customBackIcon = resizeDrawable(originalDrawable, 24, 24);
        getSupportActionBar().setHomeAsUpIndicator(customBackIcon);


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
                User_API.userApi.editKhachHang(mySharedPreferences.getUserId(), new User(mySharedPreferences.getUserId(), diaChi, hoTen, mySharedPreferences.getPassword(), mySharedPreferences.getEmail(), sdt)).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if (validate() == true) {
                            if (response != null) {
                                User user = response.body();
                                MySharedPreferences sharedPreferences = new MySharedPreferences(getApplicationContext());
                                sharedPreferences.saveUserData(user.get_id(), user.getUsername(), user.getEmail(), user.getPassword(), user.getSdt(), user.getDiaChi());
                                Toast.makeText(InformationScreen.this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                finish();
                            }
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

    private Drawable resizeDrawable(Drawable drawable, int width, int height) {
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        return new BitmapDrawable(getResources(), resizedBitmap);
    }

    boolean validate() {
        String hoTen = edHotenHS.getText().toString().trim();
        String sdt = edSdtHS.getText().toString().trim();
         if (hoTen.isEmpty()) {
            edHotenHS.setError("Họ tên ko được bỏ trống");
            return false;
        }
        else if (!hoTen.matches("\\p{L}+")) {
            edHotenHS.setError("Họ và tên phải là chữ");
            return false;
        }  else if (!sdt.matches("^0\\d{9}$")) {
            edSdtHS.setError("Số điện thoại phải là số");
            return false;
        } else if (sdt.isEmpty()) {
            edSdtHS.setError("Số điện thoại không được bỏ trống");
            return false;
        } else if (sdt.length() < 10) {
            edSdtHS.setError("Số điện thoại không hợp lệ");
            return false;
        } else if (edDiachiHS.getText().toString().trim().isEmpty()) {
            edDiachiHS.setError("Bạn phải nhập địa chỉ");
            return false;
        }
        return true;
    }
}