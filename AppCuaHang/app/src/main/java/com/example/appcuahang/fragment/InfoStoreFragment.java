package com.example.appcuahang.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appcuahang.R;
import com.example.appcuahang.api.ApiRetrofit;
import com.example.appcuahang.api.ApiService;
import com.example.appcuahang.databinding.FragmentHomeBinding;
import com.example.appcuahang.databinding.FragmentInfoStoreBinding;
import com.example.appcuahang.model.Brand;
import com.example.appcuahang.model.Store;
import com.example.appcuahang.untils.MySharedPreferences;
import com.google.android.material.textfield.TextInputLayout;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class InfoStoreFragment extends Fragment {


    FragmentInfoStoreBinding binding;

    MySharedPreferences mySharedPreferences;
    EditText edUsername ;
    EditText edAddress ;
    EditText edEmail ;
    EditText edPhone ;
    ProgressDialog progressDialog;
    TextInputLayout textMkhauMoi , textNhapLaiMkhau , textMkhauCu;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentInfoStoreBinding.inflate(getLayoutInflater(), container, false);
        ((Activity) getContext()).setTitle("Thông Tin Cá Nhân");
        initVariable();
        action();
        return binding.getRoot();
    }

    private void initVariable() {
        mySharedPreferences = new MySharedPreferences(getContext());
        binding.infoTvUsername.setText("" + mySharedPreferences.getUserName());
        binding.infoTvPhone.setText("" + mySharedPreferences.getPhone());
        binding.infoTvEmail.setText("" + mySharedPreferences.getEmail());
        binding.infoTvAddress.setText("" + mySharedPreferences.getAddress());
    }

    private void action() {
        binding.cvUpdateInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogChangeInfo();
            }
        });

        binding.cvUpdatePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogChangePassword();
            }
        });
    }

    private void dialogChangeInfo() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_change_info, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);

        edUsername = view.findViewById(R.id.dl_store_edUsername);
        edAddress = view.findViewById(R.id.dl_store_edAddress);
        edEmail = view.findViewById(R.id.dl_store_edEmail);
        edPhone = view.findViewById(R.id.dl_store_edPhone);

        Button btnSave = view.findViewById(R.id.dl_store_btnSave);
        TextView tvTitle = view.findViewById(R.id.dl_store_tvTitle);
        ImageView imgView = view.findViewById(R.id.dl_store_imageView);

        tvTitle.setText("Cập Nhật Thông Tin");
        edUsername.setText("" + mySharedPreferences.getUserName());
        edAddress.setText("" + mySharedPreferences.getAddress());
        edEmail.setText("" + mySharedPreferences.getEmail());
        edPhone.setText("" + mySharedPreferences.getPhone());
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = mySharedPreferences.getUserId();
                String password = mySharedPreferences.getPassword();
                if (validate()){
                    progressDialog = new ProgressDialog(getContext());
                    progressDialog.setMessage("Vui Lòng Chờ...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
//                    mySharedPreferences.clearUserData();
                    String username = edUsername.getText().toString().trim();
                    String address = edAddress.getText().toString().trim();
                    String email = edEmail.getText().toString().trim();
                    String phone = edPhone.getText().toString().trim();
                    ApiService apiService = ApiRetrofit.getApiService();
                    Call<Store> call = apiService.putCuaHang(mySharedPreferences.getUserId(), new Store(address,username,phone,email));
                    call.enqueue(new Callback<Store>() {
                        @Override
                        public void onResponse(Call<Store> call, Response<Store> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                mySharedPreferences.saveUserData(id, username,email,password, phone,address);
                                replaceFragment(new InfoStoreFragment());
                                progressDialog.dismiss();
                            } else {
                                Toast.makeText(getContext(), "Cập nhập thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Store> call, Throwable t) {
                            Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }

            }
        });

        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void dialogChangePassword() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_change_password, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);

        EditText edOldPass = view.findViewById(R.id.dl_store_edOldPass);
        EditText edNewPass = view.findViewById(R.id.dl_store_edNewPass);
        EditText edConfirmPass = view.findViewById(R.id.dl_store_edConfirmPass);
        textMkhauMoi = view.findViewById(R.id.textMkhauMoi);
        textNhapLaiMkhau = view.findViewById(R.id.textNhapLaiMkhau);
        textMkhauCu = view.findViewById(R.id.textMkhauCu);
        Button btnSave = view.findViewById(R.id.dl_store_btnSave);
        TextView tvTitle = view.findViewById(R.id.dl_store_tvTitle);
        ImageView imgView = view.findViewById(R.id.dl_store_imageView);

        tvTitle.setText("Đổi Mật Khẩu");
//        edNewPass.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (s.length() < 6){
//                    textMkhauMoi.setError("Yêu cầu mật khẩu tối thiểu 6 kí tự!!");
//                    textMkhauMoi.setHelperText("");
//                }else{
//                    textMkhauMoi.setHelperText("Mật khẩu hợp lệ");
//                    textMkhauMoi.setError("");
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
//        edConfirmPass.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (s.length() < 6){
//                    textNhapLaiMkhau.setError("Yêu cầu mật khẩu tối thiểu 6 kí tự!!");
//                    textNhapLaiMkhau.setHelperText("");
//                }else{
//                    textNhapLaiMkhau.setHelperText("Mật khẩu hợp lệ");
//                    textNhapLaiMkhau.setError("");
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newPass = edNewPass.getText().toString().trim();
                String confirmPass = edConfirmPass.getText().toString().trim();
                String oldPass = edOldPass.getText().toString().trim();
                String passwordPattern = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$";
                if (oldPass.isEmpty()){
                    textMkhauCu.setError("Yêu cầu nhập mật khẩu cũ");
                    textMkhauMoi.setError("");
                    textNhapLaiMkhau.setError("");
                    return;
                }
                else if (!oldPass.equals(mySharedPreferences.getPassword())) {
                    textMkhauCu.setError("Mật khẩu cũ không đúng");
                    textMkhauMoi.setError("");
                    textNhapLaiMkhau.setError("");
                    return;
                }else if (newPass.isEmpty()) {
                    textMkhauMoi.setError("Yêu cầu nhập mật khẩu mới!!");
                    textMkhauCu.setError("");
                    textNhapLaiMkhau.setError("");
                    return;
                }else if (newPass.length() < 6) {
                    textMkhauMoi.setError("Yêu cầu nhập mật khẩu tối thiểu 6 kí tự!!");
                    textMkhauCu.setError("");
                    textNhapLaiMkhau.setError("");
                    return;
                }else if (!newPass.matches(passwordPattern)) {
                    textMkhauMoi.setError("Mật khẩu có ít nhất 1 chữ hoa, 1 chữ thường, 1 số và 1 ký tự đặc biệt!!");
                    textMkhauCu.setError("");
                    textNhapLaiMkhau.setError("");
                    return;
                }
                else if (!newPass.equals(confirmPass)) {
                    textNhapLaiMkhau.setError("Mật khẩu không trùng khớp");
                    textMkhauCu.setError("");
                    textMkhauMoi.setError("");;
                    return;
                } else {
                    progressDialog = new ProgressDialog(getContext());
                    progressDialog.setMessage("Vui Lòng Chờ...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                    ApiService apiService = ApiRetrofit.getApiService();
                    Call<Store> call = apiService.putCuaHang(mySharedPreferences.getUserId(), new Store(newPass,mySharedPreferences.getEmail()));
                    call.enqueue(new Callback<Store>() {
                        @Override
                        public void onResponse(Call<Store> call, Response<Store> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(), "Đổi mật khẩu thành công", Toast.LENGTH_SHORT).show();
                                dialog.dismiss();
                                progressDialog.dismiss();
                            } else {
                                Toast.makeText(getContext(), "Đổi mật khẩu thất bại", Toast.LENGTH_SHORT).show();
                            }
                        }

                        @Override
                        public void onFailure(Call<Store> call, Throwable t) {
                            Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private boolean validate(){
        String phonePattern = "^0\\d{9}$";
        if (edUsername.getText().toString().trim().isEmpty()){
            edUsername.setError("Username không được để trống!!");
            return false;
        }else if (edEmail.getText().toString().trim().isEmpty()){
            edEmail.setError("Email không được để trống!!");
            return false;
        } else if (!Patterns.EMAIL_ADDRESS.matcher(edEmail.getText().toString().trim()).matches()) {
            edEmail.setError("Email không đúng định dạng!!");
            return false;
        } else if (edAddress.getText().toString().trim().isEmpty()){
            edAddress.setError("Địa chỉ không được để trống!!");
            return false;
        }else if (edPhone.getText().toString().trim().isEmpty()){
            edPhone.setError("Số điện thoại không được để trống!!");
            return false;
        }else if (!edPhone.getText().toString().trim().matches(phonePattern)){
            edPhone.setError("Số điện thoại không hợp lệ!!");
            return false;
        }
        return true;
    }

    private void replaceFragment(Fragment fragment){
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.main_frame,fragment);
        transaction.commit();
    }

}