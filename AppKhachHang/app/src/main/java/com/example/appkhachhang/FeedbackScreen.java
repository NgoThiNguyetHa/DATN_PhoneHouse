package com.example.appkhachhang;

import static android.app.PendingIntent.getActivity;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appkhachhang.Api.ApiRetrofit;
import com.example.appkhachhang.Api.FeedbackAPI;
import com.example.appkhachhang.Model.ChiTietDienThoai;
import com.example.appkhachhang.Model.ChiTietHoaDon;
import com.example.appkhachhang.Model.DanhGia;
import com.example.appkhachhang.Model.FileData;
import com.example.appkhachhang.Model.User;
import com.example.appkhachhang.untils.MySharedPreferences;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FeedbackScreen extends AppCompatActivity {
    ImageView imgDienThoaiDG, img_sao_xam_1, img_sao_xam_2, img_sao_xam_3, img_sao_xam_4, img_sao_xam_5, img_anh_danh_gia;

    TextView bill_item_tvDienThoaiDG, bill_item_tvMauDG, bill_item_tvSoLuongDG, bill_item_tvTongTienDG;


    EditText edFeedback;
    Button btnFeedback;

    List<ChiTietHoaDon> list;

//    Number diemFeedback = 0;
        int diemFeedback = 0;


    FirebaseDatabase database;
    Uri imageUri;
    MySharedPreferences mySharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_screen);
        imgDienThoaiDG = findViewById(R.id.imgDienThoaiDG);
        img_sao_xam_1 = findViewById(R.id.img_sao_xam_1);
        img_sao_xam_2 = findViewById(R.id.img_sao_xam_2);
        img_sao_xam_3 = findViewById(R.id.img_sao_xam_3);
        img_sao_xam_4 = findViewById(R.id.img_sao_xam_4);
        img_sao_xam_5 = findViewById(R.id.img_sao_xam_5);
        img_anh_danh_gia = findViewById(R.id.img_anh_danh_gia);
        bill_item_tvDienThoaiDG = findViewById(R.id.bill_item_tvDienThoaiDG);
        bill_item_tvMauDG = findViewById(R.id.bill_item_tvMauDG);
        bill_item_tvSoLuongDG = findViewById(R.id.bill_item_tvSoLuongDG);
        edFeedback = findViewById(R.id.edFeedback);
        bill_item_tvTongTienDG = findViewById(R.id.bill_item_tvTongTienDG);
        btnFeedback = findViewById(R.id.btnFeedback);
        mySharedPreferences = new MySharedPreferences(getApplicationContext());


        Bundle bundle = getIntent().getExtras();
        ChiTietHoaDon chiTietHoaDon = (ChiTietHoaDon) bundle.getSerializable("DanhGia");
        bill_item_tvDienThoaiDG.setText(""+ chiTietHoaDon.getMaChiTietDienThoai().getMaDienThoai().getTenDienThoai());
        bill_item_tvMauDG.setText(""+ chiTietHoaDon.getMaChiTietDienThoai().getMaMau().getTenMau());
        bill_item_tvSoLuongDG.setText("x"+ chiTietHoaDon.getSoLuong());
        bill_item_tvTongTienDG.setText("Tổng tiền: "+ chiTietHoaDon.getGiaTien());
        Picasso.get().load(chiTietHoaDon.getMaChiTietDienThoai().getMaDienThoai().getHinhAnh()).into(imgDienThoaiDG);


        img_sao_xam_1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_sao_xam_1.setImageResource(R.drawable.rating_sao_vang);
                img_sao_xam_2.setImageResource(R.drawable.rating_star_xam);
                img_sao_xam_3.setImageResource(R.drawable.rating_star_xam);
                img_sao_xam_4.setImageResource(R.drawable.rating_star_xam);
                img_sao_xam_5.setImageResource(R.drawable.rating_star_xam);
                diemFeedback = 1;
            }
        });
        img_sao_xam_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_sao_xam_1.setImageResource(R.drawable.rating_sao_vang);
                img_sao_xam_2.setImageResource(R.drawable.rating_sao_vang);
                img_sao_xam_3.setImageResource(R.drawable.rating_star_xam);
                img_sao_xam_4.setImageResource(R.drawable.rating_star_xam);
                img_sao_xam_5.setImageResource(R.drawable.rating_star_xam);
                diemFeedback = 2;
            }
        });
        img_sao_xam_3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_sao_xam_1.setImageResource(R.drawable.rating_sao_vang);
                img_sao_xam_2.setImageResource(R.drawable.rating_sao_vang);
                img_sao_xam_3.setImageResource(R.drawable.rating_sao_vang);
                img_sao_xam_4.setImageResource(R.drawable.rating_star_xam);
                img_sao_xam_5.setImageResource(R.drawable.rating_star_xam);
                diemFeedback = 3;
            }
        });
        img_sao_xam_4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_sao_xam_1.setImageResource(R.drawable.rating_sao_vang);
                img_sao_xam_2.setImageResource(R.drawable.rating_sao_vang);
                img_sao_xam_3.setImageResource(R.drawable.rating_sao_vang);
                img_sao_xam_4.setImageResource(R.drawable.rating_sao_vang);
                img_sao_xam_5.setImageResource(R.drawable.rating_star_xam);
                diemFeedback = 4;
            }
        });
        img_sao_xam_5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                img_sao_xam_1.setImageResource(R.drawable.rating_sao_vang);
                img_sao_xam_2.setImageResource(R.drawable.rating_sao_vang);
                img_sao_xam_3.setImageResource(R.drawable.rating_sao_vang);
                img_sao_xam_4.setImageResource(R.drawable.rating_sao_vang);
                img_sao_xam_5.setImageResource(R.drawable.rating_sao_vang);
                diemFeedback = 5;
            }
        });

        img_anh_danh_gia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent photoPicker = new Intent();
                photoPicker.setAction(Intent.ACTION_GET_CONTENT);
                photoPicker.setType("image/*");
                activityResultLauncher.launch(photoPicker);
            }
        });


        btnFeedback.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {
                String noiDung = edFeedback.getText().toString().trim();
//                Number diemDanhGia = diemFeedback;
                int diemDanhGia = diemFeedback;

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH) + 1; // Tháng bắt đầu từ 0 nên cộng thêm 1
                int day = calendar.get(Calendar.DAY_OF_MONTH);
                String currentDate = day + "-" + month + "-" + year;
                final  DatabaseReference reference = FirebaseDatabase.getInstance().getReference("image");
                final  StorageReference storageReference = FirebaseStorage.getInstance().getReference();

                if(imageUri != null){
                    String url_src = System.currentTimeMillis()+"."+ getFileExtension(imageUri);
                    final StorageReference imageReference = storageReference.child(url_src);
                    imageReference.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            imageReference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    FeedbackAPI feedbackAPI = ApiRetrofit.getFeedbackAPI();
                                    Call<DanhGia> call = feedbackAPI.postDanhGia(new DanhGia(noiDung,uri.toString(),diemDanhGia,currentDate, new User(mySharedPreferences.getUserId()),new ChiTietDienThoai(chiTietHoaDon.getMaChiTietDienThoai().get_id())));
                                    call.enqueue(new Callback<DanhGia>() {
                                        @Override
                                        public void onResponse(Call<DanhGia> call, Response<DanhGia> response) {
                                            if (response.isSuccessful()){
                                                Toast.makeText(FeedbackScreen.this, "đã đánh giá", Toast.LENGTH_SHORT).show();
                                            }
                                        }

                                        @Override
                                        public void onFailure(Call<DanhGia> call, Throwable t) {
                                            Toast.makeText(FeedbackScreen.this, "Loi đánh giá", Toast.LENGTH_SHORT).show();
                                        }
                                    });
                                }
                            });
                            }
                    });
                }

            }
        });


    }

    private String getFileExtension(Uri fileUri){
        ContentResolver contentResolver = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(contentResolver.getType(fileUri));
    }



    final ActivityResultLauncher<Intent> activityResultLauncher = registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), new ActivityResultCallback<ActivityResult>() {
        @Override
        public void onActivityResult(ActivityResult result) {
            Intent data = result.getData();
            imageUri = data.getData();
            img_anh_danh_gia.setImageURI(imageUri);
        }
    });


}