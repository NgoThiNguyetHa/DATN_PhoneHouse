package com.example.appkhachhang.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
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
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appkhachhang.Adapter.ListPhoneAdapter;
import com.example.appkhachhang.Adapter.StoreAdapter;
import com.example.appkhachhang.Api.ChiTietSanPham_API;
import com.example.appkhachhang.Interface_Adapter.IItemListPhoneListener;
import com.example.appkhachhang.Interface_Adapter.IItemStoreListener;
import com.example.appkhachhang.Model.ChiTietDienThoai;
import com.example.appkhachhang.Model.CuaHang;
import com.example.appkhachhang.Model.Root;
import com.example.appkhachhang.Model.SearchResponse;
import com.example.appkhachhang.Model.Store;
import com.example.appkhachhang.R;
import com.example.appkhachhang.activity.DetailScreen;
import com.example.appkhachhang.activity.ShopActivity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchFragment extends Fragment {
    RecyclerView rc_danhSachDienThoai, rc_danhSachCuaHang;
    EditText edSearch;
    TextView tv_entry;
    LinearLayout ln_boLoc, ln_locGia , ln_locDlRam , ln_locBoNho, ln_sxGiaCao , ln_sxGiaThap, ln_sxDiemDanhGia, ln_sxUuDai;
    ProgressBar progressBar;
    private static final long SEARCH_DELAY = 500;
    private Handler handler = new Handler();
    private Runnable searchRunnable;
    String giaMin, giaMax, ram, boNho, sortByPrice, uuDaiHot, maHangSanXuat, sortDanhGia;
    SearchResponse searchResponse;
    List<Store> cuaHangList;
    List<Root> dienThoaiList;
    ListPhoneAdapter adapter;
    StoreAdapter storeAdapter;
    GridLayoutManager manager, managerStore;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search, container, false);
        initView(view);

        edSearch.requestFocus();
        edSearch.addTextChangedListener(searchTextWatcher);
        initVariable();
        return view;
    }
    private void initView(View view){
        rc_danhSachDienThoai = view.findViewById(R.id.rc_danhSachDienThoai);
        rc_danhSachCuaHang = view.findViewById(R.id.rc_danhSachCuaHang);
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
        tv_entry = view.findViewById(R.id.tv_entry);
    }
    private void initVariable(){
        dienThoaiList = new ArrayList<>();
        manager = new GridLayoutManager(getContext(), 2);
        rc_danhSachDienThoai.setLayoutManager(manager);
        adapter = new ListPhoneAdapter(getContext());
        adapter.setData(dienThoaiList);
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

        cuaHangList = new ArrayList<>();
        managerStore = new GridLayoutManager(getContext(), 2);
        rc_danhSachCuaHang.setLayoutManager(managerStore);
        storeAdapter = new StoreAdapter(getContext());
        storeAdapter.setData(cuaHangList);
        storeAdapter.setOnClickListener(new IItemStoreListener() {
            @Override
            public void getSanPhamTheoCuaHang(Store store) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("cuaHang", store);
                Intent intent = new Intent(getContext(), ShopActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
        rc_danhSachCuaHang.setAdapter(storeAdapter);
    }
    private TextWatcher searchTextWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        }

        @Override
        public void afterTextChanged(Editable editable) {
            progressBar.setVisibility(View.VISIBLE);

            performSearch(editable.toString());
        }
    };
    private void performSearch(String query) {
        progressBar.setVisibility(View.VISIBLE);

        if (searchRunnable != null) {
            handler.removeCallbacks(searchRunnable);
        }
        searchRunnable = new Runnable() {
            @Override
            public void run() {
                ChiTietSanPham_API.chiTietSanPhamApi.searchDienThoaiVaCuaHang(query, giaMin, giaMax, ram, boNho, sortByPrice, uuDaiHot, maHangSanXuat, sortDanhGia).enqueue(new Callback<SearchResponse>() {
                    @Override
                    public void onResponse(Call<SearchResponse> call, Response<SearchResponse> response) {
                        if (response.isSuccessful() && response.body() != null) {
                            searchResponse = response.body();
                            dienThoaiList.clear();
                            dienThoaiList.addAll(searchResponse.getDienThoais());
                            adapter.notifyDataSetChanged();

                            cuaHangList.clear();
                            cuaHangList.addAll(searchResponse.getCuaHangs());
                            storeAdapter.notifyDataSetChanged();

                            progressBar.setVisibility(View.GONE);
                            tv_entry.setVisibility(View.GONE);
                        }else {
                            tv_entry.setVisibility(View.VISIBLE);
                        }
                    }

                    @Override
                    public void onFailure(Call<SearchResponse> call, Throwable t) {
                        Log.e("zzzz", "onFailure: " + t.getMessage() );
                    }
                });

            }
        };

        handler.postDelayed(searchRunnable, SEARCH_DELAY);
    }
}
