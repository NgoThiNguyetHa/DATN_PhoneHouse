package com.example.appkhachhang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
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
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

  BottomNavigationView bottomNavigationView;
  FrameLayout frameLayout;
  Toolbar toolbar;
  @SuppressLint("MissingInflatedId")
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);
    bottomNavigationView = findViewById(R.id.bottomNavView);
    frameLayout = findViewById(R.id.frameLayout);
    toolbar = findViewById(R.id.main_toolBar);
    setSupportActionBar(toolbar);
    bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
      @Override
      public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int itemID = item.getItemId();
        if (itemID == R.id.navHome){
          loadFragment(new HomeFragment(), false);
        } else if (itemID == R.id.navBill) {
          loadFragment(new BillOrderFragment(), false);
        } else if (itemID == R.id.navNotify) {
          loadFragment(new NotificationFragment(), false);
        } else {
          loadFragment(new UserFragment(), false);
        }
        return true;
      }
    });
    loadFragment(new HomeFragment(), true);
    fullScreen();
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