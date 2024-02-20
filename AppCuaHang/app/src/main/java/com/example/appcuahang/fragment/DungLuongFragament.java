package com.example.appcuahang.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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
import com.example.appcuahang.adapter.DungLuongAdapter;
import com.example.appcuahang.api.ApiDungLuongService;
import com.example.appcuahang.api.ApiRetrofit;
import com.example.appcuahang.api.ApiService;
import com.example.appcuahang.interface_adapter.interface_adapter.IItemBrandListenner;
import com.example.appcuahang.interface_adapter.interface_adapter.IItemDungLuongListenner;
import com.example.appcuahang.model.Brand;
import com.example.appcuahang.model.DungLuong;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DungLuongFragament extends Fragment {
    RecyclerView rc_dungLuong;
    List<DungLuong> list;
    List<DungLuong> listBackUp;
    DungLuongAdapter adapter;
    GridLayoutManager manager;
    EditText edDungLuong, edGiaTien;
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_dungluong, container, false);
        ((Activity)getContext()).setTitle("Dung lượng");
        getData();
        initView(view);
        initVariable();
        return view;
    }
    private void initView(View view){
        rc_dungLuong = view.findViewById(R.id.rc_dungLuong);
    }

    private void initVariable(){
        list = new ArrayList<>();
        listBackUp = new ArrayList<>();
        manager = new GridLayoutManager(getContext(), 2);
        rc_dungLuong.setLayoutManager(manager);
        adapter = new DungLuongAdapter(getContext(), new IItemDungLuongListenner() {
            @Override
            public void deleteBrand(String idBrand) {
            }

            @Override
            public void editDungLuong(DungLuong idDungLuong) {
                updateData(idDungLuong);
            }

            @Override
            public void showDetail(String idBrand) {

            }
        });
        adapter.setData(list);
        rc_dungLuong.setAdapter(adapter);
    }
    private void getData(){

//        ApiDuService apiRamService = ApiRetrofit.getApiRamService();
        ApiDungLuongService apiDungDuongService = ApiRetrofit.getApiDungLuongService();

        Call<List<DungLuong>> call = apiDungDuongService.getDungLuong();
        call.enqueue(new Callback<List<DungLuong>>() {
            @Override
            public void onResponse(Call<List<DungLuong>> call, Response<List<DungLuong>> response) {
                if (response.isSuccessful()) {
                    List<DungLuong> data = response.body();
                    list.clear();
                    list.addAll(data);
                    adapter.notifyDataSetChanged();
                } else {
                    // Handle error
                    Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DungLuong>> call, Throwable t) {
                // Handle failure
                Log.e("dungluong",t.getMessage());
            }
        });
    }

    private void updateData(DungLuong dungLuong) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_dungluong, null);
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

        edDungLuong = view.findViewById(R.id.dl_dungLuong_edTenDungLuong);
        Button btnSave = view.findViewById(R.id.dl_dungLuong_btnSave);
        TextView tvTitle = view.findViewById(R.id.dl_dungLuong_tvTitle);
        ImageView imgView = view.findViewById(R.id.dl_dungLuong_imageView);

        tvTitle.setText("Cập Nhật Hãng Sản Xuất");
        edDungLuong.setText(dungLuong.getBoNho());
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validate()) {
                    String tenDL = edDungLuong.getText().toString().trim();
                    ApiDungLuongService apiDungLuongService = ApiRetrofit.getApiDungLuongService();
                    Call<DungLuong> call = apiDungLuongService.putDungLuong(dungLuong.get_id(), new DungLuong(tenDL));
                    call.enqueue(new Callback<DungLuong>() {
                        @Override
                        public void onResponse(Call<DungLuong> call, Response<DungLuong> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                getData();
                                dialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<DungLuong> call, Throwable t) {
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
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.dialog,menu);
        super.onCreateOptionsMenu(menu, inflater);
    }
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.btn_add){
            dialog();
        }
        return super.onOptionsItemSelected(item);
    }
    private void dialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_dungluong, null);
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

         edDungLuong = view.findViewById(R.id.dl_dungLuong_edTenDungLuong);
        Button btnSave = view.findViewById(R.id.dl_dungLuong_btnSave);
        TextView tvTitle = view.findViewById(R.id.dl_dungLuong_tvTitle);
        ImageView imgView = view.findViewById(R.id.dl_dungLuong_imageView);

        tvTitle.setText("Dialog Thêm Dung Lượng");
        btnSave.setText("Thêm mới");
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Validate()) {
                    String tenDungLuong = edDungLuong.getText().toString().trim();
                    ApiDungLuongService apiDungLuongService = ApiRetrofit.getApiDungLuongService();
                    Call<DungLuong> call = apiDungLuongService.postDungLuong(new DungLuong(tenDungLuong));
                    call.enqueue(new Callback<DungLuong>() {
                        @Override
                        public void onResponse(Call<DungLuong> call, Response<DungLuong> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                                getData();
                                dialog.dismiss();
                            }
                        }

                        @Override
                        public void onFailure(Call<DungLuong> call, Throwable t) {
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
        if(edDungLuong.getText().toString().isEmpty()){
            edDungLuong.setError("Không được để trống!!");
            return false;
        }else if(!Pattern.matches("\\d+", edDungLuong.getText().toString())){
            edDungLuong.setError("Phải nhập là số!!");
            return false;
        }

//        if( edGiaTien.getText().toString().isEmpty()){
//            edGiaTien.setError("Không được để trống!!");
//            return false;
//        }else if(!Pattern.matches("\\d+", edGiaTien.getText().toString())){
//            edGiaTien.setError("Phải nhập là số!!");
//            return false;
//        }
        return true;
    }
}

