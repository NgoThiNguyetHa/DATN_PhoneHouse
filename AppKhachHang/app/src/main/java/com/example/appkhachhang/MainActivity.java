package com.example.appkhachhang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;
import android.widget.FrameLayout;

import com.example.appkhachhang.Fragment.BillOrderFragment;
import com.example.appkhachhang.Fragment.CartFragment;
import com.example.appkhachhang.Fragment.HomeFragment;
import com.example.appkhachhang.Fragment.NotificationFragment;
import com.example.appkhachhang.Fragment.PaymentMethodFragment;
import com.example.appkhachhang.Fragment.UserFragment;
import com.example.appkhachhang.untils.MySharedPreferences;
import com.example.appkhachhang.viewpager.DonXuLyFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.color.DynamicColors;
import com.google.android.material.elevation.SurfaceColors;

public class MainActivity extends AppCompatActivity {

  BottomNavigationView bottomNavigationView;
  FrameLayout frameLayout;
  Toolbar toolbar;
  MySharedPreferences mySharedPreferences;
  @SuppressLint("MissingInflatedId")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    bottomNavigationView = findViewById(R.id.bottomNavView);
    frameLayout = findViewById(R.id.frameLayout);
    toolbar = findViewById(R.id.main_toolBar);
    setSupportActionBar(toolbar);
    mySharedPreferences = new MySharedPreferences(getApplicationContext());
    bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
      @Override
      public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemID = item.getItemId();
        if (itemID == R.id.navHome){
          loadFragment(new HomeFragment(), false);
        } else if (itemID == R.id.navBill) {
          if (mySharedPreferences.getUserId() != null && !mySharedPreferences.getUserId().isEmpty()) {
            loadFragment(new BillOrderFragment(), false);
          }else {
            Intent intent = new Intent(MainActivity.this, LoginScreen.class);
            startActivity(intent);
          }
        } else if (itemID == R.id.navNotify) {
          loadFragment(new NotificationFragment(), false);
        } else {
          loadFragment(new UserFragment(), false);
        }
        return true;
      }
    });
    loadFragment(new HomeFragment(), true);
    Window window = this.getWindow();
    DynamicColors.applyIfAvailable(this); // After this
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
      window.setNavigationBarColor(SurfaceColors.SURFACE_2.getColor(this));
    }
    Intent intent = getIntent();
    if(intent != null) {
      // Lấy dữ liệu từ Intent nếu cần
      Bundle extras = intent.getExtras();
      if(extras != null) {
        String data = extras.getString("key");
        // Gửi dữ liệu cho Fragment HoaDon
        DonXuLyFragment hoaDonFragment = new DonXuLyFragment();
        Bundle bundle = new Bundle();
        bundle.putString("key", data);
        hoaDonFragment.setArguments(bundle);

        // Thay thế Fragment hiện tại bằng Fragment HoaDon
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout, hoaDonFragment).commit();
      }
    }
//    fullScreen();
  }
  public void loadFragment(Fragment fragment, boolean isAppInitialized){
    FragmentManager fragmentManager = getSupportFragmentManager();
    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
    if (isAppInitialized){
      fragmentTransaction.add(R.id.frameLayout, fragment);
    } else {
      fragmentTransaction.replace(R.id.frameLayout, fragment);
    }
    fragmentTransaction.commit();
  }


}