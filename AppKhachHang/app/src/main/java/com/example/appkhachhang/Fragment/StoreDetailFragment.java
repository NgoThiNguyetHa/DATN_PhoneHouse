package com.example.appkhachhang.Fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.StateListDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.content.ContextCompat;
import androidx.core.view.ViewCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import com.example.appkhachhang.Adapter.ListPhoneAdapter;
import com.example.appkhachhang.Adapter.StoreViewPagerAdapter;
import com.example.appkhachhang.Adapter.ViewPagerAdapter;
import com.example.appkhachhang.Api.ApiRetrofit;
import com.example.appkhachhang.Api.ApiService;
import com.example.appkhachhang.Api.ChiTietSanPham_API;
import com.example.appkhachhang.Interface_Adapter.IItemListPhoneListener;
import com.example.appkhachhang.Model.CuaHang;
import com.example.appkhachhang.Model.DanhGia;
import com.example.appkhachhang.Model.HangSanXuat;
import com.example.appkhachhang.Model.Root;
import com.example.appkhachhang.Model.SearchResponse;
import com.example.appkhachhang.Model.Store;
import com.example.appkhachhang.R;
import com.example.appkhachhang.activity.DetailScreen;
import com.google.android.material.tabs.TabLayout;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreDetailFragment extends Fragment {

    private TabLayout mTabLayout;
    private ViewPager mViewPager;
    Store cuaHang;
    TextView tvTenCuaHang , tvDanhGia, tvDiaChi;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store_detail, container, false);
        ((Activity)getContext()).setTitle("Cửa Hàng");
        init(view);
        action();
        customTabLayout();
        getDataBundle();
        return view;
    }

    private void init(View view){
        mTabLayout = view.findViewById(R.id.tab_layout);
        mViewPager = view.findViewById(R.id.viewPager2);
        tvTenCuaHang = view.findViewById(R.id.storeDetail_tvTenCuaHang);
        tvDanhGia = view.findViewById(R.id.storeDetail_tvDanhGia);
        tvDiaChi = view.findViewById(R.id.storeDetail_tvDiaChi);
    }

    private void action(){
        StoreViewPagerAdapter adapter = new StoreViewPagerAdapter(getActivity().getSupportFragmentManager(), FragmentStatePagerAdapter.BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
        mViewPager.setAdapter(adapter);
        mTabLayout.setupWithViewPager(mViewPager);
    }

    private void customTabLayout(){
        int tabCount = mTabLayout.getTabCount();
        for (int i = 0; i < tabCount; i++) {
            View tabView = ((ViewGroup) mTabLayout.getChildAt(0)).getChildAt(i);
            tabView.requestLayout();
            ViewCompat.setBackground(tabView, setImageButtonStateNew(requireContext()));
            ViewCompat.setPaddingRelative(tabView, tabView.getPaddingStart(), tabView.getPaddingTop(), tabView.getPaddingEnd(), tabView.getPaddingBottom());
        }
    }
    public StateListDrawable setImageButtonStateNew(Context mContext) {
        StateListDrawable states = new StateListDrawable();
        states.addState(new int[]{android.R.attr.state_selected}, ContextCompat.getDrawable(mContext, R.drawable.tab_bg_normal_blue));
        states.addState(new int[]{-android.R.attr.state_selected}, ContextCompat.getDrawable(mContext, R.drawable.tab_bg_normal));

        return states;
    }

    private void getDataBundle(){
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            cuaHang = (Store) bundle.getSerializable("cuaHang");
            tvTenCuaHang.setText("Cửa Hàng "+cuaHang.getUsername());
            tvDiaChi.setText("Địa Chỉ: "+cuaHang.getDiaChi());
            setDanhGia(cuaHang.get_id());
        }
    }

    private void setDanhGia(String id){
        ApiService apiService = ApiRetrofit.getApiService();
        Call<List<DanhGia>> ratingListCall = apiService.getDanhGiaTheoCuaHang(id);
        ratingListCall.enqueue(new Callback<List<DanhGia>>() {
            @Override
            public void onResponse(Call<List<DanhGia>> call, Response<List<DanhGia>> response) {
                if (response.isSuccessful()) {
                    List<DanhGia> data = response.body();

                    int tongDiemDanhGia = 0;
                    for (int i = 0; i < data.size(); i++) {
                        tongDiemDanhGia += data.get(i).getDiemDanhGia();
                    }
                    int soLuongDanhGia = data.size();
                    float diemTrungBinh = 0;
                    if (soLuongDanhGia != 0) {
                        diemTrungBinh = (float) tongDiemDanhGia / soLuongDanhGia;
                        String formattedNumber = String.format("%.1f", diemTrungBinh);
//                        float roundedNumber = Float.parseFloat(""+formattedNumber);
                        tvDanhGia.setText(""+(formattedNumber) + "/5");
                    }else{
                        tvDanhGia.setText("0/5");
                    }
                } else {
                    Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DanhGia>> call, Throwable t) {
                Log.e("Rating", t.getMessage());
            }
        });
    }
}
