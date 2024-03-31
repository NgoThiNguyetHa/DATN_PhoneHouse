package com.example.appkhachhang.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.appkhachhang.Fragment.HomeFragment;
import com.example.appkhachhang.viewpager.DonDaGiaoFragment;
import com.example.appkhachhang.viewpager.DonHuyFragment;
import com.example.appkhachhang.viewpager.DonXuLyFragment;


public class ViewPagerAdapter extends FragmentStatePagerAdapter {
    public ViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new DonXuLyFragment();
            case 1:
                return new DonDaGiaoFragment();
            case 2:
                return new DonHuyFragment();
        }
        return new HomeFragment();
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        switch (position){
            case 0:
                title = "Đang Xử Lý";
                break;
            case 1:
                title = "Đã Giao";
                break;
            case 2:
                title = "Đã Hủy";
                break;
        }
        return title;
    }
}
