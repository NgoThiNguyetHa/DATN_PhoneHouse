package com.example.appkhachhang.Fragment;

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
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appkhachhang.Adapter.AddressAdapter;
import com.example.appkhachhang.Api.Address_API;
import com.example.appkhachhang.Api.ApiRetrofit;
import com.example.appkhachhang.Interface_Adapter.IItemAddressListenner;
import com.example.appkhachhang.Model.AddressDelivery;
import com.example.appkhachhang.Model.User;
import com.example.appkhachhang.R;
import com.example.appkhachhang.untils.MySharedPreferences;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AddressFragment extends Fragment {
    RecyclerView rc_address;
    List<AddressDelivery> list = new ArrayList<>();
    List<AddressDelivery> listBackUp;
    AddressAdapter adapter;
    LinearLayoutManager manager;
    EditText edTenNguoiNhan;
    EditText edSDT;
    EditText edDiaChi;
    Button btnAddress;
    MySharedPreferences mySharedPreferences;

    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_address, container, false);
        ((Activity) getContext()).setTitle("Địa chỉ");
        initView(view);
        getData();
        initVariable();
        fillDataRecyclerView();
        btnAddress = view.findViewById(R.id.btnAddress);
        btnAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog();
            }
        });
        return view;
    }

    private void initView(View view) {
        rc_address = view.findViewById(R.id.rc_address);
    }

    private void initVariable() {
        list = new ArrayList<>();
        listBackUp = new ArrayList<>();
        manager = new LinearLayoutManager(getContext());
        rc_address.setLayoutManager(manager);
        adapter = new AddressAdapter(getContext(), new IItemAddressListenner() {
            @Override
            public void editAddress(AddressDelivery isAddress) {
                updateData(isAddress);
            }
        });
        adapter.setData(list);
        rc_address.setAdapter(adapter);
    }
    private void getData() {
        list = new ArrayList<>();
        manager = new LinearLayoutManager(getContext());
        rc_address.setLayoutManager(manager);
        Address_API address_api = ApiRetrofit.getApiAddress();
        mySharedPreferences = new MySharedPreferences(getContext());
        Call<List<AddressDelivery>> call = address_api.getDiaChi(mySharedPreferences.getUserId());
        call.enqueue(new Callback<List<AddressDelivery>>() {
            @Override
            public void onResponse(Call<List<AddressDelivery>> call, Response<List<AddressDelivery>> response) {
                if (response.isSuccessful()) {
                    List<AddressDelivery> data = response.body();

                    list.clear();
                    list.addAll(data);
                    adapter.notifyDataSetChanged();
                } else {
                    // Handle error
                    Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<AddressDelivery>> call, Throwable t) {
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
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_address, null);
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

        edTenNguoiNhan = view.findViewById(R.id.dl_edTenNguoiNhan);
        edSDT = view.findViewById(R.id.dl_edSDT);
        edDiaChi = view.findViewById(R.id.dl_edDiaChi);

        Button btnSave = view.findViewById(R.id.yesButton);
        TextView tvTitle = view.findViewById(R.id.dl_mau_tvTitle);
        ImageView imgView = view.findViewById(R.id.dl_mau_imageView);

        tvTitle.setText("Dialog Thêm Địa chỉ");
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Validate()){
                    String tenNguoiNhan = edTenNguoiNhan.getText().toString().trim();
                    String soDienThoai = edSDT.getText().toString().trim();
                    String diaChi = edDiaChi.getText().toString().trim();
                    Address_API address_api = ApiRetrofit.getApiAddress();
                    mySharedPreferences = new MySharedPreferences(getContext());
                    Call<AddressDelivery> call = address_api.postDiaChi(new AddressDelivery(soDienThoai , tenNguoiNhan , diaChi , new User(mySharedPreferences.getUserId())));
                    call.enqueue(new Callback<AddressDelivery>() {
                        @Override
                        public void onResponse(Call<AddressDelivery> call, Response<AddressDelivery> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(), "Thêm thành công", Toast.LENGTH_SHORT).show();
                                getData();
                                dialog.dismiss();
                                fillDataRecyclerView();
                            }
                        }

                        @Override
                        public void onFailure(Call<AddressDelivery> call, Throwable t) {
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

    //Update
    private void updateData(AddressDelivery addressDelivery) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_address, null);
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

        edTenNguoiNhan = view.findViewById(R.id.dl_edTenNguoiNhan);
        edSDT = view.findViewById(R.id.dl_edSDT);
        edDiaChi = view.findViewById(R.id.dl_edDiaChi);

        Button btnSave = view.findViewById(R.id.yesButton);
        TextView tvTitle = view.findViewById(R.id.dl_mau_tvTitle);
        ImageView imgView = view.findViewById(R.id.dl_mau_imageView);

        tvTitle.setText("Cập Nhật Địa Chỉ");
        edTenNguoiNhan.setText(addressDelivery.getTenNguoiNhan());
        edDiaChi.setText(addressDelivery.getDiaChi());
        edSDT.setText(addressDelivery.getSdt());
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Validate()){
                    String tenNguoiNhan = edTenNguoiNhan.getText().toString().trim();
                    String soDienThoai = edSDT.getText().toString().trim();
                    String diaChi = edDiaChi.getText().toString().trim();
                    Address_API address_api = ApiRetrofit.getApiAddress();
                    Call<AddressDelivery> call = address_api.putDiaChi(new AddressDelivery(soDienThoai , tenNguoiNhan , diaChi , new User(mySharedPreferences.getUserId())) , addressDelivery.get_id());
                    call.enqueue(new Callback<AddressDelivery>() {
                        @Override
                        public void onResponse(Call<AddressDelivery> call, Response<AddressDelivery> response) {
                            if (response.isSuccessful()) {
                                Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                getData();
                                dialog.dismiss();
                                fillDataRecyclerView();
                            }
                        }

                        @Override
                        public void onFailure(Call<AddressDelivery> call, Throwable t) {
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

    private boolean Validate(){
        if(edTenNguoiNhan.getText().toString().isEmpty()){
            edTenNguoiNhan.setError("Không được để trống!!");
            return false;
        }else if(edDiaChi.getText().toString().isEmpty()){
            edDiaChi.setError("Không được để trống!!");
            return false;
        }else if(edSDT.getText().toString().isEmpty()){
            edSDT.setError("Không được để trống!!");
            return false;
        }
        return true;
    }
}
