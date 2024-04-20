package com.example.appkhachhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appkhachhang.Api.ApiRetrofit;
import com.example.appkhachhang.Api.ApiService;
import com.example.appkhachhang.Fragment.DetailScreenFragment;
import com.example.appkhachhang.Fragment.PhoneListFragment;
import com.example.appkhachhang.Model.ChiTietDienThoai;
import com.example.appkhachhang.Model.ChiTietGioHang;
import com.example.appkhachhang.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.io.IOException;

import retrofit2.Response;

public class DetailScreen extends AppCompatActivity {
    Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_screen);
        initView();
    }
    private void handleErrorResponse(Response<ChiTietGioHang> response) {
        try {
            String errorBody = response.errorBody().string();
            Log.e("ERROR_RESPONSE", errorBody);
            // Display error message to the user
            Toast.makeText(DetailScreen.this, "Lỗi từ server: " + errorBody, Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void initView(){
        toolbar = findViewById(R.id.detailScreen_toolBar);
        setSupportActionBar(toolbar);
        DetailScreenFragment fragment = new DetailScreenFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.detailScreen_frameLayout, fragment);
        fragmentTransaction.commit();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        toolbar.setNavigationOnClickListener(view -> onBackPressed());
        toolbar.setTitleTextAppearance(this, R.style.ToolbarTitleText);
//        Drawable customBackIcon = getResources().getDrawable(R.drawable.icon_back_toolbar);
        Drawable originalDrawable = getResources().getDrawable(R.drawable.icon_back_toolbar);
        Drawable customBackIcon = resizeDrawable(originalDrawable, 24, 24);
        getSupportActionBar().setHomeAsUpIndicator(customBackIcon);
    }

    private Drawable resizeDrawable(Drawable drawable, int width, int height) {
        Bitmap bitmap = ((BitmapDrawable) drawable).getBitmap();
        Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, width, height, false);
        return new BitmapDrawable(getResources(), resizedBitmap);
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }
}