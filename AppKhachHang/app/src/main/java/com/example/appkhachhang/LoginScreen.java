package com.example.appkhachhang;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appkhachhang.Api.User_API;
import com.example.appkhachhang.Model.User;
import com.example.appkhachhang.untils.MySharedPreferences;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginScreen extends AppCompatActivity {
    TextView txtForgotPassword, txtNonAccount;
    EditText edEmail, edPassword;
    TextInputLayout edLayoutEmail, edLayoutPassword;
    List<User> list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_screen);
//        txtForgotPassword = findViewById(R.id.txtForgotPassword);
        txtNonAccount = findViewById(R.id.txtDangky);

        txtNonAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginScreen.this, RegisterScreen.class);
                startActivity(intent);
            }
        });
//        txtForgotPassword.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                AlertDialog.Builder builder = new AlertDialog.Builder(LoginScreen.this);
//                LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//                view = inflater.inflate(R.layout.dialog_forgotpassword, null);
//                builder.setView(view);
//                builder.create().show();
//            }
//        });
        edEmail = findViewById(R.id.edEmail);
        edPassword = findViewById(R.id.edPassword);
        edLayoutEmail = findViewById(R.id.ed_layoutemail);
        edLayoutPassword = findViewById(R.id.edLayoutPassword);
        list = new ArrayList<>();
        getListUser();
        findViewById(R.id.btnLogin).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickLogin();
            }
        });
//        findViewById(R.id.btnLoginGoogle).setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//            }
//        });
        fullScreen();
    }


    private void clickLogin() {
        String Email = edEmail.getText().toString().trim();
        String Password = edPassword.getText().toString().trim();
        if (list == null || list.isEmpty()){
            return;
        }
        for (User user: list) {
            String emailPattern = "^[a-zA-Z][a-zA-Z0-9\\-_]+@[a-zA-Z]+(\\.[a-zA-Z]+){1,3}$";
            edEmail.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (i1>i2){
                        edLayoutEmail.setHelperText("");
                        edLayoutEmail.setError("");
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            edPassword.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    if (i1>i2){
                        edLayoutPassword.setHelperText("");
                        edLayoutPassword.setError("");
                    }
                }

                @Override
                public void afterTextChanged(Editable editable) {

                }
            });
            if (Email.isEmpty()){
                edLayoutEmail.setError("Email không được để trống");
                edLayoutEmail.setHelperText("");
                return;
            } else if (Password.isEmpty()) {
                edLayoutPassword.setError("Password không được để trống");
                return;
            }  else if (!Email.matches(emailPattern)) {
                edLayoutEmail.setError("Email không hợp lệ");
                edLayoutEmail.setHelperText("");
                return;
            } else if (!isStrongPassword(Password)) {
                edLayoutPassword.setError("Sai format, vui lòng nhập lại");
                edLayoutPassword.setHelperText("");
                return;
            } else if (Email.equals(user.getEmail())&&!Password.equals(user.getPassword())) {
                edLayoutPassword.setError("Password không đúng");
                edLayoutPassword.setHelperText("");
                return;
            } else if (!isValidEmail(Email)) {
                edLayoutEmail.setError("Email không hợp lệ");
                edLayoutEmail.setHelperText("");
                return;
            } else if (Email.equals(user.getEmail()) && Password.equals(user.getPassword())){
                MySharedPreferences sharedPreferences = new MySharedPreferences(getApplicationContext());
                sharedPreferences.saveUserData(user.get_id() , user.getUsername(), user.getEmail(), user.getPassword(), user.getSdt() , user.getDiaChi());
                Intent intent = new Intent(LoginScreen.this, MainActivity.class);
//                SharedPreferences.Editor editor = getSharedPreferences("user_info", MODE_PRIVATE).edit();
//                editor.putString("idKhachHang", user.get_id());
//                Gson gson = new Gson();
//                String json = gson.toJson(user);
//                editor.putString("user", json);
//                editor.apply();
                startActivity(intent);
                break;
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
    private boolean isStrongPassword(String password) {
        // Kiểm tra xem mật khẩu có ít nhất 1 chữ hoa, 1 chữ thường, 1 số và 1 ký tự đặc biệt không
        String passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$";
        return password.matches(passwordPattern);
    }

    private void fullScreen(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R){
            WindowInsetsController controller = getWindow().getInsetsController();
            if (controller != null){
                controller.hide(WindowInsets.Type.statusBars());
            }
        }else{
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        }
    }
}