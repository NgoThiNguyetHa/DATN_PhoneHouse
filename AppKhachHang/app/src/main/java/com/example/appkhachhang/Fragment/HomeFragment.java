package com.example.appkhachhang.Fragment;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.appkhachhang.Adapter.HangSanXuatAdapter;
import com.example.appkhachhang.Adapter.ChiTietDienThoatAdapter;
import com.example.appkhachhang.Adapter.SanPhamHotAdapter;
import com.example.appkhachhang.Api.ChiTietSanPham_API;
import com.example.appkhachhang.Api.HangSanXuat_API;
import com.example.appkhachhang.Api.ThongKe_API;
import com.example.appkhachhang.activity.DetailScreen;
import com.example.appkhachhang.Interface.OnItemClickListenerHang;
import com.example.appkhachhang.Interface.OnItemClickListenerSanPham;
import com.example.appkhachhang.Interface.OnItemClickListenerSanPhamHot;
import com.example.appkhachhang.LoginScreen;
import com.example.appkhachhang.Model.ChiTietDienThoai;
import com.example.appkhachhang.Model.HangSanXuat;
import com.example.appkhachhang.Model.SanPhamHot;
import com.example.appkhachhang.R;
import com.example.appkhachhang.activity.DanhSachActivity;
import com.example.appkhachhang.untils.MySharedPreferences;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    RecyclerView recyclerViewSP, recyclerViewSPHot, recyclerViewHang;
    ImageView imgSPHot, imgSP;
    ChiTietDienThoatAdapter chiTietDienThoatAdapter;
    SanPhamHotAdapter sanPhamHotAdapter;
    HangSanXuatAdapter hangSanXuatAdapter;
    List<ChiTietDienThoai> list;
    List<SanPhamHot> listSPHot;
    List<HangSanXuat> listHang;
    Toolbar toolbar;
    AppCompatActivity activity;
    MySharedPreferences mySharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recyclerViewSPHot = view.findViewById(R.id.ryc_sphot);
        recyclerViewSP = view.findViewById(R.id.ryc_sp);
        recyclerViewHang = view.findViewById(R.id.ryc_hang);
        imgSPHot = view.findViewById(R.id.img_listSPHot);
        imgSP = view.findViewById(R.id.img_listSP);
        mySharedPreferences = new MySharedPreferences(getContext());
        imgSPHot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Xử lý khi click vào ảnh sản phẩm hot
                replaceFragment(new HotProductFragment());
            }
        });
        imgSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                replaceFragment(new ProductFragment());
            }
        });
//        toolbar = view.findViewById(R.id.main_toolBar);
//        activity = (AppCompatActivity) getActivity();
//        if (activity != null) {
//            activity.setSupportActionBar(toolbar);
//            activity.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            activity.getSupportActionBar().setTitle("Home");
//        }
        sanPham();
        sanPhamHot();
        hangSanXuat();
    }


    void sanPham(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewSP.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        getListSanPham();
        chiTietDienThoatAdapter = new ChiTietDienThoatAdapter(getContext(), list, new OnItemClickListenerSanPham() {
            @Override
            public void onItemClickSP(ChiTietDienThoai chiTietDienThoai) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("idChiTietDienThoai", chiTietDienThoai);
                DetailScreenFragment fragmentB = new DetailScreenFragment();
                fragmentB.setArguments(bundle);
                Intent intent = new Intent(getActivity(), DetailScreen.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        recyclerViewSP.setAdapter(chiTietDienThoatAdapter);
    }

    void sanPhamHot(){
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());
        linearLayoutManager1.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewSPHot.setLayoutManager(linearLayoutManager1);
        listSPHot = new ArrayList<>();
        getSanPhamHot();
        sanPhamHotAdapter = new SanPhamHotAdapter(getContext(), listSPHot, new OnItemClickListenerSanPhamHot() {
            @Override
            public void onItemClickSPHot(ChiTietDienThoai chiTietDienThoai) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("idChiTietDienThoai", chiTietDienThoai);
                DetailScreenFragment fragmentB = new DetailScreenFragment();
                fragmentB.setArguments(bundle);
                Intent intent = new Intent(getActivity(), DetailScreen.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        recyclerViewSPHot.setAdapter(sanPhamHotAdapter);
    }

    void hangSanXuat(){
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
        linearLayoutManager2.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewHang.setLayoutManager(linearLayoutManager2);
        listHang = new ArrayList<>();
        getHangSanXuat();
        hangSanXuatAdapter = new HangSanXuatAdapter(getContext(), listHang, new OnItemClickListenerHang() {
            @Override
            public void onItemClickHang(HangSanXuat hangSanXuat) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("idHangSanXuat", hangSanXuat);
                PhoneListFragment fragmentB = new PhoneListFragment();
                fragmentB.setArguments(bundle);
                Intent intent = new Intent(getActivity(), DanhSachActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        recyclerViewHang.setAdapter(hangSanXuatAdapter);
    }

    void getSanPhamHot(){
        ThongKe_API.thongKeApi.getSanPhamHot().enqueue(new Callback<List<SanPhamHot>>() {
            @Override
            public void onResponse(Call<List<SanPhamHot>> call, Response<List<SanPhamHot>> response) {
                List<SanPhamHot> sanPhamHotList = response.body();
                listSPHot.clear();
                listSPHot.addAll(sanPhamHotList);
//                Log.e("list danh gia", String.valueOf(sanPhamHotList.get(1).getDanhGia().size()));
                sanPhamHotAdapter.notifyDataSetChanged();

                if (sanPhamHotList != null && !sanPhamHotList.isEmpty()) {
                    listSPHot.clear();
                    listSPHot.addAll(sanPhamHotList);
                    for (int i = 0; i < sanPhamHotList.size(); i++) {
                        SanPhamHot sanPhamHot = sanPhamHotList.get(i);
                        if (sanPhamHot != null && sanPhamHot.getDanhGia() != null) {
//                            Log.e("list danh gia", String.valueOf(sanPhamHot.getDanhGia().size()));
                        } else {
//                            Log.e("list danh gia", "DanhGia is null or empty");
                        }
                    }
                    sanPhamHotAdapter.notifyDataSetChanged();
                } else {
//                    Log.e("list danh gia", "SanPhamHotList is null or empty");
                }
            }

            @Override
            public void onFailure(Call<List<SanPhamHot>> call, Throwable t) {
               Log.e("error", t.getMessage());

            }
        });


    }

    void getHangSanXuat(){
        HangSanXuat_API.hangSXApi.getHangSanXuat().enqueue(new Callback<List<HangSanXuat>>() {
            @Override
            public void onResponse(Call<List<HangSanXuat>> call, Response<List<HangSanXuat>> response) {
                listHang.clear();
                listHang.addAll(response.body());
                hangSanXuatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<HangSanXuat>> call, Throwable t) {
                Log.e("error", t.getMessage());

            }
        });
    }


    void getListSanPham(){
        ChiTietSanPham_API.chiTietSanPhamApi.getChiTiet().enqueue(new Callback<List<ChiTietDienThoai>>() {
            @Override
            public void onResponse(Call<List<ChiTietDienThoai>> call, Response<List<ChiTietDienThoai>> response) {
                list.clear();
                list.addAll(response.body());
                chiTietDienThoatAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<List<ChiTietDienThoai>> call, Throwable t) {
                Log.e("errorrr", "onFailure: " + t.getMessage() );
            }
        });
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.toolbar_home_menu, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.gioHang){
            if (mySharedPreferences.getUserId() != null && !mySharedPreferences.getUserId().isEmpty()) {
                replaceFragment(new CartFragment());
            }else {
                Intent intent = new Intent(getContext(), LoginScreen.class);
                startActivity(intent);
            }
        }
        return super.onOptionsItemSelected(item);
    }



    private void replaceFragment(Fragment fragment){
        FragmentManager manager = getActivity().getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.replace(R.id.frameLayout,fragment);
        transaction.commit();
    }
}