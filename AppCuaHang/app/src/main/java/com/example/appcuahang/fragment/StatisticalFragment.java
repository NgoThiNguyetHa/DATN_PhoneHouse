package com.example.appcuahang.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appcuahang.R;
import com.example.appcuahang.adapter.Top10Adapter;
import com.example.appcuahang.api.ApiRetrofit;
import com.example.appcuahang.api.ApiService;
import com.example.appcuahang.model.ThongKeDoanhThu;
import com.example.appcuahang.model.Top10sanPham;
import com.example.appcuahang.untils.MySharedPreferences;
import com.github.mikephil.charting.charts.BarChart;
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
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class StatisticalFragment extends Fragment {
    RecyclerView rc_top10;
    TextView tvTongTienDaGiao , tvTongTienDaHuy;
    TextView tvSoLuongDaHuy;
    LinearLayoutManager manager;
    TextView tvTuNgay , tvDenNgay;
    List<Top10sanPham> top10sanPhamList , listBackUp;
    Top10Adapter top10Adapter;
    BarChart thongke_bieuDoDoanhThu;
    private List<String> xValues = Arrays.asList("Doanh Thu");
    private static final String TRANG_THAI_DA_GIAO = "Đã giao";
    private static final String TRANG_THAI_DA_HUY = "Đã hủy";
    public float tongDoanhThuXuat , tongDoanhThuHuy ;
    MySharedPreferences mySharedPreferences;

    //date calendar
    Calendar calendar = Calendar.getInstance();
    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    int mYear,mMonth,mDay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_statistical, container, false);
        ((Activity) getContext()).setTitle("Thống Kê");
        initView(view);
        initVariable();
        return view;
    }

    private void initView(View view){
        rc_top10 = view.findViewById(R.id.rc_top10);
        tvTuNgay = view.findViewById(R.id.thongke_tvTuNgay);
        tvDenNgay = view.findViewById(R.id.thongke_tvDenNgay);
        tvTongTienDaGiao = view.findViewById(R.id.thongke_tvTongTienDaGiao);
        tvTongTienDaHuy = view.findViewById(R.id.thongke_tvTongTienDaHuy);
        tvSoLuongDaHuy = view.findViewById(R.id.tvSoLuongDonHuy);
        thongke_bieuDoDoanhThu = view.findViewById(R.id.thongke_bieuDoDoanhThu);
        mySharedPreferences = new MySharedPreferences(getContext());
    }
    private void initVariable(){
        top10sanPhamList = new ArrayList<>();
        listBackUp = new ArrayList<>();
        manager = new LinearLayoutManager(getContext());
        rc_top10.setLayoutManager(manager);
        top10Adapter = new Top10Adapter(getContext());
        top10Adapter.setData(top10sanPhamList);
        rc_top10.setAdapter(top10Adapter);

        //action date time

        DatePickerDialog.OnDateSetListener mDateTuNgay = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mYear = year;
                mMonth = month;
                mDay = dayOfMonth;
                GregorianCalendar c = new GregorianCalendar(mYear,mMonth,mDay);
                tvTuNgay.setText(sdf.format(c.getTime()));
            }
        };
        DatePickerDialog.OnDateSetListener mDateDenNgay = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                mYear = year;
                mMonth = month;
                mDay = dayOfMonth;
                GregorianCalendar c = new GregorianCalendar(mYear,mMonth,mDay);
                tvDenNgay.setText(sdf.format(c.getTime()));
            }
        };

        tvTuNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(getActivity(),0,mDateTuNgay,mYear,mMonth,mDay);
                d.show();
            }
        });
        tvDenNgay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                mYear = calendar.get(Calendar.YEAR);
                mMonth = calendar.get(Calendar.MONTH);
                mDay = calendar.get(Calendar.DAY_OF_MONTH);
                DatePickerDialog d = new DatePickerDialog(getActivity(),0,mDateDenNgay,mYear,mMonth,mDay);
                d.show();
            }
        });
        tvDenNgay.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                String tuNgay = tvTuNgay.getText().toString();
                String denNgay = s.toString();
                if (tuNgay.isEmpty() || denNgay.isEmpty()){
                    Toast.makeText(getContext(), "Yêu cầu chọn ngày", Toast.LENGTH_SHORT).show();
                    return;
                }
                TongDoanhThu(mySharedPreferences.getUserId(),tuNgay,denNgay);
                getDataTop10(mySharedPreferences.getUserId(),tuNgay,denNgay);
            }
        });

    }
    private void getDataTop10(String idCuaHang , String tuNgay, String denNgay){
        ApiService apiService = ApiRetrofit.getApiService();
        top10sanPhamList = new ArrayList<>();
        Call<List<Top10sanPham>> call = apiService.getTop10Product(tuNgay,denNgay,idCuaHang);
        call.enqueue(new Callback<List<Top10sanPham>>() {
            @Override
            public void onResponse(Call<List<Top10sanPham>> call, Response<List<Top10sanPham>> response) {
                if (response.isSuccessful()) {
                    List<Top10sanPham> data = response.body();
                    top10sanPhamList.clear();
                    top10sanPhamList.addAll(data);
                    top10Adapter.setData(top10sanPhamList);
                    top10Adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Top10sanPham>> call, Throwable t) {
                Log.e("brand", t.getMessage());
            }
        });
    }

    private void TongDoanhThu(String idCuaHang , String tuNgay, String denNgay){
        ApiService apiService = ApiRetrofit.getApiService();
        Call<ThongKeDoanhThu> thongKeDonHangXuat = apiService.getDoanhThuTheoNgay(tuNgay,denNgay,idCuaHang,TRANG_THAI_DA_GIAO);
        thongKeDonHangXuat.enqueue(new Callback<ThongKeDoanhThu>() {
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
                    float tongDoanhThu = tongDoanhThuXuat;
                    DecimalFormat decimalFormat = new DecimalFormat("#,##0");
                    String formattedNumber = decimalFormat.format(tongDoanhThu);
                    tvTongTienDaGiao.setText("" + formattedNumber+ " đ");
                } else {
                    Log.e("doanhthuxuat",response.message());
                }
            }
            @Override
            public void onFailure(Call<ThongKeDoanhThu> call, Throwable t) {
                Log.e("doanhthuxuat",t.getMessage());
            }
        });


        Call<ThongKeDoanhThu> thongKeDonHangHuy = apiService.getDoanhThuTheoNgay(tuNgay,denNgay,mySharedPreferences.getUserId(),TRANG_THAI_DA_HUY);
        thongKeDonHangHuy.enqueue(new Callback<ThongKeDoanhThu>() {
            @Override
            public void onResponse(Call<ThongKeDoanhThu> call, Response<ThongKeDoanhThu> response) {
                if (response.isSuccessful()) {
                    ThongKeDoanhThu res = response.body();
                    tvSoLuongDaHuy.setText(""+res.getSoLuongHoaDon());
                    if (res.getTongTien() > 0) {
                        tongDoanhThuHuy = res.getTongTien();
                    }else{
                        tongDoanhThuHuy = 0;
                    }
                    float tongTienHuy = tongDoanhThuHuy;
                    DecimalFormat decimalFormat = new DecimalFormat("#,##0");
                    String formattedNumber = decimalFormat.format(tongTienHuy);
                    tvTongTienDaHuy.setText("" + formattedNumber+ " đ");
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
        thongke_bieuDoDoanhThu.getAxisRight().setDrawLabels(false);

        ArrayList<BarEntry> entries = new ArrayList<>();
        entries.add(new BarEntry(0,tongDoanhThuXuat));

        YAxis yAxis = thongke_bieuDoDoanhThu.getAxisLeft();
        yAxis.setAxisMinimum(0f);
        yAxis.setAxisMaximum(0f);
        yAxis.setAxisMaximum(tongDoanhThuXuat);
        yAxis.setAxisLineWidth(1f);
        yAxis.setAxisLineColor(Color.BLACK);
        yAxis.setLabelCount(10);

        BarDataSet dataSet = new BarDataSet(entries,"Doanh thu đơn hàng");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(11f);
        dataSet.setValueTextSize(11f);


        BarData barData = new BarData(dataSet);
        thongke_bieuDoDoanhThu.setData(barData);

        thongke_bieuDoDoanhThu.getDescription().setEnabled(false);
        thongke_bieuDoDoanhThu.invalidate();

        thongke_bieuDoDoanhThu.getXAxis().setValueFormatter(new IndexAxisValueFormatter(xValues));
        thongke_bieuDoDoanhThu.getXAxis().setPosition(XAxis.XAxisPosition.BOTTOM);
        thongke_bieuDoDoanhThu.getXAxis().setGranularity(1f);
        thongke_bieuDoDoanhThu.getXAxis().setGranularityEnabled(true);
        thongke_bieuDoDoanhThu.animateY(2000);
    }
}