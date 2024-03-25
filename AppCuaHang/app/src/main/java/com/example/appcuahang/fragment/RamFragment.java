package com.example.appcuahang.fragment;

import android.annotation.SuppressLint;
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
import com.example.appcuahang.adapter.MauAdapter;
import com.example.appcuahang.adapter.RamAdapter;
import com.example.appcuahang.api.ApiMauService;
import com.example.appcuahang.api.ApiRamService;
import com.example.appcuahang.api.ApiRetrofit;
import com.example.appcuahang.api.ApiService;
//import com.example.appcuahang.interface_adapter.IItemBrandListenner;
import com.example.appcuahang.interface_adapter.IItemMauListenner;
import com.example.appcuahang.interface_adapter.IItemRamListenner;
import com.example.appcuahang.model.Brand;
import com.example.appcuahang.model.Mau;
import com.example.appcuahang.model.Ram;
import com.example.appcuahang.untils.MySharedPreferences;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RamFragment extends Fragment {
    RecyclerView rc_ram;
    List<Ram> list = new ArrayList<>();
    List<Ram> listBackUp;
    RamAdapter adapter;
    GridLayoutManager manager;
    EditText edTenRam;

    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_ram, container, false);
        ((Activity) getContext()).setTitle("Ram");
        initView(view);
        getData();
        initVariable();
        fillDataRecyclerView();
        return view;
    }

    private void initView(View view) {
        rc_ram = view.findViewById(R.id.rc_ram);
    }

    private void initVariable() {
        list = new ArrayList<>();
        listBackUp = new ArrayList<>();
        manager = new GridLayoutManager(getContext(), 2);
        rc_ram.setLayoutManager(manager);
        adapter = new RamAdapter(getContext(), new IItemRamListenner() {
            @Override
            public void deleteBrand(String idBrand) {

            }

            @Override
            public void editRam(Ram idRam) { //
                updateData(idRam);
            }

            @Override
            public void showDetail(String idBrand) {

            }
        });
        adapter.setData(list);
        rc_ram.setAdapter(adapter);
    }

    private void getData() {
//        list = new ArrayList<>();
//        manager = new GridLayoutManager(getContext(), 2);
//        rc_ram.setLayoutManager(manager);
        ApiRamService apiRamService = ApiRetrofit.getApiRamService();

        Call<List<Ram>> call = apiRamService.getRam();
        call.enqueue(new Callback<List<Ram>>() {
            @Override
            public void onResponse(Call<List<Ram>> call, Response<List<Ram>> response) {
                if (response.isSuccessful()) {
                    List<Ram> data = response.body();

                    list.clear();
                    list.addAll(data);
                    adapter.notifyDataSetChanged();
                } else {
                    // Handle error
                    Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Ram>> call, Throwable t) {
                // Handle failure
                Log.e("ram", t.getMessage());
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
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_ram, null);
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

        edTenRam = view.findViewById(R.id.dl_ram_edTenRam);
        Button btnSave = view.findViewById(R.id.dl_ram_btnSave);
        TextView tvTitle = view.findViewById(R.id.dl_ram_tvTitle);
        ImageView imgView = view.findViewById(R.id.dl_ram_imageView);

        tvTitle.setText("Dialog Thêm Ram");
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Validate()){


                Number tenRam = Integer.parseInt(edTenRam.getText().toString().trim());
                ApiRamService apiRamService = ApiRetrofit.getApiRamService();
                Call<Ram> call = apiRamService.postRam(new Ram(tenRam));
                call.enqueue(new Callback<Ram>() {
                    @Override
                    public void onResponse(Call<Ram> call, Response<Ram> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                            getData();
                            dialog.dismiss();
//                            fillDataRecyclerView();
                        }
                    }

                    @Override
                    public void onFailure(Call<Ram> call, Throwable t) {
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

    @SuppressLint("NotifyDataSetChanged")
    private void fillDataRecyclerView() {
        list.clear();
        list.addAll(listBackUp);
        adapter.notifyDataSetChanged();
    }

    private void updateData(Ram ram) { // thêm sửa mới
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_ram, null);
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

        edTenRam = view.findViewById(R.id.dl_ram_edTenRam);
        Button btnSave = view.findViewById(R.id.dl_ram_btnSave);
        TextView tvTitle = view.findViewById(R.id.dl_ram_tvTitle);
        ImageView imgView = view.findViewById(R.id.dl_ram_imageView);

        tvTitle.setText("Cập Nhật Ram");
        edTenRam.setText(ram.getRAM()+"");
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Validate()){


                Number tenram = Integer.parseInt(edTenRam.getText().toString().trim());
                ApiRamService apiRamService = ApiRetrofit.getApiRamService();
                Call<Ram> call = apiRamService.putRam(ram.get_id(), new Ram(tenram));
                call.enqueue(new Callback<Ram>() {
                    @Override
                    public void onResponse(Call<Ram> call, Response<Ram> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                            getData();
                            dialog.dismiss();
//                            fillDataRecyclerView();
                        }
                    }

                    @Override
                    public void onFailure(Call<Ram> call, Throwable t) {
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
        if(edTenRam.getText().toString().isEmpty()){
            edTenRam.setError("Không được để trống!!");
            return false;
        }else if(!Pattern.matches("\\d+", edTenRam.getText().toString())){
            edTenRam.setError("Phải nhập là số!!");
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
