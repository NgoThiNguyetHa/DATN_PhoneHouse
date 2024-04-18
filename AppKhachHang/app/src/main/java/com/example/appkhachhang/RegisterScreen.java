package com.example.appkhachhang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appkhachhang.Api.User_API;
import com.example.appkhachhang.Model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterScreen extends AppCompatActivity {

    EditText edEmail, edMatkhau, edHoten, edSdt;
    CheckBox checkBox;
    TextView tvSignin;
    Button btnDangky;

    List<User> list;
    FirebaseAuth mAuth;

    String error = "";
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register_screen);
        mAuth = FirebaseAuth.getInstance();
        progressBar = findViewById(R.id.progressbar);
        edEmail = findViewById(R.id.edEmaildk);
        edMatkhau = findViewById(R.id.edMatkhaudk);
        edHoten = findViewById(R.id.edHotendk);
        edSdt = findViewById(R.id.edSdtdk);

        checkBox = findViewById(R.id.checkboxdk);

        tvSignin = findViewById(R.id.tvSignindk);
        btnDangky = findViewById(R.id.btnDangky);
        list = new ArrayList<>();
        getListUser();

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
        user.setSdt(edSdt.getText().toString().trim());

        progressBar.setVisibility(View.VISIBLE);
        String email, matKhau;
        email = String.valueOf(edEmail.getText());
        matKhau = String.valueOf(edMatkhau.getText());
        mAuth.createUserWithEmailAndPassword(email, matKhau)
                .addOnCompleteListener( new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);
//                        Log.d("zzz", matKhau);

                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information

                            Toast.makeText(RegisterScreen.this, "Authentication thanhcong.",
                                    Toast.LENGTH_SHORT).show();

                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(RegisterScreen.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            String errorMessage = task.getException().getMessage();
                            error = errorMessage;
//                            Log.d("zzz", error);
                        }
                    }

                });

        if (validate() == true ){
        User_API.userApi.addUserDK(user).enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
//                Log.d("sss", "onResponse: "+validate());

                    if(checkBox.isChecked()) {

                        if (response.body() != null) {
                            Toast.makeText(RegisterScreen.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(RegisterScreen.this, LoginScreen.class);
                            startActivity(intent);
                        }
                    }else {
                        checkBox.setError("Bạn cần chấp nhận điều khoản");
                    }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
//                Log.e("error", "onFailure: "+ t.getMessage());
            }
        });
    }
    }

    boolean validate(){
        String email = edEmail.getText().toString().trim();
        String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
        if(email.isEmpty()
                ||edMatkhau.getText().toString().isEmpty()
                ||edHoten.getText().toString().isEmpty()
                ||edSdt.getText().toString().isEmpty() ){
            edEmail.setError("Bạn chưa nhập email");
            edMatkhau.setError("Bạn chưa nhập mật khẩu");
            edHoten.setError("Bạn chưa nhập họ tên");
            edSdt.setError("Bạn chưa nhập số điện thoại");
            return false;
        }
        else if(!email.matches(emailPattern)){
            edEmail.setError("Email không hợp lệ");
            return false;
        }
//        for (User user: list) {
//            if (email.equals(user.getEmail()) ){
//                for (int i = 0; i < list.size(); i++) {
//                    if (!email.equals(list.get(i).getEmail())){
//                        edEmail.setError("Email đã tồn tại");
//                        return false;
//                    }
//                }
//
//            }
//        }

         if (!error.isEmpty()) {
            edEmail.setError(error);
//             Log.d("zzzz1234", error);
            return false;
        }
        if(edMatkhau.getText().toString().length()<6){
            edMatkhau.setError("Mật khẩu tối thiểu 6 kí tự");
            return false;
        }
        if (edSdt.getText().toString().length() !=10){
            edSdt.setError("Số điện thoại chưa đúng");
            return false;
        }
        return true;
    }

    private void handleErrorResponse(Response<User> response) {
        try {
            String errorBody = response.errorBody().string();
//            Log.e("ERROR_RESPONSE", errorBody);
            // Display error message to the user
            Toast.makeText(RegisterScreen.this, "Lỗi từ server: " + errorBody, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
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
//                Toast.makeText(LoginScreen.this, "Call API error", Toast.LENGTH_SHORT).show();
            }
        });
    }
}