package com.example.appkhachhang.activity;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.appkhachhang.Fragment.HotProductFragment;
import com.example.appkhachhang.Fragment.PhoneListFragment;
import com.example.appkhachhang.R;

public class HotProductActivity extends AppCompatActivity {
  Toolbar toolbar;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_hot_product);
    initView();
  }
  private void initView(){
    toolbar = findViewById(R.id.danhSachHot_toolBar);
    setSupportActionBar(toolbar);
    HotProductFragment fragment = new HotProductFragment();
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    fragmentTransaction.add(R.id.danhSachHot_frameLayout, fragment);
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