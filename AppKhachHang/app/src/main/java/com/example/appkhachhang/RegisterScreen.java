package com.example.appkhachhang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appkhachhang.Api.User_API;
import com.example.appkhachhang.Model.User;
import com.google.android.material.textfield.TextInputLayout;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterScreen extends AppCompatActivity {

    EditText edEmail, edMatkhau, edHoten, edSđt;
    CheckBox checkBox;
    TextView tvSignin;
    Button btnDangky;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);

        edEmail = findViewById(R.id.edEmaildk);
        edMatkhau = findViewById(R.id.edMatkhaudk);
        edHoten = findViewById(R.id.edHotendk);
        edSđt = findViewById(R.id.edSdtdk);

        checkBox = findViewById(R.id.checkboxdk);

        tvSignin = findViewById(R.id.tvSignindk);
        btnDangky = findViewById(R.id.btnDangky);

        btnDangky.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addUser();
            }
        });
        tvSignin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(RegisterScreen.this, LoginScreen.class);
                startActivity(intent);
            }
        });
    }

    private void addUser() {

        User user = new User();
        user.setEmail(edEmail.getText().toString().trim());
        user.setUsername(edHoten.getText().toString().trim());
        user.setPassword(edMatkhau.getText().toString().trim());
        user.setSdt(edSđt.getText().toString().trim());
        User_API.userApi.addUserDK(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if (validate() == true && checkBox.isChecked()){
                    if (response.body() != null){
                        Toast.makeText(RegisterScreen.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.e("error", "onFailure: "+ t.getMessage());
            }
        });
    }
    boolean validate(){
        String email = edEmail.getText().toString();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(edEmail.getText().toString().isEmpty()
                ||edMatkhau.getText().toString().isEmpty()
                ||edHoten.getText().toString().isEmpty()
                ||edSđt.getText().toString().isEmpty() ){
            edEmail.setError("Bạn chưa nhập email");
            edMatkhau.setError("Bạn chưa nhập mật khẩu");
            edHoten.setError("Bạn chưa nhập họ tên");
            edSđt.setError("Bạn chưa nhập số điện thoại");
            return false;
        }
        else if(!email.matches(emailPattern)){
            edEmail.setError("Email không hợp lệ");
            return false;
        }
        if (edSđt.getText().toString().length() !=10){
            edSđt.setError("Số điện thoại chưa đúng");
            return false;
        }
        return true;
    }

    private void handleErrorResponse(Response<User> response) {
        try {
            String errorBody = response.errorBody().string();
            Log.e("ERROR_RESPONSE", errorBody);
            // Display error message to the user
            Toast.makeText(RegisterScreen.this, "Lỗi từ server: " + errorBody, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}