package com.example.appkhachhang.Fragment;

import android.app.Activity;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
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
import com.example.appkhachhang.Model.ChiTietGioHang;
import com.example.appkhachhang.R;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartFragment extends Fragment {

    RecyclerView rc_gioHang;
    TextView tvEmpty, tvTongTien;
    Button btnThanhToan;

    List<ChiTietGioHang> list;
    GioHangAdapter adapter;




    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_cart, container, false);
        ((Activity)getContext()).setTitle("Giỏ Hàng");
        initView(view);
        initVariable();
        getDataGioHang();
        return view;
    }

    private void initView(View view){
        rc_gioHang = view.findViewById(R.id.rc_gioHang);
        tvEmpty = view.findViewById(R.id.gioHang_tvEmpty);
        tvTongTien = view.findViewById(R.id.gioHang_tvTongTien);
        btnThanhToan = view.findViewById(R.id.gioHang_btnThanhToan);
    }

    private void initVariable(){
        list = new ArrayList<>();
        adapter = new GioHangAdapter(getContext(),list);
    }

    private void getDataGioHang(){
        ApiService apiService = ApiRetrofit.getApiService();
        Call<List<ChiTietGioHang>> call = apiService.getListGioHang();
        call.enqueue(new Callback<List<ChiTietGioHang>>() {
            @Override
            public void onResponse(Call<List<ChiTietGioHang>> call, Response<List<ChiTietGioHang>> response) {
                if (response.isSuccessful()) {
                    List<ChiTietGioHang> data = response.body();
                    list.clear();
                    list.addAll(data);
                    adapter.notifyDataSetChanged();
                    tvEmpty.setVisibility(View.GONE);

                } else {
                    // Handle error
                    Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ChiTietGioHang>> call, Throwable t) {
                // Handle failure
                Log.e("dungluong",t.getMessage());
            }
        });
    }
}