package com.example.appkhachhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Bundle;
import android.widget.FrameLayout;

import com.example.appkhachhang.Fragment.InfoOrderFragment;
import com.example.appkhachhang.Fragment.PhoneListFragment;
import com.example.appkhachhang.R;

public class DanhSachActivity extends AppCompatActivity {
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_sach);
        initView();
    }
    private void initView(){
        toolbar = findViewById(R.id.danhSach_toolBar);
        setSupportActionBar(toolbar);
        PhoneListFragment fragment = new PhoneListFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.danhSach_frameLayout, fragment);
        fragmentTransaction.commit();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(view -> onBackPressed());
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}