package com.example.appkhachhang.Fragment;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appkhachhang.Adapter.ListPhoneAdapter;
import com.example.appkhachhang.Api.ApiRetrofit;
import com.example.appkhachhang.Api.ApiService;
import com.example.appkhachhang.Api.ChiTietSanPham_API;
import com.example.appkhachhang.Interface_Adapter.IItemListPhoneListener;
import com.example.appkhachhang.Model.HangSanXuat;
import com.example.appkhachhang.Model.Root;
import com.example.appkhachhang.Model.SearchResponse;
import com.example.appkhachhang.Model.Store;
import com.example.appkhachhang.R;
import com.example.appkhachhang.activity.DetailScreen;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StoreDetailFragment extends Fragment {
    LinearLayout ln_boLoc, ln_locGia , ln_locDlRam , ln_locBoNho, ln_sxGiaCao , ln_sxGiaThap, ln_sxDiemDanhGia, ln_sxUuDai;
    RecyclerView rc_danhSachDienThoai;
    EditText edSearch;
    TextView tvTenCuaHang;
    ProgressBar progressBar;
    List<Root> list;
    ListPhoneAdapter adapter;
    GridLayoutManager manager;
    Store store;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_store_detail, container, false);
        initView(view);
        initVariable();
        action();
        return view;
    }
    private void initView(View view){
        rc_danhSachDienThoai = view.findViewById(R.id.rc_danhSachDienThoai);
        edSearch = view.findViewById(R.id.danhSach_edSearch);
        ln_boLoc = view.findViewById(R.id.danhSach_linearBoLoc);
        ln_locGia = view.findViewById(R.id.danhSach_linearLocGia);
        ln_locDlRam = view.findViewById(R.id.danhSach_linearLocDlRam);
        ln_locBoNho = view.findViewById(R.id.danhSach_linearLocBoNho);
        ln_sxGiaCao = view.findViewById(R.id.danhSach_linearSXGiaCaoThap);
        ln_sxGiaThap = view.findViewById(R.id.danhSach_linearSXGiaThapCao);
        ln_sxDiemDanhGia = view.findViewById(R.id.danhSach_linearSXDiemDanhGia);
        ln_sxUuDai = view.findViewById(R.id.danhSach_linearSXUuDai);
        progressBar = view.findViewById(R.id.danhSach_progressBar);
        tvTenCuaHang = view.findViewById(R.id.tvTenCuaHang);
    }
    private void initVariable(){
        list = new ArrayList<>();
        manager = new GridLayoutManager(getContext(), 2);
        rc_danhSachDienThoai.setLayoutManager(manager);
        adapter = new ListPhoneAdapter(getContext());
        adapter.setData(list);
        adapter.setOnClickListener(new IItemListPhoneListener() {
            @Override
            public void onClickDetail(Root root) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("idChiTietDienThoai", root.getChiTietDienThoai());
                Intent intent = new Intent(getContext(), DetailScreen.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        rc_danhSachDienThoai.setAdapter(adapter);
    }
    private void action(){
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            store = (Store) bundle.getSerializable("cuaHang");
            // Sử dụng dữ liệu ở đây
            getData(store.get_id());
            tvTenCuaHang.setText(store.getUsername());
        }
    }
    public void getData(String id){
        progressBar.setVisibility(View.VISIBLE);
        ChiTietSanPham_API.chiTietSanPhamApi.getChiTietDienThoaiTheoCuaHang(id).enqueue(new Callback<List<Root>>() {
            @Override
            public void onResponse(Call<List<Root>> call, Response<List<Root>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    list.clear();
                    list.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onFailure(Call<List<Root>> call, Throwable t) {
                Log.e("zzzz", "onFailure: " + t.getMessage() );
            }
        });
    }
}
