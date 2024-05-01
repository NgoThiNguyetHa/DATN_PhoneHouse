package com.example.appkhachhang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.appkhachhang.Api.User_API;
import com.example.appkhachhang.Fragment.UserFragment;
import com.example.appkhachhang.Model.User;
import com.example.appkhachhang.untils.MySharedPreferences;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends AppCompatActivity {
    EditText edPassOld, edPassNew, edPassAgain;
    Button btnSave, btnCancle;
    FirebaseAuth mAuth;
    Toolbar toolbar;

    MySharedPreferences mySharedPreferences;
    ProgressBar progressBar;

    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        mAuth = FirebaseAuth.getInstance();
        edPassOld = findViewById(R.id.edPassOld);
        edPassNew = findViewById(R.id.edPassNew);
        edPassAgain = findViewById(R.id.edPassAgain);
        btnSave = findViewById(R.id.btnSave);
        btnCancle = findViewById(R.id.btnCancle);
        toolbar = findViewById(R.id.changePass_toolBar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        toolbar.setTitleTextAppearance(this, R.style.ToolbarTitleText);
//        Drawable customBackIcon = getResources().getDrawable(R.drawable.icon_back_toolbar);
        Drawable originalDrawable = getResources().getDrawable(R.drawable.icon_back_toolbar);
        Drawable customBackIcon = resizeDrawable(originalDrawable, 24, 24);
        getSupportActionBar().setHomeAsUpIndicator(customBackIcon);

        toolbar.setTitle("Đổi mật khẩu");
        setSupportActionBar(toolbar);

        mySharedPreferences = new MySharedPreferences(getApplicationContext());
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        Log.e("TAG", "onCreate: " + user.getEmail() );

        btnCancle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                edPassAgain.setText("");
                edPassNew.setText("");
                edPassOld.setText("");
                finish();
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String oldPassStr = edPassOld.getText().toString();
                String newPassStr = edPassNew.getText().toString();
                String againPassStr = edPassAgain.getText().toString();
                if (oldPassStr.isEmpty()) {
                    edPassOld.setError("Nhập đủ thông tin");
                } else if (newPassStr.isEmpty()) {
                    edPassNew.setError("Nhập đủ thông tin");
                } else if (againPassStr.isEmpty()) {
                    edPassAgain.setError("Nhập đủ thông tin");
                } else if (newPassStr.length() < 6) {
                    edPassNew.setError("Mật khẩu mới tối thiểu 6 kí tự");
                } else if (!edPassOld.getText().toString().trim().equals(mySharedPreferences.getPassword()+"")) {
                    edPassOld.setError("Sai mật khẩu cũ");
                    Log.e("zzzzz", "onClick: "+ mySharedPreferences.getPassword() );
                } else if (!isStrongPassword(edPassNew.getText().toString().trim())) {
                    edPassNew.setError("Mật khẩu mới phải có chữ cái viết hoa và có kí tự đặc biệt");
                } else if (!edPassAgain.getText().toString().trim().equals(edPassNew.getText().toString().trim())) {
                    edPassAgain.setError("Mật khẩu nhập lại không trùng khớp");
                }

                else {
                    updatePassword(oldPassStr, newPassStr);
                    }


            }

            private void updatePassword(String oldPassStr, String newPassStr) {
                mySharedPreferences = new MySharedPreferences(getApplicationContext());
//                FirebaseAuth.getInstance().fetchSignInMethodsForEmail(mySharedPreferences.getEmail())
//                        .addOnCompleteListener(task -> {
//                            if (task.isSuccessful()) {
//                                // Kiểm tra xem email có tồn tại hay không
//                                if (task.getResult().getSignInMethods().size() > 0) {
//                                    // Email tồn tại, tiến hành lấy thông tin người dùng
//                                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(mySharedPreferences.getEmail(), "temporaryPassword")
//                                            .addOnCompleteListener(task1 -> {
//                                                if (task1.isSuccessful()) {
//                                                    FirebaseUser user = task1.getResult().getUser();
//                                                    Log.e("1", "updatePassword: " + user);
//
//                                                    // Thực hiện các thao tác cần thiết với thông tin người dùng
//                                                }
//                                            });
//                                } else {
//                                    Log.e("TAG", "Email không tồn tại trong hệ thống: " );
//                                    // Email không tồn tại trong hệ thống
//                                }
//                            } else {
//                                Log.e("TAG", "Đã xảy ra lỗi khi kiểm tra email: " );
//                                // Đã xảy ra lỗi khi kiểm tra email
//                            }
//                        });




//                Log.e("zzzzz", "updatePassword: " + mySharedPreferences.getEmail());
//
//                Log.e("zzzzz", "updatePassword: " + user.getEmail());
//                Log.e("zzzzz", "updatePassword: " + user);
//                AuthCredential authCredential = EmailAuthProvider.getCredential(mySharedPreferences.getEmail(), oldPassStr);
                User_API.userApi.updateMatKhau(new com.example.appkhachhang.Model.ChangePassword(mySharedPreferences.getEmail(), oldPassStr, newPassStr)).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response != null){
                            Toast.makeText(ChangePassword.this, "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                            MySharedPreferences sharedPreferences = new MySharedPreferences(getApplicationContext());
                            sharedPreferences.saveUserData(sharedPreferences.getUserId() , sharedPreferences.getUserName(), sharedPreferences.getEmail(), newPassStr, sharedPreferences.getPhone() , sharedPreferences.getAddress());
                            finish();
//                            Intent intent = new Intent(ChangePassword.this, UserFragment.class);
//                            startActivity(intent);

                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {

                    }
                });
//
//
//                user.reauthenticate(authCredential).addOnSuccessListener(new OnSuccessListener<Void>() {
//                    @Override
//                    public void onSuccess(Void unused) {
//                        user.updatePassword(newPassStr).addOnCompleteListener(new OnCompleteListener<Void>() {
//                            @Override
//                            public void onComplete(@NonNull Task<Void> task) {
//                                Toast.makeText(ChangePassword.this, "password succes", Toast.LENGTH_SHORT).show();
//                            }
//                        }).addOnFailureListener(new OnFailureListener() {
//                            @Override
//                            public void onFailure(@NonNull Exception e) {
//                                Toast.makeText(ChangePassword.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                                Log.e("zzzzz", "onFailure: " + e.getMessage());
//                            }
//                        });
//                    }
//                }).addOnFailureListener(new OnFailureListener() {
//                    @Override
//                    public void onFailure(@NonNull Exception e) {
//                        Toast.makeText(ChangePassword.this, e.getMessage(), Toast.LENGTH_SHORT).show();
//                        Log.e("zzzzz", "onFailure: " + e.getMessage());
//
//                    }
//                });
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


    private boolean isStrongPassword(String password) {
        // Kiểm tra xem mật khẩu có null hoặc độ dài không đủ không
        if (password == null || password.length() < 6) {
            return false;
        }

        // Kiểm tra xem mật khẩu có ít nhất 1 chữ hoa, 1 chữ thường, 1 số và 1 ký tự đặc biệt không
        String passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$";
        return password.matches(passwordPattern);
    }
}