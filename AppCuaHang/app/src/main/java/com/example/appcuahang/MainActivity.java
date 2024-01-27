package com.example.appcuahang;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager2.widget.ViewPager2;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuItem;

//import com.example.appcuahang.databinding.ActivityMainBinding;
import com.example.appcuahang.databinding.ActivityMainBinding;
import com.example.appcuahang.fragment.BillOrderFragment;
import com.example.appcuahang.fragment.HomeFragment;
import com.example.appcuahang.fragment.MoreFragment;
import com.example.appcuahang.fragment.NotificationFragment;
import com.example.appcuahang.fragment.ProductFragment;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
    ActivityMainBinding binding;
    ActionMode mActionMode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        actionBottomNav();
    }

    public void actionBottomNav() {
        binding.mainBottomNav.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                if (R.id.ic_home == item.getItemId()){
                    replaceFragment(new HomeFragment());
                }else if (R.id.ic_product == item.getItemId()){
                    replaceFragment(new ProductFragment());
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

}