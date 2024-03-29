package com.example.appkhachhang.Fragment;
import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.appkhachhang.AddressDelivery;
import com.example.appkhachhang.ChangePassword;
import com.example.appkhachhang.LoginScreen;
import com.example.appkhachhang.R;
import com.example.appkhachhang.untils.MySharedPreferences;
import com.example.appkhachhang.InformationScreen;

public class UserFragment extends Fragment {
    CardView btnChangePass, btnMap, btnInformation;
    TextView tvUsername, tvEmail;
    MySharedPreferences mySharedPreferences;
    Button btnDangXuat;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_user, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        btnMap = view.findViewById(R.id.btnMap);
        btnChangePass = view.findViewById(R.id.btnChangePass);
        tvEmail = view.findViewById(R.id.tvEmail);
        tvUsername = view.findViewById(R.id.tvUsername);
        btnDangXuat = view.findViewById(R.id.btnDangXuat);
        btnInformation = view.findViewById(R.id.btnInformation);

        mySharedPreferences = new MySharedPreferences(getContext());
        tvUsername.setText(mySharedPreferences.getUserName());
        tvEmail.setText(mySharedPreferences.getEmail());

        btnDangXuat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handleLogout();
            }
        });
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), AddressDelivery.class);
                startActivity(intent);
            }
        });

        btnChangePass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ChangePassword.class);
                startActivity(intent);
            }
        });
        btnInformation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), InformationScreen.class);
                startActivity(intent);
            }
        });
    }

    private void handleLogout() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_yes_no, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
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

        Button btnYes = view.findViewById(R.id.yesButton);
        Button btnNo = view.findViewById(R.id.noButton);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView tvMessage = view.findViewById(R.id.tvMessage);

        tvMessage.setText("Bạn có chắc chắn muốn đăng xuất?");

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), LoginScreen.class);
                startActivity(intent);
            }
        });

    }
}