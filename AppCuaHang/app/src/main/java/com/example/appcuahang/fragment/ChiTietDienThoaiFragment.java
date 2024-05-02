package com.example.appcuahang.fragment;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appcuahang.R;
import com.example.appcuahang.adapter.ApDungUuDaiAdapter;
import com.example.appcuahang.adapter.ChiTietAdapter;
import com.example.appcuahang.adapter.DanhGiaAdapter;
import com.example.appcuahang.adapter.UuDaiAdapter;
import com.example.appcuahang.api.ApiRetrofit;
import com.example.appcuahang.api.ApiService;
import com.example.appcuahang.api.ApiUuDaiService;
import com.example.appcuahang.interface_adapter.IItemDetailPhoneListenner;
import com.example.appcuahang.interface_adapter.IItemUuDaiListenner;
import com.example.appcuahang.model.Brand;
import com.example.appcuahang.model.Client;
import com.example.appcuahang.model.DetailPhone;
import com.example.appcuahang.model.DungLuong;
import com.example.appcuahang.model.Mau;
import com.example.appcuahang.model.Phone;
import com.example.appcuahang.model.Ram;
import com.example.appcuahang.model.Rating;
import com.example.appcuahang.model.UuDai;
import com.example.appcuahang.untils.MySharedPreferences;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ChiTietDienThoaiFragment extends Fragment {


    TextView tvTenDienThoai;
    ImageView imgChiTiet;
    RecyclerView rc_chiTiet , rc_DanhGia, rc__udch_uuDai;
    List<DetailPhone> list;
    List<UuDai> listUuDai;
    List<Rating> ratingList;
    ChiTietAdapter adapter;
    ApDungUuDaiAdapter adapterUuDai;
    DanhGiaAdapter danhGiaAdapter;
    GridLayoutManager manager;
    LinearLayoutManager linearLayoutManager, linearLayoutManagerUuDai;
    MySharedPreferences mySharedPreferences;
    final private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("image");
    final private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    TextView tvKichThuoc, tvCongNgheManHinh, tvCamera, tvCPU, tvPin, tvHeDieuHanh, tvDoPhanGiai, tvNamSanXuat, tvBaoHanh, tvMoTa , tvSoLuongComment;
    Toolbar mToolbar;
    Spinner spHangSX, spMau, spRam, spDungLuong;
    EditText edTen, edSoLuong, edGiaTien;

    String idSpMau, idSpRam, idSpDungLuong;
    Button dl_udch_btnHuy, dl_udch_btnCapNhat;
    LinearLayout linear_bottom;

    String maUuDai = "";
    Phone phone;
    Uri imageUri;

    ImageView uploadImageChiTiet;
    ProgressDialog progressDialog;
    List<Phone> phoneList;

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
        linear_bottom = view.findViewById(R.id.linear_bottom);


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
        listUuDai = new ArrayList<>();
    }

    private void action() {

//        Intent intent = getActivity().getIntent();
//        Bundle bundle = intent.getExtras();
//        Bundle bundle = this.getArguments();
//        Phone phone = (Phone) bundle.getSerializable("detailPhone");
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            phone = (Phone) bundle.getSerializable("detailPhone"); // Lấy dữ liệu từ Bundle bằng key
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
        mySharedPreferences = new MySharedPreferences(getContext());
        linear_bottom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (phone.getMaUuDai() != null) {
                    if (phone.getMaUuDai().get_id() != null) {
                        getDataUuDai(mySharedPreferences.getUserId());
                        dialogUuDai(phone.getMaUuDai().get_id());
                    }
                } else {
                    dialogUuDai("");
                }
            }
        });

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

        Call<List<Rating>> ratingListCall = apiService.getDanhGiaTheoDienThoai(id);
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
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_chi_tiet_dien_thoai, null);
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
        uploadImageChiTiet = view.findViewById(R.id.dl_chitiet_uploadImageView);
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
        if (detailPhone.getHinhAnh() == null) {
            uploadImageChiTiet.setImageResource(R.drawable.baseline_phone_iphone_24);
        } else {
            Picasso.get().load(detailPhone.getHinhAnh()).into(uploadImageChiTiet);
        }
        uploadImageChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent();
                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
                photoPicker.setType("image/*");
                activityResultLauncherChiTiet.launch(photoPicker);
            }
        });
        chitiet_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidateDetailPhone()) {
                    Integer strSoLuong = Integer.parseInt("" + edSoLuong.getText().toString());
                    Integer strGiaTien = Integer.parseInt("" + edGiaTien.getText().toString());
                    if (strSoLuong.toString().trim().charAt(0) == '0') {
                        edSoLuong.setText(" " + edSoLuong.getText().toString().trim().substring(1));
                    }
                    if (strGiaTien.toString().trim().charAt(0) == '0') {
                        edGiaTien.setText(" " + edGiaTien.getText().toString().trim().substring(1));
                    }
                    if (imageUri != null) {
                        progressDialog = new ProgressDialog(getContext());
                        progressDialog.setMessage("Vui Lòng Chờ...");
                        progressDialog.setCancelable(false);
                        progressDialog.show();
                        String url_src = System.currentTimeMillis() + "." + getFileExtension(imageUri);
                        final StorageReference imageReference = storageReference.child(url_src);
                        imageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        Call<DetailPhone> call = apiService.putChiTietDienThoai(detailPhone.get_id(), new DetailPhone(strSoLuong, strGiaTien, new Phone(detailPhone.getMaDienThoai().get_id()), new Mau(idSpMau, ""), new DungLuong(idSpDungLuong), new Ram(idSpRam) , uri.toString()));
                                        call.enqueue(new Callback<DetailPhone>() {
                                            @Override
                                            public void onResponse(Call<DetailPhone> call, Response<DetailPhone> response) {
                                                if (response.isSuccessful()) {
                                                    Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                                    getData(detailPhone.get_id());
                                                    action();
                                                    dialogDetail.dismiss();
                                                    progressDialog.dismiss();
                                                    imageUri = null;
                                                }
                                            }

                                            @Override
                                            public void onFailure(Call<DetailPhone> call, Throwable t) {

                                            }
                                        });
                                    }
                                });
                            }
                        });
                    } else {
                        Call<DetailPhone> call = apiService.putChiTietDienThoai(detailPhone.get_id(), new DetailPhone(strSoLuong, strGiaTien, new Phone(detailPhone.getMaDienThoai().get_id()), new Mau(idSpMau, ""), new DungLuong(idSpDungLuong), new Ram(idSpRam) , detailPhone.getHinhAnh()));
                        call.enqueue(new Callback<DetailPhone>() {
                            @Override
                            public void onResponse(Call<DetailPhone> call, Response<DetailPhone> response) {
                                if (response.isSuccessful()) {
                                    Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                    getData(detailPhone.get_id());
                                    action();
                                    dialogDetail.dismiss();
//                                    progressDialog.dismiss();
                                    imageUri = null;
                                }
                            }

                            @Override
                            public void onFailure(Call<DetailPhone> call, Throwable t) {

                            }
                        });
                    }
                }

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
    private void getDataUuDai(String id){

        ApiService apiService = ApiRetrofit.getApiService();
        Call<List<UuDai>> call = apiService.getUuDaiCuaHang(id);

        call.enqueue(new Callback<List<UuDai>>() {
            @Override
            public void onResponse(Call<List<UuDai>> call, Response<List<UuDai>> response) {
                if(response.isSuccessful()){
                    List<UuDai> data = response.body();
                    listUuDai.clear();
                    listUuDai.addAll(data);

                    adapterUuDai.notifyDataSetChanged();
                }else{
                    Toast.makeText(getContext(),"Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }
            @Override
            public void onFailure(Call<List<UuDai>> call, Throwable t) {
                Log.e("Uu dai theo cua hang", t.getMessage());
            }
        });
    }

    private void dialogUuDai(String idMaUuDai){

        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_uudaicuahang,null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        Window window = dialog.getWindow();
        if(window == null){
            return;
        }
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT,WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = Gravity.CENTER;
        window.setAttributes(windowAttributes);

        dl_udch_btnHuy = view.findViewById(R.id.dl_udch_btnHuy);
        dl_udch_btnCapNhat = view.findViewById(R.id.dl_udch_btnXacNhan);
        rc__udch_uuDai = view.findViewById(R.id.rc__udch_uuDai);

        Button btnXacNhan = view.findViewById(R.id.dl_udch_btnXacNhan);
        Button btnHuy = view.findViewById(R.id.dl_udch_btnHuy);
//        call.enqueue(new Callback<List<Phone>>() {
//            @Override
//            public void onResponse(Call<List<Phone>> call, Response<List<Phone>> response) {
//                if (response.isSuccessful()) {
//                    List<Phone> data = response.body();
//                    phoneList.clear();
//                    phoneList.addAll(data);
//                } else {
//                    Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
//                }
//            }
//
//            @Override
//            public void onFailure(Call<List<Phone>> call, Throwable t) {
//                Log.e("Get Phone", t.getMessage());
//            }
//        });
        listUuDai = new ArrayList<>();
        linearLayoutManagerUuDai = new LinearLayoutManager(getContext());
        rc__udch_uuDai.setLayoutManager(linearLayoutManagerUuDai);
        adapterUuDai = new ApDungUuDaiAdapter(getContext(), new IItemUuDaiListenner() {
            @Override
            public void showDetail(String idUuDai) {

            }

            @Override
            public void editUuDai(UuDai idUuDai) {

            }

            @Override
            public void selectUuDai(String idUuDai, boolean isChecked) {
                maUuDai = idUuDai;
                Log.d("ZZZ", "selectUuDai: "+maUuDai);

            }
        });
        mySharedPreferences = new MySharedPreferences(getContext());
        getDataUuDai(mySharedPreferences.getUserId());

        adapterUuDai.setData(listUuDai,idMaUuDai);
        rc__udch_uuDai.setAdapter(adapterUuDai);

        btnXacNhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UpdateUuDai(phone.get_id());
                listUuDai = new ArrayList<>();
                adapterUuDai = new ApDungUuDaiAdapter(getContext(), new IItemUuDaiListenner() {
                    @Override
                    public void showDetail(String idUuDai) {

                    }

                    @Override
                    public void editUuDai(UuDai idUuDai) {

                    }

                    @Override
                    public void selectUuDai(String idUuDai, boolean isChecked) {
                        maUuDai = idUuDai;
                        Log.d("ZZZ", "selectUuDai: "+maUuDai);

                    }
                });
                mySharedPreferences = new MySharedPreferences(getContext());
                getDataUuDai(mySharedPreferences.getUserId());

                adapterUuDai.setData(listUuDai,idMaUuDai);
                rc__udch_uuDai.setAdapter(adapterUuDai);
                dialog.dismiss();
            }
        });
        btnHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });

    }
    private void UpdateUuDai(String id){
        ApiService apiService = ApiRetrofit.getApiService();
        mySharedPreferences = new MySharedPreferences(getContext());
        Log.d("ZZZ", "UpdateUuDai: " + maUuDai);
        Call<Phone> call = apiService.putUuDaiDienThoai(id,new UuDai(maUuDai));
        call.enqueue(new Callback<Phone>() {
            @Override
            public void onResponse(Call<Phone> call, Response<Phone> response) {
                if (response.isSuccessful()) {
                    Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                    getData(id);
                    getDataUuDai(mySharedPreferences.getUserId());

                }
            }

            @Override
            public void onFailure(Call<Phone> call, Throwable t) {
                Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }
    final ActivityResultLauncher<Intent> activityResultLauncherChiTiet = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            Intent data = result.getData();
            imageUri = data.getData();
            uploadImageChiTiet.setImageURI(imageUri);
        }
    });
    private String getFileExtension(Uri fileUri) {
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(fileUri));
    }


    //validate chi tiết điện thoại
    private boolean checkValidateDetailPhone(){
        if (edSoLuong.getText().toString().trim().isEmpty()) {
            edSoLuong.setError("Yêu cầu không được để trống!!");
            return false;
        } else if (!Pattern.matches("\\d+", edSoLuong.getText().toString().trim())) {
            edSoLuong.setError("Yêu cầu nhập số lượng phải là số!!");
            return false;
        }
//        else if (edSoLuong.getText().toString().trim().charAt(0) == '0') {
//            edSoLuong.setText(" " + edSoLuong.getText().toString().trim().substring(1));
//            return false;
//        }
        else if (edGiaTien.getText().toString().isEmpty()) {
            edGiaTien.setError("Yêu cầu không được để trống!!");
            return false;
        }else if (!Pattern.matches("\\d+", edGiaTien.getText().toString())) {
            edGiaTien.setError("Yêu cầu nhập giá tiền phải là số!!");
            return false;
        }
//        else if (edGiaTien.getText().toString().trim().charAt(0) == '0') {
//            edGiaTien.setText(" " + edGiaTien.getText().toString().trim().substring(1));
//            return false;
//        }
        return true;
    }

}