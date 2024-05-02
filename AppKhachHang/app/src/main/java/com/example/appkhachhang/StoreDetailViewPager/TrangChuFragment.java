package com.example.appkhachhang.StoreDetailViewPager;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import com.example.appkhachhang.Adapter.ListPhoneAdapter;
import com.example.appkhachhang.Adapter.TopDienThoaiAdapter;
import com.example.appkhachhang.Adapter.TopKhuyenMaiAdapter;
import com.example.appkhachhang.Api.ApiRetrofit;
import com.example.appkhachhang.Api.ApiService;
import com.example.appkhachhang.Fragment.DetailScreenFragment;
import com.example.appkhachhang.Interface_Adapter.IItemListPhoneListener;
import com.example.appkhachhang.Model.Root;
import com.example.appkhachhang.Model.Store;
import com.example.appkhachhang.Model.TopDienThoai;
import com.example.appkhachhang.R;
import com.example.appkhachhang.activity.DetailScreen;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class TrangChuFragment extends Fragment {

    RecyclerView rc_dienThoai_ban_chay , rc_dienThoai_khuyen_mai;
    List<TopDienThoai> topDienThoaiList ;
    List<Root> khuyenMaiDienThoaiList;
    TopDienThoaiAdapter adapterTop , adapterKhuyenMai;
    TopKhuyenMaiAdapter adapter;
    LinearLayoutManager manager;
    Store cuaHang;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_trang_chu, container, false);
        initView(view);
        initVariable();
        getDataBundle();
        return view;
    }

    private void initView(View view){
        rc_dienThoai_ban_chay = view.findViewById(R.id.trangChu_rcBanChayNhat);
        rc_dienThoai_khuyen_mai = view.findViewById(R.id.trangChu_rcKhuyenMai);
    }

    private void initVariable(){
        topDienThoaiList = new ArrayList<>();
        manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rc_dienThoai_ban_chay.setLayoutManager(manager);
        adapterTop = new TopDienThoaiAdapter(getContext());
        adapterTop.setData(topDienThoaiList);
        rc_dienThoai_ban_chay.setAdapter(adapterTop);


        //khuyen mai
        khuyenMaiDienThoaiList = new ArrayList<>();
        manager = new LinearLayoutManager(getContext());
        manager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rc_dienThoai_khuyen_mai.setLayoutManager(manager);
        adapter = new TopKhuyenMaiAdapter(getContext());
        adapter.setData(khuyenMaiDienThoaiList);
        adapter.setOnClickListener(new IItemListPhoneListener() {
            @Override
            public void onClickDetail(Root root) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("idChiTietDienThoai", root.getChiTietDienThoai());
                DetailScreenFragment fragmentB = new DetailScreenFragment();
                fragmentB.setArguments(bundle);
                Intent intent = new Intent(getActivity(), DetailScreen.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        rc_dienThoai_khuyen_mai.setAdapter(adapter);
    }
    private void getDataBundle(){
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            cuaHang = (Store) bundle.getSerializable("cuaHang");
            getData(cuaHang.get_id());

        }
    }
    private void getData(String id){
        //
        ApiService apiService = ApiRetrofit.getApiService();
        Call<List<TopDienThoai>> call = apiService.getTopDienThoai_CuaHang(id);
        call.enqueue(new Callback<List<TopDienThoai>>() {
            @Override
            public void onResponse(Call<List<TopDienThoai>> call, Response<List<TopDienThoai>> response) {
                if (response.isSuccessful()) {
                    List<TopDienThoai> data = response.body();
                    topDienThoaiList.clear();
                    topDienThoaiList.addAll(data);
                    Log.e("topdienthoai",response.body().toString());
                    adapterTop.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<TopDienThoai>> call, Throwable t) {

            }
        });

        //khuyen mai
        Call<List<Root>> call1 = apiService.getChiTietDienThoaiKhuyenMai(id);
        call1.enqueue(new Callback<List<Root>>() {
            @Override
            public void onResponse(Call<List<Root>> call, Response<List<Root>> response) {
                if (response.isSuccessful()) {
                    List<Root> data = response.body();
                    khuyenMaiDienThoaiList.clear();
                    khuyenMaiDienThoaiList.addAll(data);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Root>> call, Throwable t) {

            }
        });
    }
}