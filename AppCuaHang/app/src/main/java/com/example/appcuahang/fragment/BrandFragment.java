package com.example.appcuahang.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appcuahang.R;
import com.example.appcuahang.adapter.BrandAdapter;
import com.example.appcuahang.api.ApiRetrofit;
import com.example.appcuahang.api.ApiService;
import com.example.appcuahang.interface_adapter.interface_adapter.IItemBrandListenner;
import com.example.appcuahang.model.Brand;
import com.example.appcuahang.untils.MySharedPreferences;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrandFragment extends Fragment {
    RecyclerView rc_brand;
    List<Brand> list;
    List<Brand> listBackUp;
    BrandAdapter adapter;
    GridLayoutManager manager;
    MySharedPreferences mySharedPreferences;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_brand, container, false);
        ((Activity) getContext()).setTitle("Hãng Sản Xuất");
        initView(view);
        initVariable();
        getData();
        return view;
    }

    private void initView(View view) {
        rc_brand = view.findViewById(R.id.rc_brand);
    }

    private void initVariable(){
        list = new ArrayList<>();
        listBackUp = new ArrayList<>();
        manager = new GridLayoutManager(getContext(), 2);
        rc_brand.setLayoutManager(manager);
        adapter = new BrandAdapter(getContext(), new IItemBrandListenner() {
            @Override
            public void deleteBrand(String idBrand) {

            }

            @Override
            public void editBrand(Brand idBrand) {
                updateData(idBrand);
            }

            @Override
            public void showDetail(String idBrand) {

            }
        });
        adapter.setData(list);
        rc_brand.setAdapter(adapter);
    }

//    @SuppressLint("NotifyDataSetChanged")
//    private void fillDataRecyclerView() {
//        list.clear();
//        list.addAll(listBackUp);
//        adapter.notifyDataSetChanged();
//    }

    private void getData() {
        mySharedPreferences = new MySharedPreferences(getContext());
        ApiService apiService = ApiRetrofit.getApiService();

        Call<List<Brand>> call = apiService.getHangSanXuatByCuaHang(mySharedPreferences.getUserId());

        call.enqueue(new Callback<List<Brand>>() {
            @Override
            public void onResponse(Call<List<Brand>> call, Response<List<Brand>> response) {
                if (response.isSuccessful()) {
                    List<Brand> data = response.body();
//                    listBackUp.clear();
//                    listBackUp.addAll(data);
//                    fillDataRecyclerView();
                    list.clear();
                    list.addAll(data);
                    adapter.notifyDataSetChanged();
                } else {
                    // Handle error
                    Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Brand>> call, Throwable t) {
                // Handle failure
                Log.e("brand", t.getMessage());
            }
        });

    }

    private void addData() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_brand, null);
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

        EditText edTenHang = view.findViewById(R.id.dl_brand_edTenHang);
        Button btnSave = view.findViewById(R.id.dl_brand_btnSave);
        TextView tvTitle = view.findViewById(R.id.dl_brand_tvTitle);
        ImageView imgView = view.findViewById(R.id.dl_brand_imageView);

        tvTitle.setText("Thêm Mới Hãng Sản Xuất");
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenHang = edTenHang.getText().toString().trim();
                String password = mySharedPreferences.getUserId();
                ApiService apiService = ApiRetrofit.getApiService();
                Call<Brand> call = apiService.postHangSanXuat(new Brand(tenHang, password));
                call.enqueue(new Callback<Brand>() {
                    @Override
                    public void onResponse(Call<Brand> call, Response<Brand> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "Thêm mới thành công", Toast.LENGTH_SHORT).show();
                            getData();
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<Brand> call, Throwable t) {
                        Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }


    private void updateData(Brand brand) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_brand, null);
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

        EditText edTenHang = view.findViewById(R.id.dl_brand_edTenHang);
        Button btnSave = view.findViewById(R.id.dl_brand_btnSave);
        TextView tvTitle = view.findViewById(R.id.dl_brand_tvTitle);
        ImageView imgView = view.findViewById(R.id.dl_brand_imageView);

        tvTitle.setText("Cập Nhật Hãng Sản Xuất");
        edTenHang.setText(brand.getTenHang());
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String tenHang = edTenHang.getText().toString().trim();
                String password = mySharedPreferences.getUserId();
                ApiService apiService = ApiRetrofit.getApiService();
                Call<Brand> call = apiService.putHangSanXuat(brand.get_id(), new Brand(tenHang, password));
                call.enqueue(new Callback<Brand>() {
                    @Override
                    public void onResponse(Call<Brand> call, Response<Brand> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                            getData();
                            dialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<Brand> call, Throwable t) {
                        Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });

        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }


    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.dialog, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.btn_add) {
            addData();
        }
        return super.onOptionsItemSelected(item);
    }

}