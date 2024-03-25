package com.example.appkhachhang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.appkhachhang.Api.User_API;
import com.example.appkhachhang.Model.User;
import com.example.appkhachhang.untils.MySharedPreferences;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChangePassword extends AppCompatActivity {
    EditText edPassOld, edPassNew, edPassAgain;
    Button btnSave;
    FirebaseAuth mAuth;

    MySharedPreferences mySharedPreferences;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);
        mAuth = FirebaseAuth.getInstance();
        edPassOld = findViewById(R.id.edPassOld);
        edPassNew = findViewById(R.id.edPassNew);
        edPassAgain = findViewById(R.id.edPassAgain);
        btnSave = findViewById(R.id.btnSave);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        Log.e("TAG", "onCreate: " + user.getEmail() );
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
                    edPassNew.setError("Nhập đủ 6 kí tự");
                } else if (againPassStr.length() < 6) {
                    edPassAgain.setError("Nhập đủ 6 kí tự");
                } else {
                    updatePassword(oldPassStr, newPassStr);

                }


            }

            private void updatePassword(String oldPassStr, String newPassStr) {
                mySharedPreferences = new MySharedPreferences(getApplicationContext());
                FirebaseAuth.getInstance().fetchSignInMethodsForEmail(mySharedPreferences.getEmail())
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                // Kiểm tra xem email có tồn tại hay không
                                if (task.getResult().getSignInMethods().size() > 0) {
                                    // Email tồn tại, tiến hành lấy thông tin người dùng
                                    FirebaseAuth.getInstance().createUserWithEmailAndPassword(mySharedPreferences.getEmail(), "temporaryPassword")
                                            .addOnCompleteListener(task1 -> {
                                                if (task1.isSuccessful()) {
                                                    FirebaseUser user = task1.getResult().getUser();
                                                    Log.e("1", "updatePassword: " + user);

                                                    // Thực hiện các thao tác cần thiết với thông tin người dùng
                                                }
                                            });
                                } else {
                                    Log.e("TAG", "Email không tồn tại trong hệ thống: " );
                                    // Email không tồn tại trong hệ thống
                                }
                            } else {
                                Log.e("TAG", "Đã xảy ra lỗi khi kiểm tra email: " );
                                // Đã xảy ra lỗi khi kiểm tra email
                            }
                        });




//                Log.e("zzzzz", "updatePassword: " + mySharedPreferences.getEmail());
//
//                Log.e("zzzzz", "updatePassword: " + user.getEmail());
//                Log.e("zzzzz", "updatePassword: " + user);
//                AuthCredential authCredential = EmailAuthProvider.getCredential(mySharedPreferences.getEmail(), oldPassStr);
                User_API.userApi.updateMatKhau(new com.example.appkhachhang.Model.ChangePassword(mySharedPreferences.getEmail(), oldPassStr, newPassStr)).enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        if(response != null){
                            Intent intent = new Intent(ChangePassword.this, LoginScreen.class);
                            startActivity(intent);
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

}