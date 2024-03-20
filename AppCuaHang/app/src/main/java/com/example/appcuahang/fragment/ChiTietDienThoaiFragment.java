package com.example.appcuahang.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.widget.Toolbar;
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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appcuahang.R;
import com.example.appcuahang.adapter.ChiTietAdapter;
import com.example.appcuahang.adapter.DanhGiaAdapter;
import com.example.appcuahang.api.ApiRetrofit;
import com.example.appcuahang.api.ApiService;
import com.example.appcuahang.interface_adapter.IItemDetailPhoneListenner;
import com.example.appcuahang.model.Brand;
import com.example.appcuahang.model.DetailPhone;
import com.example.appcuahang.model.DungLuong;
import com.example.appcuahang.model.Mau;
import com.example.appcuahang.model.Phone;
import com.example.appcuahang.model.Ram;
import com.example.appcuahang.model.Rating;
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

public class ChiTietDienThoaiFragment extends Fragment {


    TextView tvTenDienThoai;
    ImageView imgChiTiet;
    RecyclerView rc_chiTiet , rc_DanhGia;
    List<DetailPhone> list;
    List<Rating> ratingList;
    ChiTietAdapter adapter;
    DanhGiaAdapter danhGiaAdapter;
    GridLayoutManager manager;
    LinearLayoutManager linearLayoutManager;
    final private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("image");
    final private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    TextView tvKichThuoc, tvCongNgheManHinh, tvCamera, tvCPU, tvPin, tvHeDieuHanh, tvDoPhanGiai, tvNamSanXuat, tvBaoHanh, tvMoTa , tvSoLuongComment;
    Toolbar mToolbar;
    Spinner spHangSX, spMau, spRam, spDungLuong;
    EditText edTen, edSoLuong, edGiaTien;

    String idSpMau, idSpRam, idSpDungLuong;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_chi_tiet_dien_thoai, container, false);
        initView(view);
        initVariable();
        action();
        return view;
    }

    private void initView(View view) {
        tvTenDienThoai = view.findViewById(R.id.chitiet_tvTenDienThoai);
        imgChiTiet = view.findViewById(R.id.chitiet_imgDienThoai);
        rc_chiTiet = view.findViewById(R.id.rc_chiTiet);
        rc_DanhGia = view.findViewById(R.id.rc_danhGia);
        tvKichThuoc = view.findViewById(R.id.chitiet_tvKichThuoc);
        tvCongNgheManHinh = view.findViewById(R.id.chitiet_tvCongNgheManHinh);
        tvCamera = view.findViewById(R.id.chitiet_tvCamera);
        tvCPU = view.findViewById(R.id.chitiet_tvCPU);
        tvPin = view.findViewById(R.id.chitiet_tvPin);
        tvHeDieuHanh = view.findViewById(R.id.chitiet_tvHeDieuHanh);
        tvDoPhanGiai = view.findViewById(R.id.chitiet_tvDoPhanGiai);
        tvNamSanXuat = view.findViewById(R.id.chitiet_tvNamSanXuat);
        tvBaoHanh = view.findViewById(R.id.chitiet_tvBaoHanh);
        tvMoTa = view.findViewById(R.id.chitiet_tvMoTa);
        tvSoLuongComment = view.findViewById(R.id.chitiet_tvSoLuongComment);
    }

    private void initVariable() {
        list = new ArrayList<>();
        FirebaseApp.initializeApp(getContext());
        manager = new GridLayoutManager(getContext(), 2);
        rc_chiTiet.setLayoutManager(manager);
        adapter = new ChiTietAdapter(getContext());
        adapter.setOnClick(new IItemDetailPhoneListenner() {
            @Override
            public void showDetailPhone(DetailPhone idDetailPhone) {

            }

            @Override
            public void editDetail(DetailPhone idDetailPhone) {
                dialogUpdateDetail(idDetailPhone);
            }
        });
        adapter.setData(list);
        rc_chiTiet.setAdapter(adapter);
        //danh gia list
        ratingList = new ArrayList<>();
        linearLayoutManager = new LinearLayoutManager(getContext());
        rc_DanhGia.setLayoutManager(linearLayoutManager);;
        danhGiaAdapter = new DanhGiaAdapter(getContext());
        danhGiaAdapter.setData(ratingList);
        rc_DanhGia.setAdapter(danhGiaAdapter);
    }

    private void action() {
//        Intent intent = getActivity().getIntent();
//        Bundle bundle = intent.getExtras();
//        Bundle bundle = this.getArguments();
//        Phone phone = (Phone) bundle.getSerializable("detailPhone");
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            Phone phone = (Phone) bundle.getSerializable("detailPhone"); // Lấy dữ liệu từ Bundle bằng key
            // Sử dụng dữ liệu ở đây
            tvTenDienThoai.setText("" + phone.getTenDienThoai());
            if (phone.getHinhAnh() == null) {
                imgChiTiet.setImageResource(R.drawable.baseline_phone_iphone_24);
            } else {
                Picasso.get().load(phone.getHinhAnh()).into(imgChiTiet);
            }
            getData(phone.get_id());
            getThongSoKyThuat(phone);
        }
    }

    private void getData(String id) {
        ApiService apiService = ApiRetrofit.getApiService();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    DetailPhone model = dataSnapshot.getValue(DetailPhone.class);
                    list.add(model);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Call<List<DetailPhone>> call = apiService.getChiTiet(id);

        call.enqueue(new Callback<List<DetailPhone>>() {
            @Override
            public void onResponse(Call<List<DetailPhone>> call, Response<List<DetailPhone>> response) {
                if (response.isSuccessful()) {
                    List<DetailPhone> data = response.body();
                    list.clear();
                    list.addAll(data);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DetailPhone>> call, Throwable t) {
                Log.e("detail", t.getMessage());
            }
        });

        //get list danh gia

        Call<List<Rating>> ratingListCall = apiService.getDanhGia();
        ratingListCall.enqueue(new Callback<List<Rating>>() {
            @Override
            public void onResponse(Call<List<Rating>> call, Response<List<Rating>> response) {
                if (response.isSuccessful()) {
                    List<Rating> data = response.body();
                    ratingList.clear();
                    ratingList.addAll(data);
                    danhGiaAdapter.notifyDataSetChanged();
                    tvSoLuongComment.setText("("+data.size()+")");
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

    private void getThongSoKyThuat(Phone phone) {
        tvKichThuoc.setText("Kích thước: " + phone.getKichThuoc());
        tvCongNgheManHinh.setText("Công nghệ màn hình: " + phone.getCongNgheManHinh());
        tvCamera.setText("Camera: " + phone.getCamera());
        tvCPU.setText("CPU: " + phone.getCpu());
        tvPin.setText("Pin: " + phone.getPin());
        tvHeDieuHanh.setText("Hệ điều hành: " + phone.getHeDieuHanh());
        tvDoPhanGiai.setText("Độ phân giải: " + phone.getDoPhanGiai());
        tvNamSanXuat.setText("Năm sản xuất: " + phone.getNamSanXuat());
        tvBaoHanh.setText("Thời gian bảo hành: " + phone.getThoiGianBaoHanh());
        tvMoTa.setText("Mô tả thêm: " + phone.getMoTaThem());
    }

    private void dialogUpdateDetail(DetailPhone detailPhone) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_them_chi_tiet, null);
        builder.setView(view);
        Dialog dialogDetail = builder.create();
        dialogDetail.show();
        Window window = dialogDetail.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);
        //initView
        spMau = view.findViewById(R.id.dl_chitiet_spMau);
        spRam = view.findViewById(R.id.dl_chitiet_spRam);
        spDungLuong = view.findViewById(R.id.dl_chitiet_spDungLuong);
        edTen = view.findViewById(R.id.dl_chitiet_edTenDienThoai);
        edSoLuong = view.findViewById(R.id.dl_chitiet_edSoLuong);
        edGiaTien = view.findViewById(R.id.dl_chitiet_edGiaTien);
        Button chitiet_button = view.findViewById(R.id.dl_chitiet_button);
        ImageView imgClose = view.findViewById(R.id.dl_chitiet_imageView);
        TextView tvTitle = view.findViewById(R.id.dl_chitiet_tvTitle);
        tvTitle.setText("Câp Nhật Chi Tiết Điện Thoại");
        chitiet_button.setText("Cập Nhật");
        getDataSpinnerChiTiet(detailPhone);
        //data spinner
        ApiService apiService = ApiRetrofit.getApiService();
        //set edittext
        edTen.setText("" + detailPhone.getMaDienThoai().getTenDienThoai());
        edGiaTien.setText(""+detailPhone.getGiaTien());
        edSoLuong.setText(""+detailPhone.getSoLuong());
        chitiet_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Integer strSoLuong = Integer.parseInt("" + edSoLuong.getText().toString());
                Integer strGiaTien = Integer.parseInt("" + edGiaTien.getText().toString());
                Call<DetailPhone> call = apiService.putChiTietDienThoai(detailPhone.get_id(), new DetailPhone(strSoLuong, strGiaTien, new Phone(detailPhone.getMaDienThoai().get_id()), new Mau(idSpMau), new DungLuong(idSpDungLuong), new Ram(idSpRam)));
                call.enqueue(new Callback<DetailPhone>() {
                    @Override
                    public void onResponse(Call<DetailPhone> call, Response<DetailPhone> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getContext(), "Thêm mới thành công", Toast.LENGTH_SHORT).show();
                            getData(detailPhone.get_id());
                            action();
                            dialogDetail.dismiss();
                        }
                    }

                    @Override
                    public void onFailure(Call<DetailPhone> call, Throwable t) {

                    }
                });
            }
        });
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialogDetail.dismiss();
            }
        });

    }

    private void getDataSpinnerChiTiet(DetailPhone detailPhone) {
        ApiService apiService = ApiRetrofit.getApiService();
        //get ram
        Call<List<Ram>> call = apiService.getRamSpinner();
        call.enqueue(new Callback<List<Ram>>() {
            @Override
            public void onResponse(Call<List<Ram>> call, Response<List<Ram>> response) {
                if (response.isSuccessful()){
                    List<Ram> item = response.body();
                    ArrayAdapter<Ram> ramArrayAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, item);
                    spRam.setAdapter(ramArrayAdapter);
                    for (int i = 0; i < item.size(); i++) {
                        if (detailPhone.getMaRam().get_id().equals(item.get(i).get_id())){
                            spRam.setSelection(i);
                        }
                    }
                    spRam.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            idSpRam = item.get(i).get_id();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Ram>> call, Throwable t) {
                Log.e("Ram", t.getMessage());
            }
        });
        //dungluong
        Call<List<DungLuong>> callDungLuong = apiService.getDungLuongSpinner();
        callDungLuong.enqueue(new Callback<List<DungLuong>>() {
            @Override
            public void onResponse(Call<List<DungLuong>> call, Response<List<DungLuong>> response) {
                if (response.isSuccessful()){
                    List<DungLuong> item = response.body();
                    ArrayAdapter dungLuongAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, item);
                    spDungLuong.setAdapter(dungLuongAdapter);
                    for (int i = 0; i < item.size(); i++) {
                        if (detailPhone.getMaDungLuong().get_id().equals(item.get(i).get_id())){
                            spDungLuong.setSelection(i);
                        }
                    }
                    spDungLuong.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            idSpDungLuong = item.get(i).get_id();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<DungLuong>> call, Throwable t) {
                Log.e("DungLuong", t.getMessage());
            }
        });
        //mau
        Call<List<Mau>> callMau = apiService.getMauSpinner();
        callMau.enqueue(new Callback<List<Mau>>() {
            @Override
            public void onResponse(Call<List<Mau>> call, Response<List<Mau>> response) {
                if (response.isSuccessful()){
                    List<Mau> item = response.body();
                    ArrayAdapter MauAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, item);
                    spMau.setAdapter(MauAdapter);
                    for (int i = 0; i < item.size(); i++) {
                        if (detailPhone.getMaMau().get_id().equals(item.get(i).get_id())){
                            spMau.setSelection(i);
                        }
                    }
                    spMau.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            idSpMau = item.get(i).get_id();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<Mau>> call, Throwable t) {
                Log.e("Mau", t.getMessage());
            }
        });
    }
}