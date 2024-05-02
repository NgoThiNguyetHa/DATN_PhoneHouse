package com.example.appkhachhang.Fragment;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appkhachhang.Adapter.AddressAdapter;
import com.example.appkhachhang.Adapter.NotificationAdapter;
import com.example.appkhachhang.Api.Address_API;
import com.example.appkhachhang.Api.ApiRetrofit;
import com.example.appkhachhang.Api.ApiService;
import com.example.appkhachhang.Interface.OnItemClickListenerNotification;
import com.example.appkhachhang.Interface_Adapter.IItemAddressListenner;
import com.example.appkhachhang.MainActivity;
import com.example.appkhachhang.Model.AddressDelivery;
import com.example.appkhachhang.Model.HoaDon;
import com.example.appkhachhang.Model.Notification;
import com.example.appkhachhang.R;
import com.example.appkhachhang.activity.ThongTinDonHangActivity;
import com.example.appkhachhang.untils.MySharedPreferences;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NotificationFragment extends Fragment {
    RecyclerView rc_thongBao;
    TextView tvDocTatCa;
    List<Notification> list;
    NotificationAdapter adapter;
    LinearLayoutManager manager;
    MySharedPreferences mySharedPreferences;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_notification, container, false);
        ((getActivity())).setTitle("Thông Báo");
        initView(view);
        initVariable();
        getData();
        return  view;
    }
    private void initView(View view) {
        rc_thongBao = view.findViewById(R.id.rc_thongBao);
        tvDocTatCa = view.findViewById(R.id.tvDocTatCa);
    }
    private void initVariable() {
        list = new ArrayList<>();
        manager = new LinearLayoutManager(getContext());
        rc_thongBao.setLayoutManager(manager);
        adapter = new NotificationAdapter(getContext(), list, new OnItemClickListenerNotification() {
            @Override
            public void onItemClick(Notification notification) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("infoOrder", notification.getMaHoaDon());
                InfoOrderFragment fragmentB = new InfoOrderFragment();
                fragmentB.setArguments(bundle);
                Intent intent = new Intent(getActivity(), ThongTinDonHangActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);

                updateTrangThaiThongBao(notification.get_id());
            }
        });
        rc_thongBao.setAdapter(adapter);

        tvDocTatCa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                handleReadAll();
            }
        });
    }

    private void getData() {
        ApiService apiService = ApiRetrofit.getApiService();
        mySharedPreferences = new MySharedPreferences(getContext());
        Call<List<Notification>> call = apiService.getThongBao("khachhang", mySharedPreferences.getUserId());
        call.enqueue(new Callback<List<Notification>>() {
            @Override
            public void onResponse(Call<List<Notification>> call, Response<List<Notification>> response) {
                if (response.isSuccessful()) {
                    List<Notification> data = response.body();
                    list.clear();
                    list.addAll(data);
                    adapter.notifyDataSetChanged();
                } else {
                    // Handle error
                    Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Notification>> call, Throwable t) {
                // Handle failure
//                Log.e("mau", t.getMessage());
            }
        });
    }

    private void updateTrangThaiThongBao(String id){
        ApiService apiService = ApiRetrofit.getApiService();
        Call<String> call = apiService.updateThongBao(id, new Notification("1"));
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()){
                    getData();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {

            }
        });
    }

    private void readAll() {
        ApiService apiService = ApiRetrofit.getApiService();
        mySharedPreferences = new MySharedPreferences(getContext());
        Call<String> call = apiService.updateAllThongBao("khachhang", mySharedPreferences.getUserId());
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    getData();
                } else {
                    // Handle error
                    Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                // Handle failure
            }
        });
    }

    private void handleReadAll() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        View view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_yes_no, null);
        builder.setView(view);
        AlertDialog dialog = builder.create();
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

        Button btnYes = view.findViewById(R.id.yesButton);
        Button btnNo = view.findViewById(R.id.noButton);
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) TextView tvMessage = view.findViewById(R.id.tvMessage);

        tvMessage.setText("Bạn có muốn đánh dấu tất cả là đã đọc? Lưu ý hành động này không thể gỡ bỏ.");

        btnNo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        btnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readAll();
                dialog.dismiss();
            }
        });

    }

    @Override
    public void onPause() {
        getData();
        super.onPause();
    }

    @Override
    public void onResume() {
        getData();
        super.onResume();
    }
}