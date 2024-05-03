package com.example.appcuahang.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.Toast;

import com.example.appcuahang.R;
import com.example.appcuahang.adapter.ReviewDanhGiaAdapter;
import com.example.appcuahang.api.ApiRetrofit;
import com.example.appcuahang.api.ApiService;
import com.example.appcuahang.interface_adapter.IItemDanhGiaListenner;
import com.example.appcuahang.interface_adapter.IItemPhoneListenner;
import com.example.appcuahang.model.Brand;
import com.example.appcuahang.model.Phone;
import com.example.appcuahang.model.Rating;
import com.example.appcuahang.model.Store;
import com.example.appcuahang.untils.MySharedPreferences;
import com.google.firebase.FirebaseApp;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ReviewFragment extends Fragment {


    RecyclerView rc_QLDanhGia;
    ReviewDanhGiaAdapter adapter;
    CardView cv_sxTheoDiemDanhGia , cv_sxTheoNgay;
    List<Rating> list;
    LinearLayoutManager manager;
    MySharedPreferences mySharedPreferences;
    ProgressDialog progressDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_review, container, false);
        ((Activity) getContext()).setTitle("Đánh Giá");
        initView(view);
        initVariable();
        getData();
        return view;
    }

    private void initView(View view){
        rc_QLDanhGia = view.findViewById(R.id.rc_qlDanhGia);
    }

    private void initVariable(){
        list = new ArrayList<>();
        FirebaseApp.initializeApp(getContext());
        mySharedPreferences = new MySharedPreferences(getContext());
        manager = new LinearLayoutManager(getContext());
        rc_QLDanhGia.setLayoutManager(manager);
        adapter = new ReviewDanhGiaAdapter(getContext(), new IItemDanhGiaListenner() {
            @Override
            public void deleteDanhGia(String idDanhGia) {
                actionDelete(idDanhGia);
            }
        });
        adapter.setData(list);
        rc_QLDanhGia.setAdapter(adapter);
    }

    private void getData(){
        ApiService apiService = ApiRetrofit.getApiService();
        Call<List<Rating>> ratingListCall = apiService.getDanhGiaTheoCuaHang(mySharedPreferences.getUserId());
        ratingListCall.enqueue(new Callback<List<Rating>>() {
            @Override
            public void onResponse(Call<List<Rating>> call, Response<List<Rating>> response) {
                if (response.isSuccessful()) {
                    List<Rating> data = response.body();
                    list.clear();
                    list.addAll(data);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Rating>> call, Throwable t) {
                Log.e("Rating", t.getMessage());
            }
        });
    }
    //xoa danh gia
    private void actionDelete(String id){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_delete_danhgia, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);
        Button btnYes, btnNo;
        btnYes = view.findViewById(R.id.dl_danhGia_btnYes);
        btnNo = view.findViewById(R.id.dl_danhGia_btnNo);
        ApiService apiService = ApiRetrofit.getApiService();
        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Vui Lòng Chờ...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                Call<Rating> call = apiService.deleteDanhGia(id);
                call.enqueue(new Callback<Rating>() {
                    @Override
                    public void onResponse(Call<Rating> call, Response<Rating> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "Xóa thành công", Toast.LENGTH_SHORT).show();
                            getData();
                            dialog.dismiss();
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<Rating> call, Throwable t) {
                        Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }
}