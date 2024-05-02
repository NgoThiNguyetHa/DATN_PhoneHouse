package com.example.appkhachhang.StoreDetailViewPager;

import android.app.ProgressDialog;
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
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appkhachhang.Adapter.HangSanXuatAdapter;
import com.example.appkhachhang.Adapter.ListPhoneAdapter;
import com.example.appkhachhang.Adapter.TopDienThoaiAdapter;
import com.example.appkhachhang.Api.ApiRetrofit;
import com.example.appkhachhang.Api.ApiService;
import com.example.appkhachhang.Api.HangSanXuat_API;
import com.example.appkhachhang.Fragment.DetailScreenFragment;
import com.example.appkhachhang.Fragment.PhoneListFragment;
import com.example.appkhachhang.Interface.OnItemClickListenerHang;
import com.example.appkhachhang.Interface_Adapter.IItemListPhoneListener;
import com.example.appkhachhang.Model.HangSanXuat;
import com.example.appkhachhang.Model.Root;
import com.example.appkhachhang.Model.Store;
import com.example.appkhachhang.Model.TopDienThoai;
import com.example.appkhachhang.R;
import com.example.appkhachhang.activity.DanhSachActivity;
import com.example.appkhachhang.activity.DetailScreen;
import com.example.appkhachhang.untils.MySharedPreferences;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SanPhamCuaHangFragment extends Fragment {
    RecyclerView rcSanPham, rcFilterHangSX;
    List<HangSanXuat> hangSanXuatList;
    List<Root> list;

    LinearLayoutManager linearLayoutManager;
    GridLayoutManager gridLayoutManager;
    HangSanXuatAdapter hangSanXuatAdapter;
    ListPhoneAdapter listPhoneAdapter;
    MySharedPreferences mySharedPreferences;
    Store cuaHang;
    TextView sanPhamCuaHang_tvSoLuongSP;
    ProgressDialog progressDialog;

    LinearLayout lnThapCao , lnCaoThap;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_san_pham, container, false);
        initView(view);
        initVariable();
        getDataBundle();
        return view;
    }

    private void initView(View view) {
        rcSanPham = view.findViewById(R.id.sanPham_rcSanPham);
        rcFilterHangSX = view.findViewById(R.id.sanPham_rcFillterHangSX);
        sanPhamCuaHang_tvSoLuongSP = view.findViewById(R.id.sanPhamCuaHang_tvSoLuongSP);
        lnCaoThap = view.findViewById(R.id.sanPhamCuaHang_linearCaoDenThap);
        lnThapCao = view.findViewById(R.id.sanPhamCuaHang_linearThapCao);
    }

    private void initVariable() {
        mySharedPreferences = new MySharedPreferences(getContext());
        //hãng sx
        hangSanXuatList = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getContext());
        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        rcFilterHangSX.setLayoutManager(linearLayoutManager);
        hangSanXuatAdapter = new HangSanXuatAdapter(getContext(), hangSanXuatList, new OnItemClickListenerHang() {
            @Override
            public void onItemClickHang(HangSanXuat hangSanXuat) {
                //xử lý filter
                getChiTietTheoHang(hangSanXuat.get_id());
            }
        });
        rcFilterHangSX.setAdapter(hangSanXuatAdapter);

        //list danh sách theo cửa hàng
        list = new ArrayList<>();
        gridLayoutManager = new GridLayoutManager(getContext(),2);
        rcSanPham.setLayoutManager(gridLayoutManager);
        listPhoneAdapter = new ListPhoneAdapter(getContext());
        listPhoneAdapter.setData(list);
        listPhoneAdapter.setOnClickListener(new IItemListPhoneListener() {
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
        rcSanPham.setAdapter(listPhoneAdapter);


    }
    private void getDataBundle(){
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            cuaHang = (Store) bundle.getSerializable("cuaHang");
            getData(cuaHang.get_id());
            actionButton(cuaHang.get_id());
        }
    }
    private void getData(String id){
        //hãng
        HangSanXuat_API.hangSXApi.getHangSanXuat().enqueue(new Callback<List<HangSanXuat>>() {
            @Override
            public void onResponse(Call<List<HangSanXuat>> call, Response<List<HangSanXuat>> response) {
                if (response.isSuccessful()) {
                    hangSanXuatList.clear();
                    hangSanXuatList.addAll(response.body());
                    hangSanXuatAdapter.notifyDataSetChanged();
                }else{
                    Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<HangSanXuat>> call, Throwable t) {

            }
        });

        //all chi tiet
        ApiService apiService = ApiRetrofit.getApiService();
        Call<List<Root>> call1 = apiService.getAllDienThoai_CuaHang(id);
        call1.enqueue(new Callback<List<Root>>() {
            @Override
            public void onResponse(Call<List<Root>> call, Response<List<Root>> response) {
                if (response.isSuccessful()) {
                    List<Root> data = response.body();
                    list.clear();
                    list.addAll(data);
                    listPhoneAdapter.notifyDataSetChanged();
                    sanPhamCuaHang_tvSoLuongSP.setText("Tất cả sản phẩm: "+list.size() + " kết quả");
                } else {
                    Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Root>> call, Throwable t) {

            }
        });
    }

    private void getChiTietTheoHang(String id){
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Vui Lòng Chờ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiService apiService = ApiRetrofit.getApiService();
        Call<List<Root>> call = apiService.getAllChiTietTheoHang(id);
        call.enqueue(new Callback<List<Root>>() {
            @Override
            public void onResponse(Call<List<Root>> call, Response<List<Root>> response) {
                if (response.isSuccessful()) {
                    List<Root> data = response.body();
                    list.clear();
                    list.addAll(data);
                    listPhoneAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();
                    sanPhamCuaHang_tvSoLuongSP.setText("Tất cả sản phẩm: "+list.size() + " kết quả");

                } else {
                    Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Root>> call, Throwable t) {
                Log.e("Get ListPhone", t.getMessage());
            }
        });
    }
    private void actionButton(String id){
        lnCaoThap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortGiaTienCaoThap(id);
            }
        });
        lnThapCao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sortGiaTienThapCao(id);
            }
        });
    }

    private void sortGiaTienCaoThap(String id) {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Vui Lòng Chờ...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiService apiService = ApiRetrofit.getApiService();
        Call<List<Root>> call = apiService.getSapXepGiaGiamCuaHang(id);
        call.enqueue(new Callback<List<Root>>() {
            @Override
            public void onResponse(Call<List<Root>> call, Response<List<Root>> response) {
                if (response.isSuccessful()) {
                    List<Root> data = response.body();
                    list.clear();
                    list.addAll(data);
                    listPhoneAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();

                } else {
                    Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Root>> call, Throwable t) {
                Log.e("filter dung luong ram", t.getLocalizedMessage());
            }
        });
    }

    private void sortGiaTienThapCao(String id) {
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
        ApiService apiService = ApiRetrofit.getApiService();
        Call<List<Root>> call = apiService.getSapXepGiaTangCuaHang(id);
        call.enqueue(new Callback<List<Root>>() {
            @Override
            public void onResponse(Call<List<Root>> call, Response<List<Root>> response) {
                if (response.isSuccessful()) {
                    List<Root> data = response.body();
                    list.clear();
                    list.addAll(data);
                    listPhoneAdapter.notifyDataSetChanged();
                    progressDialog.dismiss();

                } else {
                    Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Root>> call, Throwable t) {
                Log.e("filter dung luong ram", t.getLocalizedMessage());
            }
        });
    }
}