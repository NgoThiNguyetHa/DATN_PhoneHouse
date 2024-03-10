package com.example.appcuahang.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appcuahang.R;
import com.example.appcuahang.adapter.BrandAdapter;
import com.example.appcuahang.adapter.ChiTietAdapter;
import com.example.appcuahang.api.ApiRetrofit;
import com.example.appcuahang.api.ApiService;
import com.example.appcuahang.interface_adapter.IItemBrandListenner;
import com.example.appcuahang.interface_adapter.IItemMauListenner;
import com.example.appcuahang.model.Brand;
import com.example.appcuahang.model.DetailPhone;
import com.example.appcuahang.model.Mau;
import com.example.appcuahang.model.Phone;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chi_tiet_dien_thoai);
        initView();
        initVariable();
        action();
    }

    private void initView(){
        tvTenDienThoai = findViewById(R.id.chitiet_tvTenDienThoai);
        imgChiTiet = findViewById(R.id.chitiet_imgDienThoai);
        rc_chiTiet = findViewById(R.id.rc_chiTiet);
    }
    private void initVariable(){
        list = new ArrayList<>();
        FirebaseApp.initializeApp(getApplicationContext());
        manager = new GridLayoutManager(getApplicationContext(), 2);
        rc_chiTiet.setLayoutManager(manager);
        adapter = new ChiTietAdapter(getApplicationContext());
        adapter.setData(list);
        rc_chiTiet.setAdapter(adapter);
    }
    private void action(){
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        Phone phone = (Phone) bundle.getSerializable("detailPhone");
        tvTenDienThoai.setText(""+phone.getTenDienThoai());
        if (phone.getHinhAnh() == null){
            imgChiTiet.setImageResource(R.drawable.baseline_phone_iphone_24);
        }else {
            Picasso.get().load(phone.getHinhAnh()).into(imgChiTiet);
        }
        getData(phone.get_id());
        getThongSoKyThuat(phone);
    }

    private void getData(String id) {
        ApiService apiService = ApiRetrofit.getApiService();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
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
                    Toast.makeText(getApplicationContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DetailPhone>> call, Throwable t) {
                Log.e("detail", t.getMessage());
            }
        });
    }

    private void getThongSoKyThuat(Phone phone){
        tvKichThuoc = findViewById(R.id.chitiet_tvKichThuoc);
        tvCongNgheManHinh = findViewById(R.id.chitiet_tvCongNgheManHinh);
        tvCamera = findViewById(R.id.chitiet_tvCamera);
        tvCPU = findViewById(R.id.chitiet_tvCPU);
        tvPin = findViewById(R.id.chitiet_tvPin);
        tvHeDieuHanh = findViewById(R.id.chitiet_tvHeDieuHanh);
        tvDoPhanGiai = findViewById(R.id.chitiet_tvDoPhanGiai);
        tvNamSanXuat = findViewById(R.id.chitiet_tvNamSanXuat);
        tvBaoHanh = findViewById(R.id.chitiet_tvBaoHanh);
        tvMoTa = findViewById(R.id.chitiet_tvMoTa);
        tvKichThuoc.setText("Kích thước: "+phone.getKichThuoc());
        tvCongNgheManHinh.setText("Công nghệ màn hình: "+phone.getCongNgheManHinh());
        tvCamera.setText("Camera: "+phone.getCamera());
        tvCPU.setText("CPU: "+phone.getCpu());
        tvPin.setText("Pin: "+phone.getPin());
        tvHeDieuHanh.setText("Hệ điều hành: "+phone.getHeDieuHanh());
        tvDoPhanGiai.setText("Độ phân giải: "+phone.getDoPhanGiai());
        tvNamSanXuat.setText("Năm sản xuất: "+phone.getNamSanXuat());
        tvBaoHanh.setText("Thời gian bảo hành: "+phone.getThoiGianBaoHanh());
        tvMoTa.setText("Mô tả thêm: "+phone.getMoTaThem());
    }


}