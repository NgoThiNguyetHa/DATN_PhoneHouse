package com.example.appcuahang.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.graphics.Color;
import android.graphics.Typeface;
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
import com.example.appcuahang.model.ThongKeTheoTungThang;
import com.example.appcuahang.model.Top10sanPham;
import com.example.appcuahang.untils.MySharedPreferences;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.AxisBase;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IBarDataSet;
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
    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
    BarDataSet dataSet;
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
        getThongKeTrongNam(mySharedPreferences.getUserId());
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
//                TongDoanhThu(mySharedPreferences.getUserId(),tuNgay,denNgay);
//                getDataTop10(mySharedPreferences.getUserId(),tuNgay,denNgay);
            }
        });

    }
    private void getThongKeTrongNam(String idCuaHang){
        ApiService apiService = ApiRetrofit.getApiService();
        Call<List<ThongKeTheoTungThang>> call = apiService.getThongKeTheoNam(currentYear,idCuaHang);
        call.enqueue(new Callback<List<ThongKeTheoTungThang>>() {
            @Override
            public void onResponse(Call<List<ThongKeTheoTungThang>> call, Response<List<ThongKeTheoTungThang>> response) {
                if (response.isSuccessful()) {
                    List<ThongKeTheoTungThang> res = response.body();
                    int tongDoanhThu = 0;
                    for (ThongKeTheoTungThang item : res){
                        tongDoanhThu += item.getTongTien();
                    }
                    testStyle(res);
                    // Hiển thị tổng doanh thu
                    DecimalFormat decimalFormat = new DecimalFormat("#,##0");
                    String formattedNumber = decimalFormat.format(tongDoanhThu);
                    tvTongTienDaGiao.setText("" + formattedNumber+ " đ");
                } else {
                    Log.e("thongketrongnam",response.message());
                }
            }

            @Override
            public void onFailure(Call<List<ThongKeTheoTungThang>> call, Throwable t) {
                Log.e("thongketrongnam",t.getMessage());
            }
        });

    }

    private void drawBarChart(List<ThongKeTheoTungThang> dataList) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>();
        for (int i = 0; i < dataList.size(); i++) {
            entries.add(new BarEntry(i, (float) dataList.get(i).getTongTien()));

        }

        BarDataSet barDataSet = new BarDataSet(entries, "Doanh thu");
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.BLACK);
        // Cấu hình dữ liệu cho biểu đồ
        BarData barData = new BarData(barDataSet);
        thongke_bieuDoDoanhThu.setData(barData);

        // Cấu hình trục X
        XAxis xAxis = thongke_bieuDoDoanhThu.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM); // Hiển thị trục X ở dưới
        xAxis.setDrawGridLines(false); // Tắt đường line trên biểu đồ
        xAxis.setGranularity(5f);
        xAxis.setSpaceMin(5f);
        xAxis.setSpaceMax(5f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        barDataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        barDataSet.setValueTextColor(Color.DKGRAY);
        barDataSet.setValueTypeface(Typeface.DEFAULT_BOLD);
        barDataSet.setValueTextSize(8f);
        // Cấu hình animation
        thongke_bieuDoDoanhThu.animateY(2000); // Hiệu ứng animation khi hiển thị biểu đồ
        thongke_bieuDoDoanhThu.invalidate(); // refresh
        thongke_bieuDoDoanhThu.setDragEnabled(true);
        thongke_bieuDoDoanhThu.setScaleEnabled(true);
        thongke_bieuDoDoanhThu.setVisibleXRangeMaximum(5); // Số lượng cột tối đa hiển thị trên màn hình
        thongke_bieuDoDoanhThu.setHorizontalScrollBarEnabled(true);
        thongke_bieuDoDoanhThu.invalidate(); // Vẽ lại biểu đồ
        thongke_bieuDoDoanhThu.setGridBackgroundColor(Color.WHITE);
    }

    //dùng
    private void testStyle (List<ThongKeTheoTungThang> dataList) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        for (int i = 0; i < dataList.size(); i++) {
            entries.add(new BarEntry(dataList.get(i).get_id(), (float) dataList.get(i).getTongTien()));
            labels.add("Tháng " + dataList.get(i).get_id());
        }
        dataSet = new BarDataSet(entries, "Tháng ");
        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.9f);

        thongke_bieuDoDoanhThu.setData(barData);

        YAxis yAxis = thongke_bieuDoDoanhThu.getAxisLeft();
        yAxis.setAxisMinimum(0f);

        thongke_bieuDoDoanhThu.getAxisRight().setEnabled(false);

        XAxis xAxis = thongke_bieuDoDoanhThu.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(12);
        xAxis.setValueFormatter(null);
        xAxis.setAxisMaximum(labels.size());
        xAxis.setAxisMinimum(0.5f);
        // Đặt nhãn cho trục X
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
        xAxis.setSpaceMin(2f);
        xAxis.setSpaceMax(0.5f);
        xAxis.setCenterAxisLabels(true);
        xAxis.setGranularity(2f);
        xAxis.setGranularityEnabled(true);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTypeface(Typeface.DEFAULT_BOLD);
        dataSet.setValueTextSize(9.5f);
        Description description = new Description();
        description.setText("");
        thongke_bieuDoDoanhThu.setDescription(description);
        thongke_bieuDoDoanhThu.animateY(2000);
        thongke_bieuDoDoanhThu.setDragEnabled(true);
        thongke_bieuDoDoanhThu.setScaleEnabled(true);
        thongke_bieuDoDoanhThu.setVisibleXRangeMaximum(5);
        thongke_bieuDoDoanhThu.setHorizontalScrollBarEnabled(true);
        thongke_bieuDoDoanhThu.getLegend().setEnabled(false);
        thongke_bieuDoDoanhThu.invalidate();

    }

}