package com.example.appkhachhang.Adapter;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import com.example.appkhachhang.Fragment.StoreDetailFragment;
import com.example.appkhachhang.StoreDetailViewPager.SanPhamCuaHangFragment;
import com.example.appkhachhang.StoreDetailViewPager.ThongTinCuaHangFragment;
import com.example.appkhachhang.StoreDetailViewPager.TrangChuFragment;


public class StoreViewPagerAdapter extends FragmentStatePagerAdapter {
    public StoreViewPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                return new TrangChuFragment();
            case 1:
                return new SanPhamCuaHangFragment();
            case 2:
                return new ThongTinCuaHangFragment();
        }
        return new StoreDetailFragment();
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
                title = "Trang Chủ";
                break;
            case 1:
                title = "Tất Cả Sản Phẩm";
                break;
            case 2:
                title = "Hồ Sơ Cửa Hàng";
                break;
        }
        return title;
    }
}
