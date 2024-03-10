package com.example.appcuahang.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.appcuahang.R;
import com.example.appcuahang.adapter.PhoneAdapter;
import com.example.appcuahang.api.ApiRetrofit;
import com.example.appcuahang.api.ApiService;
import com.example.appcuahang.databinding.ActivityMainBinding;
import com.example.appcuahang.databinding.DialogDienThoaiBinding;
import com.example.appcuahang.interface_adapter.IItemPhoneListenner;
import com.example.appcuahang.model.Brand;
import com.example.appcuahang.model.Phone;
import com.example.appcuahang.untils.MySharedPreferences;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class DienThoaiActivity extends AppCompatActivity {
    DialogDienThoaiBinding binding;
    PhoneAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DialogDienThoaiBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        addData();
    }


    private void addData(){
        ApiService apiService = ApiRetrofit.getApiService();
        binding.addDienThoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Call<Phone> call = apiService.addDienThoai(new Phone("strTenDT","strKichThuoc","strCNMH","strCamera","strCPu","strPin","strHeDieuHanh","strDoPhanGiai","strNamSX","strBaoHanh","strMoTa","idHang", "uri.toString()",""));
                call.enqueue(new Callback<Phone>() {
                    @Override
                    public void onResponse(Call<Phone> call, Response<Phone> response) {
                        if (response.isSuccessful()) {
                            Toast.makeText(getApplicationContext(), "Thêm mới thành công", Toast.LENGTH_SHORT).show();
                        }
                    }
                    @Override
                    public void onFailure(Call<Phone> call, Throwable t) {
                        Toast.makeText(getApplicationContext(), "" + t.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }


    private void editPhone(Phone phone){

    }
}