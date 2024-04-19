package com.example.appcuahang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.WindowInsets;
import android.view.WindowInsetsController;
import android.view.WindowManager;

//import com.example.appcuahang.databinding.ActivityMainBinding;
import com.example.appcuahang.databinding.ActivityMainBinding;
import com.example.appcuahang.fragment.BillOrderFragment;
import com.example.appcuahang.fragment.HomeFragment;
import com.example.appcuahang.fragment.MoreFragment;
import com.example.appcuahang.fragment.NotificationFragment;
import com.example.appcuahang.fragment.PhoneFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
  ActivityMainBinding binding;
  @Override
  protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    binding = ActivityMainBinding.inflate(getLayoutInflater());
    setContentView(binding.getRoot());
    actionBottomNav();
    fullScreen();
  }

  public void actionBottomNav() {
    setSupportActionBar(binding.mainToolBar);
    binding.mainBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
      @Override
      public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (R.id.ic_home == item.getItemId()){
          replaceFragment(new HomeFragment());
        }else if (R.id.ic_product == item.getItemId()){
          replaceFragment(new PhoneFragment());
        }else if (R.id.ic_bill == item.getItemId()){
          replaceFragment(new BillOrderFragment());
        }else if (R.id.ic_notification == item.getItemId()){
          replaceFragment(new NotificationFragment());
        }else {
          replaceFragment(new MoreFragment());
        }
        return true;
      }
    });
    replaceFragment(new HomeFragment());
  }

  private void replaceFragment(Fragment fragment){
    FragmentManager manager = getSupportFragmentManager();
    FragmentTransaction transaction = manager.beginTransaction();
    transaction.replace(R.id.main_frame,fragment);
    transaction.commit();
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