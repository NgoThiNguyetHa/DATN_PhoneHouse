package com.example.appkhachhang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
                if(validate()== true) {
                    addUser();
                }
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
                            User_API.userApi.addUserDK(user).enqueue(new Callback<User>() {
                                @Override
                                public void onResponse(Call<User> call, Response<User> response) {
//                Log.d("sss", "onResponse: "+validate());
                                    if (response.body() != null && response.isSuccessful()) {
                                        Toast.makeText(RegisterScreen.this, "Đăng ký thành công", Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(RegisterScreen.this, LoginScreen.class);
                                        startActivity(intent);
                                    }


                                }

                                @Override
                                public void onFailure(Call<User> call, Throwable t) {
                                    Log.e("error", "onFailure: "+ t.getMessage());
                                }
                            });
                            // Sign in success, update UI with the signed-in user's information
                        } else {
                            // If sign in fails, display a message to the user.
                            edEmail.setError("Email đã tồn tại, vui lòng nhập email khác");
                        }
                    }

                });



    }


    boolean validate(){
        String Email = edEmail.getText().toString().trim();
        String Password = edMatkhau.getText().toString().trim();
        String hoTen = edHoten.getText().toString().trim() ;
        String chuoiChu = "a-zA-Z";
//        String emailPattern = "[a-zA-Z0-9._+-]+@[a-zA-Z0-9_-]+\\.+[a-z]+";
////        String emailPattern1 = "^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|([a-zA-Z0-9\\-]+\\.([a-zA-Z0-9\\-]+\\.)+[a-zA-Z]{2,}))$";
//        String emailPattern1 = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" +
//                "fpt\\.edu\\.vn$";
        String emailPattern1 =  "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.){1,2}[a-zA-Z]{2,7}$";

        if (Email.isEmpty()){
            edEmail.setError("Email không được bỏ trống");
            return false;
        } else if (Password.isEmpty()) {
            edMatkhau.setError("Mật khẩu không được bỏ trống");
            return false;
        }  else if (!Email.matches(emailPattern1)) {
            edEmail.setError("Email không hợp lệ");
            return false;
        } else if (!isStrongPassword(Password)) {
            edMatkhau.setError("Mật khẩu phải có chữ cái viết hoa, số và có kí tự đặc biệt");
            return false;
        }  else if (Password.length()<6) {
            edMatkhau.setError("Password tối thiểu 6 ký tự");

            return false;
        } else if (!isValidEmail(Email)) {
            edEmail.setError("Email không hợp lệ");
            return false;
        }else if(edHoten.getText().toString().trim().isEmpty()){
            edHoten.setError("Bạn phải nhập họ tên");
            return false;
//        }else if (!hoTen.matches("[a-zA-Z]+")){
//            edHoten.setError("Họ và tên phải là chữ");
//            return false;
        } else if (!hoTen.matches("\\p{L}+")){
            edHoten.setError("Họ và tên phải là chữ");
            return false;
        }
        else if (edMatkhau.getText().toString().trim().matches("\\p{L}+")) {
            edSdt.setError("Số điện thoại phải là số");
            return false;
        } else if(edSdt.getText().toString().trim().isEmpty()){
            edSdt.setError("Bạn phải nhập số điện thoại");
            return false;
        }else if(edSdt.getText().toString().trim().length()<1
                ||edSdt.getText().toString().trim().length()>10){
            edSdt.setError("Số điện thoại không đúng định dạng");
            return false;
        }else if(!checkBox.isChecked()){
            Toast.makeText(this, "Bạn cần đồng ý điều khoản", Toast.LENGTH_SHORT).show();
            return  false;
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

         else if (!error.isEmpty()) {
            edEmail.setError(error);
//             Log.d("zzzz1234", error);
            return false;
        }
        else if(edMatkhau.getText().toString().length()<6){
            edMatkhau.setError("Mật khẩu tối thiểu 6 kí tự");
            return false;
        }
        else if (edSdt.getText().toString().length() !=10){
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
    private boolean isStrongPassword(String password) {
        // Kiểm tra xem mật khẩu có null hoặc độ dài không đủ không
        if (password == null || password.length() < 6) {
            return false;
        }

        // Kiểm tra xem mật khẩu có ít nhất 1 chữ hoa, 1 chữ thường, 1 số và 1 ký tự đặc biệt không
        String passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$";
        return password.matches(passwordPattern);
    }
    private boolean isValidEmail(String email) {
        // Kiểm tra xem chuỗi email có null hay không
        if (email == null) {
            return false;
        }

        // Kiểm tra xem chuỗi email có ít nhất một ký tự trước ký tự '@' không
        int atIndex = email.indexOf('@');
        if (atIndex <= 0 && atIndex>=64) {
            return false;
        }

        // Lấy phần của chuỗi trước ký tự '@'
        String partBeforeAt = email.substring(0, atIndex);

        // Kiểm tra xem phần này có phù hợp với mẫu mong muốn hay không
        String emailPattern = "[a-zA-Z0-9._+-]+";
        return partBeforeAt.matches(emailPattern);
    }
}