package com.example.appkhachhang.Fragment;

import static android.content.Intent.getIntent;

import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.appkhachhang.Api.ApiRetrofit;
import com.example.appkhachhang.Api.FeedbackAPI;
import com.example.appkhachhang.FeedbackScreen;
import com.example.appkhachhang.Model.ChiTietDienThoai;
import com.example.appkhachhang.Model.DanhGia;
import com.example.appkhachhang.Model.User;
import com.example.appkhachhang.R;
import com.example.appkhachhang.ViewFeedbackScreen;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class updateDanhGiaFragment extends Fragment {
    ImageView img_sao_xam_1,img_sao_xam_2,img_sao_xam_3,img_sao_xam_4,img_sao_xam_5, img_update_anh_danh_gia;
    EditText edUpdateFeedback;
    Button btnUpdateFeedback,btnCancleFeedback;
    DanhGia danhGia;

    Uri imageUri;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_update_feedback, container, false);

        initView(view);
        initVariable();
        return view;


    }




    private void initVariable() {

        edUpdateFeedback.setText(danhGia.getNoiDung());
        if (danhGia.getDiemDanhGia() == 1){
            img_sao_xam_1.setImageResource(R.drawable.rating_sao_vang);
            img_sao_xam_2.setImageResource(R.drawable.rating_star_xam);
            img_sao_xam_3.setImageResource(R.drawable.rating_star_xam);
            img_sao_xam_4.setImageResource(R.drawable.rating_star_xam);
            img_sao_xam_5.setImageResource(R.drawable.rating_star_xam);
        }else if(danhGia.getDiemDanhGia() == 2){
            img_sao_xam_1.setImageResource(R.drawable.rating_sao_vang);
            img_sao_xam_2.setImageResource(R.drawable.rating_sao_vang);
            img_sao_xam_3.setImageResource(R.drawable.rating_star_xam);
            img_sao_xam_4.setImageResource(R.drawable.rating_star_xam);
            img_sao_xam_5.setImageResource(R.drawable.rating_star_xam);
        }else if(danhGia.getDiemDanhGia() == 3){
            img_sao_xam_1.setImageResource(R.drawable.rating_sao_vang);
            img_sao_xam_2.setImageResource(R.drawable.rating_sao_vang);
            img_sao_xam_3.setImageResource(R.drawable.rating_sao_vang);
            img_sao_xam_4.setImageResource(R.drawable.rating_star_xam);
            img_sao_xam_5.setImageResource(R.drawable.rating_star_xam);
        }else if(danhGia.getDiemDanhGia() == 4){
            img_sao_xam_1.setImageResource(R.drawable.rating_sao_vang);
            img_sao_xam_2.setImageResource(R.drawable.rating_sao_vang);
            img_sao_xam_3.setImageResource(R.drawable.rating_sao_vang);
            img_sao_xam_4.setImageResource(R.drawable.rating_sao_vang);
            img_sao_xam_5.setImageResource(R.drawable.rating_star_xam);
        }else if(danhGia.getDiemDanhGia() == 5){
            img_sao_xam_1.setImageResource(R.drawable.rating_sao_vang);
            img_sao_xam_2.setImageResource(R.drawable.rating_sao_vang);
            img_sao_xam_3.setImageResource(R.drawable.rating_sao_vang);
            img_sao_xam_4.setImageResource(R.drawable.rating_sao_vang);
            img_sao_xam_5.setImageResource(R.drawable.rating_sao_vang);
        }
        if(!danhGia.getHinhAnh().equals("")){
            Picasso.get().load(danhGia.getHinhAnh()).into(img_update_anh_danh_gia);
        }else{
            img_update_anh_danh_gia.setVisibility(View.GONE);
        }
        img_update_anh_danh_gia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent();
                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });
        btnUpdateFeedback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String noiDung = edUpdateFeedback.getText().toString().trim();
//                Number diemDanhGia = diemFeedback;
                int diemDanhGia = danhGia.getDiemDanhGia();

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0 nên cộng thêm 1
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                // Định dạng ngày theo "dd-MM-yyyy"
                SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
                String currentDate = sdf.format(calendar.getTime());

                final StorageReference storageReference = FirebaseStorage.getInstance().getReference();
                if (imageUri != null) {
                    String url_src = System.currentTimeMillis() + "." + getFileExtension(imageUri);
                    final StorageReference imageReference = storageReference.child(url_src);
                    String finalNoiDung = noiDung;
                    imageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    FeedbackAPI feedbackAPI = ApiRetrofit.getFeedbackAPI();
                                    Call<DanhGia> call = feedbackAPI.update( finalNoiDung, uri.toString(), diemDanhGia, currentDate, danhGia.getIdKhachHang(), danhGia.getIdChiTietDienThoai());
                                    call.enqueue(new Callback<DanhGia>() {
                                        @Override
                                        public void onResponse(Call<DanhGia> call, Response<DanhGia> response) {
                                            if (response.isSuccessful()) {

                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<DanhGia> call, Throwable t) {

                                        }
                                    });
                                }
                            });
                        }
                    });
                }else{
                    FeedbackAPI feedbackAPI = ApiRetrofit.getFeedbackAPI();
                    Call<DanhGia> call = feedbackAPI.update(noiDung, "", diemDanhGia, currentDate, danhGia.getIdKhachHang(), danhGia.getIdChiTietDienThoai());
                    call.enqueue(new Callback<DanhGia>() {
                        @Override
                        public void onResponse(Call<DanhGia> call, Response<DanhGia> response) {
                            if(response.isSuccessful()){

                            }
                        }

                        @Override
                        public void onFailure(Call<DanhGia> call, Throwable t) {

                        }
                    });
                }

            }
        });



    }


    private void initView(View view) {
        img_sao_xam_1 = view.findViewById(R.id.img_sao_xam_1);
        img_sao_xam_2 = view.findViewById(R.id.img_sao_xam_2);
        img_sao_xam_3 = view.findViewById(R.id.img_sao_xam_3);
        img_sao_xam_4 = view.findViewById(R.id.img_sao_xam_4);
        img_sao_xam_5 = view.findViewById(R.id.img_sao_xam_5);
        img_update_anh_danh_gia = view.findViewById(R.id.img_update_anh_danh_gia);
        btnUpdateFeedback = view.findViewById(R.id.btnUpdateFeedback);
        btnCancleFeedback = view.findViewById(R.id.btnCancleFeedback);

    };



    final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            Intent data = result.getData();
            imageUri = data.getData();
            img_update_anh_danh_gia.setImageURI(imageUri);
        }
    });
    private String getFileExtension(Uri fileUri) {
        ContentResolver contentResolver = getContext().getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(fileUri));
    }





}
