package com.example.appcuahang.fragment;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcuahang.R;
import com.example.appcuahang.adapter.BrandAdapter;
import com.example.appcuahang.adapter.MauAdapter;
import com.example.appcuahang.api.ApiMauService;
import com.example.appcuahang.api.ApiRetrofit;
import com.example.appcuahang.api.ApiService;
//import com.example.appcuahang.interface_adapter.IItemBrandListenner;
import com.example.appcuahang.interface_adapter.IItemMauListenner;
import com.example.appcuahang.model.Brand;
import com.example.appcuahang.model.Mau;
import com.example.appcuahang.untils.MySharedPreferences;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MauFragment extends Fragment {
    RecyclerView rc_mau;
    List<Mau> list = new ArrayList<>();
    List<Mau> listFilter;
    MauAdapter adapter;
    GridLayoutManager manager;
    EditText edTenMau;
    TextView tv_entry;
    TextInputEditText mau_edSearch;
    ProgressDialog progressDialog;

    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_mau, container, false);
        ((Activity) getContext()).setTitle("Màu");
        initView(view);
        initVariable();
        getData();

        //Khoi tao
        listFilter = new ArrayList<>();
        mau_edSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                listFilter.clear();
                tv_entry.setVisibility(View.VISIBLE);
                for(int i = 0; i< list.size(); i++){
                    if(list.get(i).getTenMau().toString().trim().toLowerCase().contains(mau_edSearch.getText().toString().trim().toLowerCase()) && mau_edSearch.getText().length() != 0){
                        listFilter.add(list.get(i));
                        tv_entry.setVisibility(View.GONE);
                    }
                }
                if(listFilter.size() == 0){
                    tv_entry.setVisibility(View.VISIBLE);
                }
                adapter = new MauAdapter(getContext(), new IItemMauListenner() {
                    @Override
                    public void deleteBrand(String idBrand) {

                    }

                    @Override
                    public void editMau(Mau isMau) { //
                        updateData(isMau);
                    }

                    @Override
                    public void showDetail(String idBrand) {

                    }
                });

                if (mau_edSearch.getText().toString().trim().isEmpty()) {
                    adapter.setData(list);
                    tv_entry.setVisibility(View.GONE);
                    rc_mau.setAdapter(adapter);
                } else {
                    adapter.setData(listFilter);
                    rc_mau.setAdapter(adapter);
                }

            }
        });
        return view;
    }

    private void initView(View view) {

        rc_mau = view.findViewById(R.id.rc_mau);
        mau_edSearch = view.findViewById(R.id.mau_edSearch);
        tv_entry = view.findViewById(R.id.tv_entry);
    }

    private void initVariable() {
        list = new ArrayList<>();
        listFilter = new ArrayList<>();
        manager = new GridLayoutManager(getContext(), 2);
        rc_mau.setLayoutManager(manager);
        adapter = new MauAdapter(getContext(), new IItemMauListenner() {
            @Override
            public void deleteBrand(String idBrand) {

            }

            @Override
            public void editMau(Mau isMau) { //
                updateData(isMau);
            }

            @Override
            public void showDetail(String idBrand) {

            }
        });
        adapter.setData(list);
        rc_mau.setAdapter(adapter);
    }

    private void getData() {
        ApiMauService apiMauService = ApiRetrofit.getApiMauService();

        Call<List<Mau>> call = apiMauService.getMau();
        call.enqueue(new Callback<List<Mau>>() {
            @Override
            public void onResponse(Call<List<Mau>> call, Response<List<Mau>> response) {
                if (response.isSuccessful()) {
                    List<Mau> data = response.body();

                    list.clear();
                    list.addAll(data);
                    adapter.notifyDataSetChanged();
                } else {
                    // Handle error
                    Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Mau>> call, Throwable t) {
                // Handle failure
                Log.e("mau", t.getMessage());
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
            dialog();
        }
        return super.onOptionsItemSelected(item);
    }

    private void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_mau, null);
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

        edTenMau = view.findViewById(R.id.dl_mau_edTenMau);
        Button btnSave = view.findViewById(R.id.dl_mau_btnSave);
        TextView tvTitle = view.findViewById(R.id.dl_mau_tvTitle);
        ImageView imgView = view.findViewById(R.id.dl_mau_imageView);

        tvTitle.setText("Dialog Thêm Màu");
        btnSave.setText("Thêm mới");
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Validate()){

                    progressDialog = new ProgressDialog(getContext());
                    progressDialog.setMessage("Vui Lòng Chờ...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                String tenMau = edTenMau.getText().toString().trim();
                ApiMauService apiMauService = ApiRetrofit.getApiMauService();
                Call<Mau> call = apiMauService.postMau(new Mau("", tenMau));
                call.enqueue(new Callback<Mau>() {
                    @Override
                    public void onResponse(Call<Mau> call, Response<Mau> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                            getData();
                            dialog.dismiss();
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<Mau> call, Throwable t) {
                        Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
            }
        });

        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    private void updateData(Mau mau) { // thêm sửa mới
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_mau, null);
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

        edTenMau = view.findViewById(R.id.dl_mau_edTenMau);

        Button btnSave = view.findViewById(R.id.dl_mau_btnSave);
        TextView tvTitle = view.findViewById(R.id.dl_mau_tvTitle);
        ImageView imgView = view.findViewById(R.id.dl_mau_imageView);

        tvTitle.setText("Cập Nhật Màu");
        edTenMau.setText(mau.getTenMau());
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(ValidateUpdate(mau)){

                    progressDialog = new ProgressDialog(getContext());
                    progressDialog.setMessage("Vui Lòng Chờ...");
                    progressDialog.setCancelable(false);
                    progressDialog.show();
                String tenMau = edTenMau.getText().toString().trim();
                ApiMauService apiMauService = ApiRetrofit.getApiMauService();
                Call<Mau> call = apiMauService.putMau(mau.get_id(), new Mau(mau.get_id(), tenMau));
                call.enqueue(new Callback<Mau>() {
                    @Override
                    public void onResponse(Call<Mau> call, Response<Mau> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                            getData();
                            dialog.dismiss();
                            progressDialog.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<Mau> call, Throwable t) {
                        Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
            }
        });


        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }
    private boolean Validate(){
        if(edTenMau.getText().toString().isEmpty()){
            edTenMau.setError("Không được để trống!!");
            return false;
        }
        for (Mau mau: list){
            if (mau.getTenMau().toLowerCase().equals(edTenMau.getText().toString().trim().toLowerCase())){
                edTenMau.setError("Màu đã tồn tại!!");
                return false;
            }
        }
        return true;
    }

    private boolean ValidateUpdate( Mau itemUpdate){
        if(edTenMau.getText().toString().isEmpty()){
            edTenMau.setError("Không được để trống!!");
            return false;
        }
        for (Mau mau: list){
            if (mau.getTenMau().toLowerCase().equals(edTenMau.getText().toString().trim().toLowerCase())
                    && itemUpdate.get_id() != mau.get_id()
            ){
                edTenMau.setError("Màu đã tồn tại!!");
                return false;
            }
        }
        return true;
    }
}
