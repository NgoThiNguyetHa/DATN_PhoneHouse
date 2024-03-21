package com.example.appcuahang.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appcuahang.R;
import com.example.appcuahang.adapter.BrandAdapter;
import com.example.appcuahang.adapter.ChiTietAdapter;
import com.example.appcuahang.api.ApiRetrofit;
import com.example.appcuahang.api.ApiService;
import com.example.appcuahang.fragment.ChiTietDienThoaiFragment;
import com.example.appcuahang.interface_adapter.IItemBrandListenner;
import com.example.appcuahang.interface_adapter.IItemDetailPhoneListenner;
import com.example.appcuahang.interface_adapter.IItemMauListenner;
import com.example.appcuahang.model.Brand;
import com.example.appcuahang.model.DetailPhone;
import com.example.appcuahang.model.DungLuong;
import com.example.appcuahang.model.Mau;
import com.example.appcuahang.model.Phone;
import com.example.appcuahang.model.Ram;
import com.example.appcuahang.untils.MySharedPreferences;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietDienThoaiActivity extends AppCompatActivity {
    TextView tvTenDienThoai;
    ImageView imgChiTiet;
    RecyclerView rc_chiTiet;
    List<DetailPhone> list;
    ChiTietAdapter adapter;
    GridLayoutManager manager;

    final private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("image");
    final private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    TextView tvKichThuoc , tvCongNgheManHinh , tvCamera, tvCPU , tvPin,tvHeDieuHanh, tvDoPhanGiai, tvNamSanXuat , tvBaoHanh , tvMoTa;
    Toolbar mToolbar;
    Spinner spHangSX, spMau, spRam, spDungLuong;
    EditText edTen, edSoLuong, edGiaTien;

    String idSpMau, idSpRam, idSpDungLuong;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_dien_thoai);
//        initView();
//        initVariable();
//        action();
        ChiTietDienThoaiFragment fragment = new ChiTietDienThoaiFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.add(R.id.chiTiet_container, fragment);
        fragmentTransaction.commit();
    }

//    private void initView(){
//        tvTenDienThoai = findViewById(R.id.chitiet_tvTenDienThoai);
//        imgChiTiet = findViewById(R.id.chitiet_imgDienThoai);
//        rc_chiTiet = findViewById(R.id.rc_chiTiet);
//        mToolbar = findViewById(R.id.chitiet_toolBar);
//        setSupportActionBar(mToolbar);
//        setTitle("Chi Tiết Điện Thoại");
//        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                onBackPressed(); // Implemented by activity
//            }
//        });
//        if (getSupportActionBar() != null){
//            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//            getSupportActionBar().setDisplayShowHomeEnabled(true);
//        }
//    }
//    private void initVariable(){
//        list = new ArrayList<>();
//        FirebaseApp.initializeApp(getApplicationContext());
//        manager = new GridLayoutManager(getApplicationContext(), 2);
//        rc_chiTiet.setLayoutManager(manager);
//        adapter = new ChiTietAdapter(getApplicationContext());
//        adapter.setOnClick(new IItemDetailPhoneListenner() {
//            @Override
//            public void showDetailPhone(DetailPhone idDetailPhone) {
//
//            }
//
//            @Override
//            public void editDetail(DetailPhone idDetailPhone) {
//                dialogUpdateDetail(idDetailPhone);
//            }
//        });
//        adapter.setData(list);
//        rc_chiTiet.setAdapter(adapter);
//    }
//    private void action(){
//        Intent intent = this.getIntent();
//        Bundle bundle = intent.getExtras();
//        Phone phone = (Phone) bundle.getSerializable("detailPhone");
//        tvTenDienThoai.setText(""+phone.getTenDienThoai());
//        if (phone.getHinhAnh() == null){
//            imgChiTiet.setImageResource(R.drawable.baseline_phone_iphone_24);
//        }else {
//            Picasso.get().load(phone.getHinhAnh()).into(imgChiTiet);
//        }
//        getData(phone.get_id());
//        getThongSoKyThuat(phone);
//    }
//
//    private void getData(String id) {
//        ApiService apiService = ApiRetrofit.getApiService();
//        reference.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
//                    DetailPhone model = dataSnapshot.getValue(DetailPhone.class);
//                    list.add(model);
//                }
//                adapter.notifyDataSetChanged();
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//
//        Call<List<DetailPhone>> call = apiService.getChiTiet(id);
//
//        call.enqueue(new Callback<List<DetailPhone>>() {
//            @Override
//            public void onResponse(Call<List<DetailPhone>> call, Response<List<DetailPhone>> response) {
//                if (response.isSuccessful()) {
//                    List<DetailPhone> data = response.body();
//                    list.clear();
//                    list.addAll(data);
//                    adapter.notifyDataSetChanged();
//                } else {
//                    Toast.makeText(getApplicationContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<DetailPhone>> call, Throwable t) {
//                Log.e("detail", t.getMessage());
//            }
//        });
//    }
//
//    private void getThongSoKyThuat(Phone phone){
//        tvKichThuoc = findViewById(R.id.chitiet_tvKichThuoc);
//        tvCongNgheManHinh = findViewById(R.id.chitiet_tvCongNgheManHinh);
//        tvCamera = findViewById(R.id.chitiet_tvCamera);
//        tvCPU = findViewById(R.id.chitiet_tvCPU);
//        tvPin = findViewById(R.id.chitiet_tvPin);
//        tvHeDieuHanh = findViewById(R.id.chitiet_tvHeDieuHanh);
//        tvDoPhanGiai = findViewById(R.id.chitiet_tvDoPhanGiai);
//        tvNamSanXuat = findViewById(R.id.chitiet_tvNamSanXuat);
//        tvBaoHanh = findViewById(R.id.chitiet_tvBaoHanh);
//        tvMoTa = findViewById(R.id.chitiet_tvMoTa);
//        tvKichThuoc.setText("Kích thước: "+phone.getKichThuoc());
//        tvCongNgheManHinh.setText("Công nghệ màn hình: "+phone.getCongNgheManHinh());
//        tvCamera.setText("Camera: "+phone.getCamera());
//        tvCPU.setText("CPU: "+phone.getCpu());
//        tvPin.setText("Pin: "+phone.getPin());
//        tvHeDieuHanh.setText("Hệ điều hành: "+phone.getHeDieuHanh());
//        tvDoPhanGiai.setText("Độ phân giải: "+phone.getDoPhanGiai());
//        tvNamSanXuat.setText("Năm sản xuất: "+phone.getNamSanXuat());
//        tvBaoHanh.setText("Thời gian bảo hành: "+phone.getThoiGianBaoHanh());
//        tvMoTa.setText("Mô tả thêm: "+phone.getMoTaThem());
//    }
//
//    private void dialogUpdateDetail(DetailPhone detailPhone) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getApplicationContext());
//        View view = LayoutInflater.from(getApplicationContext()).inflate(R.layout.dialog_them_chi_tiet, null);
//        builder.setView(view);
//        Dialog dialogDetail = builder.create();
//        dialogDetail.show();
//        Window window = dialogDetail.getWindow();
//        if (window == null) {
//            return;
//        }
//        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
//        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        WindowManager.LayoutParams windowAttributes = window.getAttributes();
//        windowAttributes.gravity = Gravity.CENTER;
//        window.setAttributes(windowAttributes);
//        //initView
//        spMau = view.findViewById(R.id.dl_chitiet_spMau);
//        spRam = view.findViewById(R.id.dl_chitiet_spRam);
//        spDungLuong = view.findViewById(R.id.dl_chitiet_spDungLuong);
//        edTen = view.findViewById(R.id.dl_chitiet_edTenDienThoai);
//        edSoLuong = view.findViewById(R.id.dl_chitiet_edSoLuong);
//        edGiaTien = view.findViewById(R.id.dl_chitiet_edGiaTien);
//        Button chitiet_button = view.findViewById(R.id.dl_chitiet_button);
//        ImageView imgClose = view.findViewById(R.id.dl_chitiet_imageView);
//        TextView tvTitle = view.findViewById(R.id.dl_chitiet_tvTitle);
//        tvTitle.setText("Câp Nhật Chi Tiết Điện Thoại");
//        chitiet_button.setText("Cập Nhật");
//        getDataSpinnerChiTiet(detailPhone);
//        ApiService apiService = ApiRetrofit.getApiService();
//        edTen.setText("" + detailPhone.getMaDienThoai().getTenDienThoai());
//        chitiet_button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Integer strSoLuong = Integer.parseInt("" + edSoLuong.getText().toString());
//                Integer strGiaTien = Integer.parseInt("" + edGiaTien.getText().toString());
//                Call<DetailPhone> call = apiService.putChiTietDienThoai(detailPhone.get_id(),new DetailPhone(strSoLuong, strGiaTien, new Phone(detailPhone.getMaDienThoai().get_id()), new Mau(idSpMau), new DungLuong(idSpDungLuong), new Ram(idSpRam)));
//                call.enqueue(new Callback<DetailPhone>() {
//                    @Override
//                    public void onResponse(Call<DetailPhone> call, Response<DetailPhone> response) {
//                        if (response.isSuccessful()) {
//                            Toast.makeText(getApplicationContext(), "Thêm mới thành công", Toast.LENGTH_SHORT).show();
//                            dialogDetail.dismiss();
//                        }
//                    }
//
//                    @Override
//                    public void onFailure(Call<DetailPhone> call, Throwable t) {
//
//                    }
//                });
//            }
//        });
//        imgClose.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                dialogDetail.dismiss();
//            }
//        });
//
//    }
//
//    private void getDataSpinnerChiTiet(DetailPhone detailPhone) {
//        ApiService apiService = ApiRetrofit.getApiService();
//        Call<List<Mau>> call = apiService.getMauSpinner();
//        call.enqueue(new Callback<List<Mau>>() {
//            @Override
//            public void onResponse(Call<List<Mau>> call, Response<List<Mau>> response) {
//                if (response.isSuccessful()) {
//                    List<Mau> data = response.body();
//                    ArrayAdapter MauAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, data);
//                    spMau.setAdapter(MauAdapter);
//                    for (int i = 0; i < data.size(); i++) {
//                        if (detailPhone.getMaMau().equals(data.get(i).get_id())){
//                            spMau.setSelection(i);
//                        }
//                    }
//                    spMau.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                        @Override
//                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                            idSpMau = data.get(i).get_id();
//                        }
//
//                        @Override
//                        public void onNothingSelected(AdapterView<?> adapterView) {
//
//                        }
//                    });
//                } else {
//                    Toast.makeText(getApplicationContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Mau>> call, Throwable t) {
//                Log.e("Mau", t.getMessage());
//            }
//        });
//
//        Call<List<Ram>> callRam = apiService.getRam();
//        callRam.enqueue(new Callback<List<Ram>>() {
//            @Override
//            public void onResponse(Call<List<Ram>> call, Response<List<Ram>> response) {
//                if (response.isSuccessful()) {
//                    List<Ram> data = response.body();
//                    ArrayAdapter MauAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, data);
//                    spRam.setAdapter(MauAdapter);
//                    for (int i = 0; i < data.size(); i++) {
//                        if (detailPhone.getMaRam().equals(data.get(i).get_id())){
//                            spRam.setSelection(i);
//                        }
//                    }
//                    spRam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                        @Override
//                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                            idSpRam = data.get(i).get_id();
//                        }
//
//                        @Override
//                        public void onNothingSelected(AdapterView<?> adapterView) {
//
//                        }
//                    });
//                } else {
//                    Toast.makeText(getApplicationContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Ram>> call, Throwable t) {
//                Log.e("Ram", t.getMessage());
//            }
//        });
//
//
//        Call<List<DungLuong>> callDungLuong = apiService.getDungLuong();
//        callDungLuong.enqueue(new Callback<List<DungLuong>>() {
//            @Override
//            public void onResponse(Call<List<DungLuong>> call, Response<List<DungLuong>> response) {
//                if (response.isSuccessful()) {
//                    List<DungLuong> data = response.body();
//                    ArrayAdapter MauAdapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, data);
//                    spDungLuong.setAdapter(MauAdapter);
//                    for (int i = 0; i < data.size(); i++) {
//                        if (detailPhone.getMaDungLuong().equals(data.get(i).get_id())){
//                            spDungLuong.setSelection(i);
//                        }
//                    }
//                    spDungLuong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//                        @Override
//                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                            idSpDungLuong = data.get(i).get_id();
//                        }
//
//                        @Override
//                        public void onNothingSelected(AdapterView<?> adapterView) {
//
//                        }
//                    });
//                } else {
//                    Toast.makeText(getApplicationContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<DungLuong>> call, Throwable t) {
//                Log.e("DungLuong", t.getMessage());
//            }
//        });
//    }

}