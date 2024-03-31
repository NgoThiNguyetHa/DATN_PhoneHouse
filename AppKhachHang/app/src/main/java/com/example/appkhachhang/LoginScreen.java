package com.example.appkhachhang;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appkhachhang.Api.User_API;
import com.example.appkhachhang.Model.User;
import com.example.appkhachhang.untils.MySharedPreferences;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginScreen extends AppCompatActivity {
    TextView txtForgotPassword, txtNonAccount;
    EditText edEmail, edPassword;
    List<User> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
        txtForgotPassword = findViewById(R.id.txtForgotPassword);
        txtNonAccount = findViewById(R.id.txtDangky);

        txtNonAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginScreen.this, RegisterScreen.class);
                startActivity(intent);
            }
        });
        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(LoginScreen.this);
                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.dialog_forgotpassword, null);
                builder.setView(view);
                builder.create().show();
            }
        });
        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);
        list = new ArrayList<>();
        getListUser();
        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickLogin();
            }
        });
        findViewById(R.id.btnLoginGoogle).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });
    }


    private void clickLogin() {
        String Email = edEmail.getText().toString().trim();
        String Password = edPassword.getText().toString().trim();
        if (list == null || list.isEmpty()){
            return;
        }
        for (User user: list) {
            if (Email.equals(user.getEmail()) && Password.equals(user.getPassword())){
                for (int i = 0; i < list.size(); i++) {
                    if (Email.equals(list.get(i).getEmail())){
                        Intent intent = new Intent(LoginScreen.this, MainActivity.class);
                        SharedPreferences.Editor editor = getSharedPreferences("user_info", MODE_PRIVATE).edit();
                        editor.putString("idKhachHang", user.get_id());
                        editor.apply();
                        startActivity(intent);
                    }
                }
                MySharedPreferences sharedPreferences = new MySharedPreferences(getApplicationContext());
                sharedPreferences.saveUserData(user.get_id() , user.getUsername(), user.getEmail(), user.getPassword(), user.getSdt() , user.getDiaChi());
                Intent intent = new Intent(LoginScreen.this, MainActivity.class);
                startActivity(intent);                break;
            } else if (Email.isEmpty()||Password.isEmpty()){
                edEmail.setError("Email invalid");
                edPassword.setError("Password invalid");
            }
        }
    }
    void getListUser(){
        User_API.userApi.getAllUser().enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                list = response.body();
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(LoginScreen.this, "Call API error", Toast.LENGTH_SHORT).show();
            }
        });
    }


}