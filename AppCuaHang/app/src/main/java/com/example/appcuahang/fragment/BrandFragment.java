package com.example.appcuahang.fragment;

import static android.app.Activity.RESULT_OK;

import android.app.Activity;
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
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appcuahang.R;
import com.example.appcuahang.adapter.BrandAdapter;
import com.example.appcuahang.api.ApiRetrofit;
import com.example.appcuahang.api.ApiService;
import com.example.appcuahang.interface_adapter.IItemBrandListenner;
import com.example.appcuahang.model.Brand;
import com.example.appcuahang.untils.MySharedPreferences;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
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

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BrandFragment extends Fragment {
    RecyclerView rc_brand;
    List<Brand> list;
    List<Brand> listFilter;
    BrandAdapter adapter;
    GridLayoutManager manager;
    MySharedPreferences mySharedPreferences;

    TextView tv_entry;
    TextInputEditText brand_edSearch;



    //upload image

    final private DatabaseReference reference = FirebaseDatabase.getInstance().getReference("image");
    final private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    Uri imageUri;
    ImageView uploadImage;
    FirebaseDatabase database;
    ProgressDialog progressDialog;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_brand, container, false);
        ((Activity) getContext()).setTitle("Hãng Sản Xuất");
        initView(view);
        initVariable();
        getData();

        listFilter = new ArrayList<>();
        brand_edSearch.addTextChangedListener(new TextWatcher() {
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
                    if(list.get(i).getTenHang().toString().toLowerCase().contains(brand_edSearch.getText().toString().toLowerCase()) && brand_edSearch.getText().length() != 0){
                        listFilter.add(list.get(i));
                        tv_entry.setVisibility(View.GONE);
                    }
                }
                if(listFilter.size() == 0){
                    tv_entry.setVisibility(View.VISIBLE);
                }
                adapter = new BrandAdapter(getContext(), new IItemBrandListenner() {
                    @Override
                    public void deleteBrand(String idBrand) {

                    }

                    @Override
                    public void editBrand(Brand idBrand) {
                        updateData(idBrand);
                    }

                    @Override
                    public void showDetail(String idBrand) {

                    }
                });
                if (brand_edSearch.getText().toString().trim().isEmpty()) {
                    adapter.setData(list);
                    tv_entry.setVisibility(View.GONE);
                    rc_brand.setAdapter(adapter);
                } else {
                    adapter.setData(listFilter);
                    rc_brand.setAdapter(adapter);
                }

            }
        });
        return view;
    }

    private void initView(View view) {

        rc_brand = view.findViewById(R.id.rc_brand);
        brand_edSearch = view.findViewById(R.id.brand_edSearch);
        tv_entry = view.findViewById(R.id.tv_entry);
    }

    private void initVariable(){
        list = new ArrayList<>();
        FirebaseApp.initializeApp(getContext());

        manager = new GridLayoutManager(getContext(), 2);
        rc_brand.setLayoutManager(manager);
        adapter = new BrandAdapter(getContext(), new IItemBrandListenner() {
            @Override
            public void deleteBrand(String idBrand) {

            }

            @Override
            public void editBrand(Brand idBrand) {
                updateData(idBrand);
            }

            @Override
            public void showDetail(String idBrand) {

            }
        });
        adapter.setData(list);
        rc_brand.setAdapter(adapter);
    }
    private void getData() {
        mySharedPreferences = new MySharedPreferences(getContext());
        ApiService apiService = ApiRetrofit.getApiService();
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                    Brand model = dataSnapshot.getValue(Brand.class);
                    list.add(model);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        Call<List<Brand>> call = apiService.getHangSanXuat();

        call.enqueue(new Callback<List<Brand>>() {
            @Override
            public void onResponse(Call<List<Brand>> call, Response<List<Brand>> response) {
                if (response.isSuccessful()) {
                    List<Brand> data = response.body();
                    list.clear();
                    list.addAll(data);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Brand>> call, Throwable t) {
                Log.e("brand", t.getMessage());
            }
        });

    }

    private void addData() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_brand, null);
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

        EditText edTenHang = view.findViewById(R.id.dl_brand_edTenHang);
        Button btnSave = view.findViewById(R.id.dl_brand_btnSave);
        TextView tvTitle = view.findViewById(R.id.dl_brand_tvTitle);
        ImageView imgViewClose = view.findViewById(R.id.dl_brand_imageView);
        uploadImage = view.findViewById(R.id.dl_brand_uploadImageView);
        uploadImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent();
                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });

        tvTitle.setText("Thêm Mới Hãng Sản Xuất");
        btnSave.setText("Thêm Mới");
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                String tenHang = edTenHang.getText().toString().trim();
                ApiService apiService = ApiRetrofit.getApiService();
                //====
                //upload ảnh lên firebase
                if (imageUri != null){
                    String url_src = System.currentTimeMillis() +"."+ getFileExtension(imageUri);
                    final StorageReference imageReference = storageReference.child(url_src);
                    imageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
//                                    String key = reference.push().getKey();
//                                    reference.child(key).setValue(uri.toString());
                                    Call<Brand> call = apiService.postHangSanXuat(new Brand(tenHang, uri.toString()));
                                    call.enqueue(new Callback<Brand>() {
                                        @Override
                                        public void onResponse(Call<Brand> call, Response<Brand> response) {
                                            if (response.isSuccessful()) {
                                                Toast.makeText(getContext(), "Thêm mới thành công", Toast.LENGTH_SHORT).show();
                                                getData();
                                                dialog.dismiss();
                                                progressDialog.dismiss();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Brand> call, Throwable t) {
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
        imgViewClose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }

    //cập nhật ảnh
    private void updateData(Brand brand) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_brand, null);
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

        EditText edTenHang = view.findViewById(R.id.dl_brand_edTenHang);
        Button btnSave = view.findViewById(R.id.dl_brand_btnSave);
        TextView tvTitle = view.findViewById(R.id.dl_brand_tvTitle);
        ImageView imgView = view.findViewById(R.id.dl_brand_imageView);
        uploadImage = view.findViewById(R.id.dl_brand_uploadImageView);
        tvTitle.setText("Cập Nhật Hãng Sản Xuất");
        edTenHang.setText(brand.getTenHang());
        if (brand.getHinhAnh() == null) {
            uploadImage.setImageResource(R.drawable.img_10);
        }else {
            Picasso.get().load(brand.getHinhAnh()).into(uploadImage);
        }
        btnSave.setText("Cập Nhật");
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
                //====
                progressDialog = new ProgressDialog(getContext());
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(false);
                progressDialog.show();
                //upload ảnh lên firebase
                if (imageUri != null){
                    String url_src = System.currentTimeMillis() +"."+ getFileExtension(imageUri);
                    final StorageReference imageReference = storageReference.child(url_src);
                    imageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String tenHang = edTenHang.getText().toString().trim();
                                    ApiService apiService = ApiRetrofit.getApiService();
                                    Call<Brand> call = apiService.putHangSanXuat(brand.get_id(), new Brand(tenHang , uri.toString()));
                                    call.enqueue(new Callback<Brand>() {
                                        @Override
                                        public void onResponse(Call<Brand> call, Response<Brand> response) {
                                            if (response.isSuccessful()) {
                                                Toast.makeText(getContext(), "Cập nhật thành công", Toast.LENGTH_SHORT).show();
                                                getData();
                                                dialog.dismiss();
                                                progressDialog.dismiss();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<Brand> call, Throwable t) {
                                            Toast.makeText(getContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                                            Log.e("loi update",t.getMessage());
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

        imgView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

    }


    //hiện icon trên menu
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.dialog, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.btn_add) {
            addData();
        }
        return super.onOptionsItemSelected(item);
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