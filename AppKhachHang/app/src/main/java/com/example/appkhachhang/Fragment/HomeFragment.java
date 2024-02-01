package com.example.appkhachhang.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.appkhachhang.Adapter.HangSanXuatAdapter;
import com.example.appkhachhang.Adapter.SanPhamAdapter;
import com.example.appkhachhang.Adapter.SanPhamHotAdapter;
import com.example.appkhachhang.Api.HangSanXuat_API;
import com.example.appkhachhang.Api.SanPham_API;
import com.example.appkhachhang.Api.ThongKe_API;
import com.example.appkhachhang.Model.HangSanXuat;
import com.example.appkhachhang.Model.SanPham;
import com.example.appkhachhang.Model.SanPhamHot;
import com.example.appkhachhang.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {
    RecyclerView recyclerViewSP, recyclerViewSPHot, recyclerViewHang;
    SanPhamAdapter sanPhamAdapter;
    SanPhamHotAdapter sanPhamHotAdapter;
    HangSanXuatAdapter hangSanXuatAdapter;
    List<SanPham> list;
    List<SanPhamHot> listSPHot;
    List<HangSanXuat> listHang;
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
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewSP.setLayoutManager(linearLayoutManager);
        list = new ArrayList<>();
        getListSanPham();
        sanPhamAdapter = new SanPhamAdapter(getContext(), list);
        recyclerViewSP.setAdapter(sanPhamAdapter);
        LinearLayoutManager linearLayoutManager1 = new LinearLayoutManager(getContext());
        linearLayoutManager1.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewSPHot.setLayoutManager(linearLayoutManager1);
        listSPHot = new ArrayList<>();
        getSanPhamHot();
        sanPhamHotAdapter = new SanPhamHotAdapter(getContext(), listSPHot);
        recyclerViewSPHot.setAdapter(sanPhamAdapter);
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getContext());
        linearLayoutManager2.setOrientation(RecyclerView.HORIZONTAL);
        recyclerViewHang.setLayoutManager(linearLayoutManager2);
        listHang = new ArrayList<>();
        getHangSanXuat();
        hangSanXuatAdapter = new HangSanXuatAdapter(getContext(), listHang);
        recyclerViewHang.setAdapter(hangSanXuatAdapter);
    }

    void getSanPhamHot(){
        ThongKe_API.thongKeApi.getSanPhamHot().enqueue(new Callback<List<SanPham>>() {
            @Override
            public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                list.clear();
                list.addAll(response.body());
                sanPhamAdapter.notifyDataSetChanged();
                Log.e("abc", "onResponse: " + list.get(0).getMaMau() + "");
            }

            @Override
            public void onFailure(Call<List<SanPham>> call, Throwable t) {
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
                Log.e("abcabc", "onResponse: " + listHang.get(0).getTenHang() );
            }

            @Override
            public void onFailure(Call<List<HangSanXuat>> call, Throwable t) {
                Log.e("error", t.getMessage());

            }
        });
    }

    void getListSanPham(){
        SanPham_API.sanPhamApi.getAllSanPham().enqueue(new Callback<List<SanPham>>() {
            @Override
            public void onResponse(Call<List<SanPham>> call, Response<List<SanPham>> response) {
                list.clear();
                list.addAll(response.body());
                sanPhamAdapter.notifyDataSetChanged();
                Log.e("abc", "onResponse: " + list.get(0).getMaMau() + "");
            }

            @Override
            public void onFailure(Call<List<SanPham>> call, Throwable t) {
                Toast.makeText(getActivity(), "Call API error: "  + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });
    }

}