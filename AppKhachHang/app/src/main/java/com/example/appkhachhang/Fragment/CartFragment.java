package com.example.appkhachhang.Fragment;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appkhachhang.Adapter.GioHangAdapter;
import com.example.appkhachhang.Api.ApiRetrofit;
import com.example.appkhachhang.Api.ApiService;
import com.example.appkhachhang.Interface.OnClickListenerGioHang;
import com.example.appkhachhang.Model.ChiTietDienThoai;
import com.example.appkhachhang.Model.ChiTietGioHang;
import com.example.appkhachhang.Model.User;
import com.example.appkhachhang.R;
import com.example.appkhachhang.ThanhToanActivity;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.internal.$Gson$Types;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment implements OnClickListenerGioHang {

    RecyclerView rc_gioHang;
    TextView tvEmpty, tvTongTien;
    Button btnThanhToan;

    List<ChiTietGioHang> list, listChon;
    GioHangAdapter adapter;
    LinearLayoutManager linearLayoutManager;

    User user;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rc_gioHang = view.findViewById(R.id.rc_gioHang);
        tvEmpty = view.findViewById(R.id.gioHang_tvEmpty);
        tvTongTien = view.findViewById(R.id.gioHang_tvTongTien);
        btnThanhToan = view.findViewById(R.id.gioHang_btnThanhToan);
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rc_gioHang.setLayoutManager(linearLayoutManager);
        ((Activity)getContext()).setTitle("Giỏ hàng");
        list = new ArrayList<>();
        getDataGioHang();
        listChon = new ArrayList<>();
        adapter = new GioHangAdapter(getContext(),list, this);
        rc_gioHang.setAdapter(adapter);

        btnThanhToan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getContext(), ThanhToanActivity.class);
                Gson gson = new Gson();
                String json = gson.toJson(listChon);
                intent.putExtra("chiTietGioHangList", json);
                startActivity(intent);
            }
        });
    }

    private void getDataGioHang(){
        SharedPreferences prefs = getActivity().getSharedPreferences("user_info", MODE_PRIVATE);
        String idKhachHang = prefs.getString("idKhachHang", "abc");
        ApiService apiService = ApiRetrofit.getApiService();
        Call<List<ChiTietGioHang>> call = apiService.getListGioHang(idKhachHang);
        call.enqueue(new Callback<List<ChiTietGioHang>>() {
            @Override
            public void onResponse(Call<List<ChiTietGioHang>> call, Response<List<ChiTietGioHang>> response) {
                    if (response.body()!= null){
                        List<ChiTietGioHang> data = response.body();
                        list.clear();
                        list.addAll(data);
                        adapter.notifyDataSetChanged();
                        tvEmpty.setVisibility(View.GONE);
                    } else {
                        tvEmpty.setText("Không có dữ liệu");
                    }
            }

            @Override
            public void onFailure(Call<List<ChiTietGioHang>> call, Throwable t) {
                // Handle failure
                Log.e("handle", t.getMessage());
            }
        });
    }

    @Override
    public void onItemClick(ChiTietGioHang chiTietGioHang, boolean isChecked) {
        if (isChecked){
            listChon.add(chiTietGioHang);

        } else {
            listChon.remove(chiTietGioHang);
        }
        int tongtien = 0;
        for (int i = 0; i < listChon.size(); i++) {
            tongtien += listChon.get(i).getGiaTien()*listChon.get(i).getSoLuong();
        }
        tvTongTien.setText(tongtien + "");
//        SharedPreferences sharedPreferences = getContext().getSharedPreferences("listChon", MODE_PRIVATE);
//        SharedPreferences.Editor editor = sharedPreferences.edit();
//        Gson gson = new Gson();
//        String json = gson.toJson(listChon);
//        editor.putString("listChonJson", json);
//        editor.apply();
    }
}