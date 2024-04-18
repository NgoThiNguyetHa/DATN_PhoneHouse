package com.example.appcuahang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;

import com.example.appcuahang.R;
import com.example.appcuahang.fragment.ChiTietDienThoaiFragment;
import com.example.appcuahang.fragment.ChiTietHoaDonFragment;

public class ChiTietHoaDonActivity extends AppCompatActivity {
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_hoa_don);
        toolbar = findViewById(R.id.main_toolBar);
        setSupportActionBar(toolbar);
        ChiTietHoaDonFragment fragment = new ChiTietHoaDonFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.chiTietHoaDon_container, fragment);
        fragmentTransaction.commit();
    }
}