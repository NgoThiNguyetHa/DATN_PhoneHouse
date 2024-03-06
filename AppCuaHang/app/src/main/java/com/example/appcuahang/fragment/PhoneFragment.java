package com.example.appcuahang.fragment;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
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
import android.webkit.MimeTypeMap;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appcuahang.R;
import com.example.appcuahang.activity.DienThoaiActivity;
import com.example.appcuahang.adapter.BrandAdapter;
import com.example.appcuahang.adapter.PhoneAdapter;
import com.example.appcuahang.api.ApiRetrofit;
import com.example.appcuahang.api.ApiService;
import com.example.appcuahang.interface_adapter.interface_adapter.IItemBrandListenner;
import com.example.appcuahang.model.Brand;
import com.example.appcuahang.model.Phone;
import com.example.appcuahang.untils.MySharedPreferences;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PhoneFragment extends Fragment {

    List<Phone> list;
    PhoneAdapter adapter;
    GridLayoutManager manager;
    RecyclerView rc_phone;
    MySharedPreferences mySharedPreferences;
    final private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("image");
    final private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    Uri imageUri;
    ImageView uploadImage , imgClose;

    Spinner spHangSX;
    EditText edTenDienThoai, edCamera , edKichThuoc, edCPU , edPin, edHeDieuHanh, edNamSX ,edCongNgheManHinh , edDoPhanGiai , edBaoHanh , edMoTa;
    TextView tvTitle;
    Button btnSave;
    Phone phone;
    String idHang;
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_phone, container, false);
        ((Activity) getContext()).setTitle("Điện Thoại");
        initView(view);
        initVariable();
        getData();
        return view;
    }
    private void initView(View view) {
        rc_phone = view.findViewById(R.id.rc_phone);
    }

    private void initVariable(){
        list = new ArrayList<>();
        FirebaseApp.initializeApp(getContext());

        manager = new GridLayoutManager(getContext(), 2);
        rc_phone.setLayoutManager(manager);
        adapter = new PhoneAdapter(getContext());
        adapter.setData(list);
        rc_phone.setAdapter(adapter);
    }
    private void getData() {
        mySharedPreferences = new MySharedPreferences(getContext());
        ApiService apiService = ApiRetrofit.getApiService();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Phone model = dataSnapshot.getValue(Phone.class);
                    list.add(model);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Call<List<Phone>> call = apiService.getDienThoai(mySharedPreferences.getUserId());

        call.enqueue(new Callback<List<Phone>>() {
            @Override
            public void onResponse(Call<List<Phone>> call, Response<List<Phone>> response) {
                if (response.isSuccessful()) {
                    List<Phone> data = response.body();
                    list.clear();
                    list.addAll(data);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Phone>> call, Throwable t) {
                Log.e("brand", t.getMessage());
            }
        });

    }

    //hiện icon trên toolbar
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.dialog, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.btn_add) {
//            dialogAdd();
            dialogAdd();
        }
        return super.onOptionsItemSelected(item);
    }
    private void dialogAdd(){
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_dienthoai, null);
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
        //init view
        spHangSX = view.findViewById(R.id.dienthoai_spHangSX);
        edTenDienThoai = view.findViewById(R.id.dienthoai_edTenDT);
        edCamera = view.findViewById(R.id.dienthoai_edCamera);
        edKichThuoc = view.findViewById(R.id.dienthoai_edKichThuoc);
        edCPU = view.findViewById(R.id.dienthoai_edCPU);
        edPin = view.findViewById(R.id.dienthoai_edPin);
        edHeDieuHanh = view.findViewById(R.id.dienthoai_edHeDieuHanh);
        edNamSX = view.findViewById(R.id.dienthoai_edNamSX);
        edCongNgheManHinh = view.findViewById(R.id.dienthoai_edManHinh);
        edDoPhanGiai = view.findViewById(R.id.dienthoai_edDoPhanGiai);
        edNamSX = view.findViewById(R.id.dienthoai_edNamSX);
        edMoTa = view.findViewById(R.id.dienthoai_edMoTa);
        edBaoHanh = view.findViewById(R.id.dienthoai_edBaoHanh);
        uploadImage = view.findViewById(R.id.dienthoai_uploadImageView);
        imgClose = view.findViewById(R.id.dienthoai_imageView);
        tvTitle = view.findViewById(R.id.dienthoai_tvTitle);
        btnSave = view.findViewById(R.id.dienthoai_btnSave);
        //init variable
        mySharedPreferences = new MySharedPreferences(getContext());
        ApiService apiService = ApiRetrofit.getApiService();
        Call<List<Brand>> call = apiService.getHangSanXuatByCuaHang(mySharedPreferences.getUserId());
        //set text
        tvTitle.setText("Thêm Mới Điện Thoại");
        btnSave.setText("Thêm Mới");
        //set up data spinner
        call.enqueue(new Callback<List<Brand>>() {
            @Override
            public void onResponse(Call<List<Brand>> call, Response<List<Brand>> response) {
                if (response.isSuccessful()) {
                    List<Brand> data = response.body();
                    ArrayAdapter loaiSanPhamAdapter = new ArrayAdapter<>(getContext(), android.R.layout.simple_spinner_dropdown_item, data);
                    spHangSX.setAdapter(loaiSanPhamAdapter);
                    spHangSX.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            String tenHang = data.get(i).getTenHang();
                            idHang = data.get(i).get_id();
                        }
                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                } else {
                    Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Brand>> call, Throwable t) {
                Log.e("brand", t.getMessage());
            }
        });

        //set up image
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent();
                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strTenDT = edTenDienThoai.getText().toString().trim();
                String strKichThuoc = edKichThuoc.getText().toString().trim();
                String strCNMH = edCongNgheManHinh.getText().toString().trim();
                String strCamera = edCamera.getText().toString().trim();
                String strCPu = edCPU.getText().toString().trim();
                String strPin = edPin.getText().toString().trim();
                String strHeDieuHanh = edHeDieuHanh.getText().toString().trim();
                String strDoPhanGiai = edDoPhanGiai.getText().toString().trim();
                String strNamSX = edNamSX.getText().toString().trim();
                String strBaoHanh = edBaoHanh.getText().toString().trim();
                String strMoTa = edMoTa.getText().toString().trim();
                ApiService apiService = ApiRetrofit.getApiService();
                //upload ảnh lên firebase
                if (imageUri != null){
                    String url_src = System.currentTimeMillis() +"."+ getFileExtension(imageUri);
                    final StorageReference imageReference = storageReference.child(url_src);
                    imageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Toast.makeText(getContext(), "Upload success", Toast.LENGTH_SHORT).show();
                            imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    Call<Phone> call = apiService.addDienThoai(new Phone(strTenDT,strKichThuoc,strCNMH,strCamera,strCPu,strPin,strHeDieuHanh,strDoPhanGiai,strNamSX,strBaoHanh,strMoTa,idHang, uri.toString(),""));
                                    call.enqueue(new Callback<Phone>() {
                                        @Override
                                        public void onResponse(Call<Phone> call, Response<Phone> response) {
                                            if (response.isSuccessful()) {
                                                Toast.makeText(getContext(), "Thêm mới thành công", Toast.LENGTH_SHORT).show();
                                                getData();
                                                dialog.dismiss();
                                            }
                                        }
                                        @Override
                                        public void onFailure(Call<Phone> call, Throwable t) {
                                            Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                        }
                    });
                }else{
                    Toast.makeText(getContext(), "Yêu cầu chọn ảnh", Toast.LENGTH_SHORT).show();
                }

            }
        });
        imgClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
    }

    //cấp quyền người dùng
    final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            if (result.getResultCode() == RESULT_OK){
                Intent data = result.getData();
                imageUri = data.getData();
                uploadImage.setImageURI(imageUri);
            }else{
                Toast.makeText(getContext(), "Chưa chọn ảnh", Toast.LENGTH_SHORT).show();
            }
        }
    });
    //cấp đường link từ image
    private String getFileExtension(Uri fileUri){
        ContentResolver contentResolver = getActivity().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(fileUri));
    }
}