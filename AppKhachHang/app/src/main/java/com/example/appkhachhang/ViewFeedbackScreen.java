package com.example.appkhachhang;

import static com.google.common.io.Files.getFileExtension;

import androidx.activity.result.ActivityResult;
import androidx.activity.result.ActivityResultCallback;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.appkhachhang.Adapter.ViewFeedbackAdapter;
import com.example.appkhachhang.Api.ApiRetrofit;
import com.example.appkhachhang.Api.FeedbackAPI;
import com.example.appkhachhang.Fragment.updateDanhGiaFragment;
import com.example.appkhachhang.Interface.OnItemClickListenerDanhGia;
import com.example.appkhachhang.Interface.OnItemClickListenerUpdateDanhGia;
import com.example.appkhachhang.Model.ChiTietDienThoai;
import com.example.appkhachhang.Model.ChiTietHoaDon;
import com.example.appkhachhang.Model.DanhGia;
import com.example.appkhachhang.Model.User;
import com.example.appkhachhang.untils.MySharedPreferences;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ViewFeedbackScreen extends AppCompatActivity {
    RecyclerView rc_Feedback;
    LinearLayoutManager manager;
    MySharedPreferences mySharedPreferences;
    ViewFeedbackAdapter adapter;

    List<DanhGia> list = new ArrayList<>();
    Uri imageUri;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_feedback_screen);
        rc_Feedback = findViewById(R.id.rc_ViewFeedback);

        getData();
    }

    private void getData() {
        list = new ArrayList<>();
        manager = new LinearLayoutManager(getApplicationContext());
        rc_Feedback.setLayoutManager(manager);

        mySharedPreferences = new MySharedPreferences(getApplicationContext());
        getDanhGiaTheoKhachHang(mySharedPreferences.getUserId());
//        Log.e("zzzzz", "getData: "+ mySharedPreferences.getUserId() );
    }

    private void getDanhGiaTheoKhachHang(String userId) {
        FeedbackAPI feedbackAPI = ApiRetrofit.getFeedbackAPI();
        Call<List<DanhGia>> call = feedbackAPI.getDanhGia(userId);
        call.enqueue(new Callback<List<DanhGia>>() {
            @Override
            public void onResponse(Call<List<DanhGia>> call, Response<List<DanhGia>> response) {
                if (response.isSuccessful()) {
                    List<DanhGia> danhGia = response.body();
                    adapter = new ViewFeedbackAdapter(getApplicationContext(), danhGia, new OnItemClickListenerUpdateDanhGia() {
                        @Override
                        public void onItemClickUpdateDanhGia(DanhGia danhGia) {
                            Dialog dialog = new Dialog(ViewFeedbackScreen.this);
                            dialog.setContentView(R.layout.dialog_update_feedback);


                            Bundle bundle = new Bundle();
                            bundle.putSerializable("updateDanhGia", danhGia);
                            Intent intent = new Intent(ViewFeedbackScreen.this, updateDanhGiaFragment.class);
                            intent.putExtras(bundle);
                            startActivity(intent);
                        }
                    });

                    rc_Feedback.setAdapter(adapter);
                    Log.e("zzzz", "onResponse: " + response);
                }
            }

            @Override
            public void onFailure(Call<List<DanhGia>> call, Throwable t) {
                Log.e("zzzzz", "onFailure: " + t.getMessage());
            }
        });
    }
}