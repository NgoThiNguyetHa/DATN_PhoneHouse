package com.example.appkhachhang.viewpager;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import com.example.appkhachhang.Adapter.HoaDonAdapter;
import com.example.appkhachhang.Api.ApiRetrofit;
import com.example.appkhachhang.Api.ApiService;
import com.example.appkhachhang.Fragment.InfoOrderFragment;
import com.example.appkhachhang.Interface_Adapter.IItemBillOrderListener;
import com.example.appkhachhang.Model.HoaDon;
import com.example.appkhachhang.R;
import com.example.appkhachhang.activity.ThongTinDonHangActivity;
import com.example.appkhachhang.untils.MySharedPreferences;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DonDaGiaoFragment extends Fragment {

    RecyclerView rc_donDaGiao;
    List<HoaDon> list = new ArrayList<>();
    HoaDonAdapter adapter;
    LinearLayoutManager manager;
    MySharedPreferences mySharedPreferences;
    private static final String trangThai = "Đã giao";
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_don_da_giao, container, false);
        initView(view);
        getData();
        return view;
    }
    private void initView(View view){
        rc_donDaGiao = view.findViewById(R.id.rc_donDaGiao);
    }

    private void getData(){
        list = new ArrayList<>();
        manager = new LinearLayoutManager(getContext());
        rc_donDaGiao.setLayoutManager(manager);
        mySharedPreferences = new MySharedPreferences(getContext());
        adapter= new HoaDonAdapter(getContext(), new IItemBillOrderListener() {
            @Override
            public void showDetail(HoaDon idHoaDon) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("infoOrder", idHoaDon);
                InfoOrderFragment fragmentB = new InfoOrderFragment();
                fragmentB.setArguments(bundle);
                Intent intent = new Intent(getActivity(), ThongTinDonHangActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        adapter.setData(list);
        rc_donDaGiao.setAdapter(adapter);
        getHoaDonByTrangThai(trangThai,mySharedPreferences.getUserId());
        Log.d("userid", "getData: " + mySharedPreferences.getUserId());
    }

    public void getHoaDonByTrangThai(String trangThaiNhanHang, String maCuaHang) {
        ApiService apiService = ApiRetrofit.getApiService();
        Call<List<HoaDon>> call = apiService.getHoaDonTrangThai(trangThaiNhanHang,maCuaHang);
        call.enqueue(new Callback<List<HoaDon>>() {
            @Override
            public void onResponse(Call<List<HoaDon>> call, Response<List<HoaDon>> response) {
                if (response.isSuccessful()) {
                    List<HoaDon> hoaDonList = response.body();
                    list.clear();
                    list.addAll(hoaDonList);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<HoaDon>> call, Throwable t) {
                Toast.makeText(getContext(), "Error", Toast.LENGTH_SHORT).show();
//                Log.e("err",t.getMessage());
            }
        });
    }
    @Override
    public void onResume() {
        super.onResume();
        getData();
    }
}