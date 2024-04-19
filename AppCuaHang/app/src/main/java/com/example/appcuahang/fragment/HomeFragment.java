package com.example.appcuahang.fragment;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.appcuahang.R;
import com.example.appcuahang.api.ApiMauService;
import com.example.appcuahang.api.ApiRetrofit;
import com.example.appcuahang.api.ApiService;
import com.example.appcuahang.databinding.FragmentHomeBinding;
import com.example.appcuahang.model.Mau;
import com.example.appcuahang.model.ThongKeDoanhThu;
import com.example.appcuahang.model.ThongKeHoaDon;
import com.example.appcuahang.model.ThongKeKhachHang;
import com.example.appcuahang.model.ThongKeSanPham;
import com.example.appcuahang.untils.MySharedPreferences;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeFragment extends Fragment {

    FragmentHomeBinding binding;
    MySharedPreferences mySharedPreferences;
    Date currentDate = new Date();
    private static final String date = "18-09-2024";
    private static final String statusHuy = "Đã hủy";
    private static final String statusXuat = "Đã giao";
    public int tongDoanhThuXuat , tongDoanhThuHuy ;
    private List<String> xValues = Arrays.asList("Đơn Hàng Đã Giao","Đơn Hàng Hủy");

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(getLayoutInflater(), container, false);
        ((Activity)getContext()).setTitle("Màn Hình Chính");
        initVariable();
        return binding.getRoot();
    }

    private void initVariable(){
        mySharedPreferences = new MySharedPreferences(getContext());
        //set datetime today
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        String dateString = dateFormat.format(currentDate);

        //action
        binding.tvNgay.setText("Hôm nay, "+dateString);
        SoLuongHoaDon(mySharedPreferences.getUserId() , dateString);
        SoLuongKhachHang(mySharedPreferences.getUserId(),dateString);
        SoLuongSanPham(mySharedPreferences.getUserId(),dateString);
        TongDoanhThu(mySharedPreferences.getUserId(),date);
    }
    private void SoLuongHoaDon(String idCuaHang , String ngayTao){
        ApiService apiService = ApiRetrofit.getApiService();
        Call<List<ThongKeHoaDon>> hoaDonHuy = apiService.getCountByStatusAndDate(statusHuy,idCuaHang, ngayTao);
        hoaDonHuy.enqueue(new Callback<List<ThongKeHoaDon>>() {
            @Override
            public void onResponse(Call<List<ThongKeHoaDon>> call, Response<List<ThongKeHoaDon>> response) {
                if (response.isSuccessful()) {
                    List<ThongKeHoaDon> count = response.body();
                    if (count.size() > 0) {
                        binding.countOrderCancel.setText(""+count.get(0).getCount());
                    }else{
                        binding.countOrderCancel.setText("0");
                    }
                } else {
//                    Log.e("hoadonhuy",response.message());
                }
            }

            @Override
            public void onFailure(Call<List<ThongKeHoaDon>> call, Throwable t) {
                Log.e("hoadonhuy",t.getMessage());
            }
        });

        Call<List<ThongKeHoaDon>> hoaDonXuat = apiService.getCountByStatusAndDate(statusXuat,idCuaHang, ngayTao);
        hoaDonXuat.enqueue(new Callback<List<ThongKeHoaDon>>() {
            @Override
            public void onResponse(Call<List<ThongKeHoaDon>> call, Response<List<ThongKeHoaDon>> response) {
                if (response.isSuccessful()) {
                    List<ThongKeHoaDon> count = response.body();
                    if (count.size() > 0) {
                        binding.countOrderDone.setText(""+count.get(0).getCount());
                    }else{
                        binding.countOrderDone.setText("0");
                    }
                } else {
//                    Log.e("hoadonxuat",response.message());
                }
            }

            @Override
            public void onFailure(Call<List<ThongKeHoaDon>> call, Throwable t) {
                Log.e("hoadonxuat",t.getMessage());
            }
        });
    }

    private void SoLuongKhachHang(String idCuaHang , String ngayTao){
        ApiService apiService = ApiRetrofit.getApiService();
        Call<ThongKeKhachHang> call = apiService.getSoLuongKhachHang(idCuaHang, ngayTao);
        call.enqueue(new Callback<ThongKeKhachHang>() {
            @Override
            public void onResponse(Call<ThongKeKhachHang> call, Response<ThongKeKhachHang> response) {
                if (response.isSuccessful()) {
                    ThongKeKhachHang count = response.body();
                    if (count.getSoLuongKhachHang() > 0) {
                        binding.countClient.setText(""+count.getSoLuongKhachHang());
                    }else{
                        binding.countClient.setText("0");
                    }
                } else {
                    Log.e("khachhang",response.message());
                }
            }

            @Override
            public void onFailure(Call<ThongKeKhachHang> call, Throwable t) {
                Log.e("khachhang",t.getMessage());
            }
        });
    }

    private void SoLuongSanPham(String idCuaHang , String ngayTao){
        ApiService apiService = ApiRetrofit.getApiService();
        Call<ThongKeSanPham> call = apiService.getSoLuongSanPham(idCuaHang, ngayTao);
        call.enqueue(new Callback<ThongKeSanPham>() {
            @Override
            public void onResponse(Call<ThongKeSanPham> call, Response<ThongKeSanPham> response) {
                if (response.isSuccessful()) {
                    ThongKeSanPham count = response.body();
                    if (count.getSoLuongDaBan() > 0) {
                        binding.countProduct.setText(""+count.getSoLuongDaBan());
                    }else{
                        binding.countProduct.setText("0");
                    }
                } else {
                    Log.e("sanpham",response.message());
                }
            }

            @Override
            public void onFailure(Call<ThongKeSanPham> call, Throwable t) {
                Log.e("sanpham",t.getMessage());
            }
        });
    }

    private void TongDoanhThu(String idCuaHang, String ngayTao){
        ApiService apiService = ApiRetrofit.getApiService();
        Call<ThongKeDoanhThu> doanhThuXuat = apiService.getDoanhThuHomNay(statusXuat,idCuaHang, ngayTao);
        doanhThuXuat.enqueue(new Callback<ThongKeDoanhThu>() {
            @Override
            public void onResponse(Call<ThongKeDoanhThu> call, Response<ThongKeDoanhThu> response) {
                if (response.isSuccessful()) {
                    ThongKeDoanhThu res = response.body();
                    if (res.getTongTien() > 0) {
                        tongDoanhThuXuat = res.getTongTien();
                    }else{
                        tongDoanhThuXuat = 0;
                    }
                    barChart();
                } else {
                    Log.e("doanhthuxuat",response.message());
                }
            }
            @Override
            public void onFailure(Call<ThongKeDoanhThu> call, Throwable t) {
                Log.e("doanhthuxuat",t.getMessage());
            }
        });


        Call<ThongKeDoanhThu> doanhThuHuy = apiService.getDoanhThuHomNay(statusHuy,idCuaHang, ngayTao);
        doanhThuHuy.enqueue(new Callback<ThongKeDoanhThu>() {
            @Override
            public void onResponse(Call<ThongKeDoanhThu> call, Response<ThongKeDoanhThu> response) {
                if (response.isSuccessful()) {
                    ThongKeDoanhThu res = response.body();
                    if (res.getTongTien() > 0) {
                        tongDoanhThuHuy = res.getTongTien();
                    }else{
                        tongDoanhThuHuy = 0;
                    }
                    barChart();
                } else {
                    Log.e("doanhthuhuy",response.message());
                }
            }
            @Override
            public void onFailure(Call<ThongKeDoanhThu> call, Throwable t) {
                Log.e("doanhthuhuy",t.getMessage());
            }
        });
    }

    private void barChart() {
        int tongDoanhThu = tongDoanhThuXuat - tongDoanhThuHuy;
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        String formattedNumber = decimalFormat.format(tongDoanhThu);
        binding.tvTongDoanhThu.setText("" + formattedNumber);
        binding.homeBieuDoDoanhThu.getAxisRight().setDrawLabels(false);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0,tongDoanhThuXuat));
        entries.add(new BarEntry(1,tongDoanhThuHuy));

        YAxis yAxis = binding.homeBieuDoDoanhThu.getAxisLeft();
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(0f);
        yAxis.setAxisMaximum(tongDoanhThuXuat+tongDoanhThuHuy);
        yAxis.setAxisLineWidth(1f);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setLabelCount(10);

        BarDataSet dataSet = new BarDataSet(entries,"Subjects");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(11f);
        dataSet.setValueTextSize(11f);


        BarData barData = new BarData(dataSet);
        binding.homeBieuDoDoanhThu.setData(barData);

        binding.homeBieuDoDoanhThu.getDescription().setEnabled(false);
        binding.homeBieuDoDoanhThu.invalidate();

        binding.homeBieuDoDoanhThu.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xValues));
        binding.homeBieuDoDoanhThu.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        binding.homeBieuDoDoanhThu.getXAxis().setGranularity(1f);
        binding.homeBieuDoDoanhThu.getXAxis().setGranularityEnabled(true);
        binding.homeBieuDoDoanhThu.animateY(2000);
    }
}