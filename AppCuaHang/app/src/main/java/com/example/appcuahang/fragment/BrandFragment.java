package com.example.appcuahang.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.appcuahang.R;
import com.example.appcuahang.adapter.BrandAdapter;
import com.example.appcuahang.api.ApiRetrofit;
import com.example.appcuahang.api.ApiService;
import com.example.appcuahang.model.Brand;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrandFragment extends Fragment {
    RecyclerView rc_brand;
    List<Brand> list = new ArrayList<>();
    BrandAdapter adapter;
    GridLayoutManager manager;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_brand, container, false);
        initView(view);
        getData();
        return view;
    }

    private void initView(View view){
        rc_brand = view.findViewById(R.id.rc_brand);

    }

    private void getData(){
        list = new ArrayList<>();
        manager = new GridLayoutManager(getContext(),2);
        rc_brand.setLayoutManager(manager);
        ApiService apiService = ApiRetrofit.getApiService();

        Call<List<Brand>> call = apiService.getHangSanXuat();
        call.enqueue(new Callback<List<Brand>>() {
            @Override
            public void onResponse(Call<List<Brand>> call, Response<List<Brand>> response) {
                if (response.isSuccessful()) {
                    List<Brand> data = response.body();
                    adapter = new BrandAdapter(getContext(),data);
                    rc_brand.setAdapter(adapter);
                } else {
                    // Handle error
                    Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Brand>> call, Throwable t) {
                // Handle failure
                Log.e("brand",t.getMessage());
            }
        });
    }
}