package com.example.appkhachhang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appkhachhang.Adapter.DiaChiNhanHangAdapter;
import com.example.appkhachhang.Adapter.DienThoaiThanhToanAdapter;
import com.example.appkhachhang.Api.Address_API;
import com.example.appkhachhang.Api.ApiRetrofit;
import com.example.appkhachhang.Api.ApiService;
import com.example.appkhachhang.Model.AddressDelivery;
import com.example.appkhachhang.Model.ChiTietDienThoai;
import com.example.appkhachhang.Model.ChiTietGioHang;
import com.example.appkhachhang.Model.ChiTietHoaDon;
import com.example.appkhachhang.Model.CuaHang;
import com.example.appkhachhang.Model.HoaDon;
import com.example.appkhachhang.Model.Store;
import com.example.appkhachhang.Model.User;
import com.example.appkhachhang.untils.MySharedPreferences;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import java.util.zip.Inflater;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThanhToanActivity extends AppCompatActivity {
    User user;
    TextView tvTen, tvSdt, tvGhiChu, tvDiaChi, tvTongTienHang, tvPhiVanChuyen, tvTongThanhToan, tvTongHoaDon;
    ImageView imgDiaChi;
    LinearLayout ln_ghiChu;
    RecyclerView rc_listChon, rcDiaChiNhanHang;
    LinearLayoutManager linearLayoutManager;
    DienThoaiThanhToanAdapter adapter;
    Spinner spnPhuongThucThanhToan;
    List<AddressDelivery> list;
    DiaChiNhanHangAdapter adapterDiaChi;
    String idDiaChi, selectedItem;
    List<ChiTietHoaDon> chiTietHoaDons;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);
        SharedPreferences pref = getSharedPreferences("user_info", MODE_PRIVATE);
        Gson gson = new Gson();
        String user_json = pref.getString("user", "abc");
        user = gson.fromJson(user_json, User.class);


        tvTen = findViewById(R.id.tv_tenKhachHang);
        tvSdt = findViewById(R.id.tv_sdtKhachHang);
        tvDiaChi = findViewById(R.id.tv_DiaChiKhachHang);
        imgDiaChi = findViewById(R.id.imgDiaChi);
        rc_listChon = findViewById(R.id.rc_listChon);
        tvTongTienHang = findViewById(R.id.tv_tongTienHang);
        tvPhiVanChuyen = findViewById(R.id.tv_PhiVanChuyen);
        tvTongThanhToan = findViewById(R.id.tv_tongThanhToan);
        tvTongHoaDon = findViewById(R.id.tvTongHoaDon);
        spnPhuongThucThanhToan = findViewById(R.id.spn_PhuongThucThanhToan);
        list = new ArrayList<>();
        getData(user.get_id());
        adapterDiaChi = new DiaChiNhanHangAdapter(ThanhToanActivity.this, list);


        imgDiaChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(ThanhToanActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View dialog_view = inflater.inflate(R.layout.dialog_diachi, null);
                rcDiaChiNhanHang = dialog_view.findViewById(R.id.rc_diaChiNhanHang);
                linearLayoutManager = new LinearLayoutManager(getApplicationContext());
                linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
                rcDiaChiNhanHang.setLayoutManager(linearLayoutManager);
                builder.setView(dialog_view);

                rcDiaChiNhanHang.setAdapter(adapterDiaChi);

                builder.setPositiveButton("Chọn", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        adapterDiaChi.setOnItemCheckedChangeListener(new DiaChiNhanHangAdapter.OnItemCheckedChangeListener() {
                            @Override
                            public void onItemCheckedChanged(AddressDelivery addressDelivery) {
                                tvTen.setText(addressDelivery.getTenNguoiNhan() + " | ");
                                tvSdt.setText(addressDelivery.getSdt());
                                tvDiaChi.setText("Địa chỉ: " + addressDelivery.getDiaChi());
                                idDiaChi = addressDelivery.get_id();
                            }
                        });
                    }
                });
                builder.setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });

                builder.create().show();
            }
        });
        linearLayoutManager = new LinearLayoutManager(this);
        linearLayoutManager.setOrientation(RecyclerView.VERTICAL);
        rc_listChon.setLayoutManager(linearLayoutManager);
        this.setTitle("Thanh toán");
        Intent intent = getIntent();
        String json = intent.getStringExtra("chiTietGioHangList");
        Gson gson1 = new Gson();
        Type type = new TypeToken<List<ChiTietGioHang>>() {}.getType();
        List<ChiTietGioHang> chiTietGioHangList = gson1.fromJson(json, type);
        adapter = new DienThoaiThanhToanAdapter(chiTietGioHangList, this);
        rc_listChon.setAdapter(adapter);

        int tongTien = 0;
        for (int i = 0; i < chiTietGioHangList.size(); i++) {
            tongTien += Integer.parseInt(chiTietGioHangList.get(i).getGiaTien().toString())*Integer.parseInt(chiTietGioHangList.get(i).getSoLuong().toString());
        }

        int phiVanChuyen = 0;
        tvTongTienHang.setText(tongTien + "");
        tvPhiVanChuyen.setText(phiVanChuyen + "");
        tvTongThanhToan.setText(tongTien + phiVanChuyen +"");
        tvTongHoaDon.setText(tongTien + phiVanChuyen +"");
        int tongThanhToan = tongTien+phiVanChuyen;

        ArrayAdapter<CharSequence> adapterSpn = ArrayAdapter.createFromResource(this, R.array.spn_phuongthuc, android.R.layout.simple_list_item_1);
        spnPhuongThucThanhToan.setDropDownWidth(android.R.layout.simple_spinner_dropdown_item);
        spnPhuongThucThanhToan.setAdapter(adapterSpn);
        spnPhuongThucThanhToan.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                 selectedItem = adapterView.getItemAtPosition(i).toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        findViewById(R.id.btnThanhToan).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                List<String> addStores = new ArrayList<>();
                for (ChiTietGioHang item: chiTietGioHangList) {
                    String maCuaHang = item.getMaChiTietDienThoai().getMaDienThoai().getMaCuaHang().get_id();

                    if (!addStores.contains(maCuaHang)){
                        Calendar calendar = Calendar.getInstance();
                        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
                        String formattedDate = dateFormat.format(calendar.getTime());
                        HoaDon hoaDon = new HoaDon();
                        hoaDon.setTongTien(String.valueOf(tongThanhToan));
                        hoaDon.setNgayTao(formattedDate);
                        hoaDon.setPhuongThucThanhToan(selectedItem);
                        hoaDon.setMaKhachHang(new User(user.get_id()));
                        hoaDon.setMaCuaHang(new Store(maCuaHang));
                        hoaDon.setMaDiaChiNhanHang(new AddressDelivery(idDiaChi));
                        hoaDon.setTrangThaiNhanHang("Đang xử lý");
                        ApiRetrofit.getApiService().addHoaDon(hoaDon).enqueue(new Callback<HoaDon>() {
                            @Override
                            public void onResponse(Call<HoaDon> call, Response<HoaDon> response) {
                                if (response.body()!=null){
                                    Toast.makeText(ThanhToanActivity.this, "Thêm hóa đơn thành công", Toast.LENGTH_SHORT).show();
                                    chiTietHoaDons = new ArrayList<>();
                                    String hoaDonId = response.body().get_id();
                                    ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon();
                                    chiTietHoaDon.setMaHoaDon(new HoaDon(hoaDonId));
                                    chiTietHoaDon.setMaChiTietDienThoai(item.getMaChiTietDienThoai());
                                    chiTietHoaDon.setSoLuong(item.getSoLuong().toString());
                                    chiTietHoaDons.add(chiTietHoaDon);
                                    addChiTietHoaDon();
                                } else {
                                    Log.e("Error", "Response not successful");
                                }
                            }

                            @Override
                            public void onFailure(Call<HoaDon> call, Throwable t) {
                                Log.e("errorrr", "onFailure: " + t.getMessage());
                            }
                        });
                    }
                }
            }
        });
    }
    private void getData(String id) {
        Address_API address_api = ApiRetrofit.getApiAddress();
        Call<List<AddressDelivery>> call = address_api.getDiaChi(id);
        call.enqueue(new Callback<List<AddressDelivery>>() {
            @Override
            public void onResponse(Call<List<AddressDelivery>> call, Response<List<AddressDelivery>> response) {
                if (response.body()!=null) {
                    list.clear();
                    list.addAll(response.body());
                    adapterDiaChi.notifyDataSetChanged();
                    idDiaChi = list.get(0).get_id();
                    tvTen.setText(list.get(0).getTenNguoiNhan() + " | ");
                    tvSdt.setText(list.get(0).getSdt());
                    tvDiaChi.setText("Địa chỉ: " + list.get(0).getDiaChi());
                } else {
                    Toast.makeText(ThanhToanActivity.this, "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<AddressDelivery>> call, Throwable t) {
                // Handle failure
                Log.e("mau", t.getMessage());
            }
        });
    }
    void addChiTietHoaDon() {
        // Gọi API để thêm chi tiết hóa đơn
        ApiRetrofit.getApiService().addChiTietHoaDon(chiTietHoaDons).enqueue(new Callback<List<ChiTietHoaDon>>() {
            @Override
            public void onResponse(Call<List<ChiTietHoaDon>> call, Response<List<ChiTietHoaDon>> response) {
                if (response.body()!=null){
                    Log.d("themHoaDon", "onResponse: " + "Thêm thành công");
                }
            }

            @Override
            public void onFailure(Call<List<ChiTietHoaDon>> call, Throwable t) {
                Log.e("errorrr", "onFailure: " + t.getMessage() );
            }
        });
    }
}