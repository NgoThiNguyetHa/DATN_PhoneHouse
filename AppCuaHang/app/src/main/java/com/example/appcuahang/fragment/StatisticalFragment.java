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
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appcuahang.R;
import com.example.appcuahang.adapter.Top10Adapter;
import com.example.appcuahang.api.ApiRetrofit;
import com.example.appcuahang.api.ApiService;
import com.example.appcuahang.model.ThongKeDoanhThu;
import com.example.appcuahang.model.ThongKeDonHuy;
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
    private static final String[] days = new String[] {
            "7 ngày qua", "14 ngày qua", "1 tháng qua"
    };
    AutoCompleteTextView autoDropdown;

    ProgressBar progressBar;
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
        tvTongTienDaGiao = view.findViewById(R.id.thongke_tvTongTienDaGiao);
        tvTongTienDaHuy = view.findViewById(R.id.thongke_tvTongTienDaHuy);
        tvSoLuongDaHuy = view.findViewById(R.id.tvSoLuongDonHuy);
        thongke_bieuDoDoanhThu = view.findViewById(R.id.thongke_bieuDoDoanhThu);
        autoDropdown = view.findViewById(R.id.thongke_autoCompleTextView);
        mySharedPreferences = new MySharedPreferences(getContext());
        progressBar = view.findViewById(R.id.thongke_progressBar);
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

        getThongKeTrongNam(mySharedPreferences.getUserId());
        getTongDonHuy(mySharedPreferences.getUserId());
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                android.R.layout.simple_dropdown_item_1line, days);
        autoDropdown.setAdapter(adapter);
        autoDropdown.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (position == 0){
                    getDataTop10(mySharedPreferences.getUserId(),"7");
                }else if (position == 1){
                    getDataTop10(mySharedPreferences.getUserId(),"14");
                }else {
                    getDataTop10(mySharedPreferences.getUserId(),"30");
                }
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
//    private void testStyle (List<ThongKeTheoTungThang> dataList) {
//        ArrayList<BarEntry> entries = new ArrayList<>();
//        List<String> labels = new ArrayList<>();
//
//        for (int i = 0; i < dataList.size(); i++) {
//            entries.add(new BarEntry(dataList.get(i).get_id(), (float) dataList.get(i).getTongTien()));
//            labels.add("Tháng " + dataList.get(i).get_id());
//        }
//        dataSet = new BarDataSet(entries, "Tháng ");
//        BarData barData = new BarData(dataSet);
//        barData.setBarWidth(0.9f);
//
//        thongke_bieuDoDoanhThu.setData(barData);
//
//        YAxis yAxis = thongke_bieuDoDoanhThu.getAxisLeft();
//        yAxis.setAxisMinimum(0f);
//
//        thongke_bieuDoDoanhThu.getAxisRight().setEnabled(false);
//
//        XAxis xAxis = thongke_bieuDoDoanhThu.getXAxis();
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setLabelCount(12);
//        xAxis.setValueFormatter(null);
//        xAxis.setAxisMaximum(labels.size());
//        xAxis.setAxisMinimum(0.5f);
//        // Đặt nhãn cho trục X
//        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
//        xAxis.setDrawGridLines(false);
//        xAxis.setSpaceMin(2f);
//        xAxis.setSpaceMax(0.5f);
//        xAxis.setCenterAxisLabels(true);
//        xAxis.setGranularity(2f);
//        xAxis.setGranularityEnabled(true);
//        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
//        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
//        dataSet.setValueTextColor(Color.BLACK);
//        dataSet.setValueTypeface(Typeface.DEFAULT_BOLD);
//        dataSet.setValueTextSize(9.5f);
//        Description description = new Description();
//        description.setText("");
//        thongke_bieuDoDoanhThu.setDescription(description);
//        thongke_bieuDoDoanhThu.animateY(2000);
//        thongke_bieuDoDoanhThu.setDragEnabled(true);
//        thongke_bieuDoDoanhThu.setScaleEnabled(true);
//        thongke_bieuDoDoanhThu.setVisibleXRangeMaximum(5);
//        thongke_bieuDoDoanhThu.setHorizontalScrollBarEnabled(true);
//        thongke_bieuDoDoanhThu.getLegend().setEnabled(false);
//        thongke_bieuDoDoanhThu.invalidate();
//
//    }
    private void testStyle (List<ThongKeTheoTungThang> dataList) {
        ArrayList<BarEntry> entries = new ArrayList<>();
        List<String> labels = new ArrayList<>();

        for (int i = 0; i < dataList.size(); i++) {
            entries.add(new BarEntry(dataList.get(i).get_id(), (float) dataList.get(i).getTongTien()));
            labels.add("Tháng " + (i));
            if (i == dataList.size() - 1) {
                labels.add("Tháng " + 12);
            }
        }
        dataSet = new BarDataSet(entries, "Tháng ");
        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.9f);

        thongke_bieuDoDoanhThu.setData(barData);

        YAxis yAxis = thongke_bieuDoDoanhThu.getAxisLeft();
        yAxis.setAxisMinimum(100f);

        thongke_bieuDoDoanhThu.getAxisRight().setEnabled(false);

        XAxis xAxis = thongke_bieuDoDoanhThu.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setLabelCount(13);
        xAxis.setValueFormatter(null);
//        xAxis.setAxisMaximum(labels.size());
        xAxis.setAxisMaximum(12.5f);
        xAxis.setAxisMinimum(0.5f);
        // Đặt nhãn cho trục X
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawGridLines(false);
//        xAxis.setSpaceMin(2f);
//        xAxis.setSpaceMax(0.5f);
//        xAxis.setCenterAxisLabels(true);
//        xAxis.setGranularity(2f);
        xAxis.setGranularity(1f);
        xAxis.setSpaceMin(3f);
        xAxis.setSpaceMax(3f);
        xAxis.setGranularityEnabled(true);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
//        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
//        dataSet.setValueTextColor(Color.BLACK);
//        dataSet.setValueTypeface(Typeface.DEFAULT_BOLD);
//        dataSet.setValueTextSize(9.5f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextColor(Color.DKGRAY);
        dataSet.setValueTypeface(Typeface.DEFAULT_BOLD);
        dataSet.setValueTextSize(9f);
        Description description = new Description();
        description.setText("");
//        thongke_bieuDoDoanhThu.setDescription(description);
//        thongke_bieuDoDoanhThu.animateY(2000);
//        thongke_bieuDoDoanhThu.setDragEnabled(true);
//        thongke_bieuDoDoanhThu.setScaleEnabled(true);
//        thongke_bieuDoDoanhThu.setVisibleXRangeMaximum(4);
//        thongke_bieuDoDoanhThu.setHorizontalScrollBarEnabled(true);
//        thongke_bieuDoDoanhThu.getLegend().setEnabled(false);
//        thongke_bieuDoDoanhThu.invalidate();
        thongke_bieuDoDoanhThu.animateY(2000); // Hiệu ứng animation khi hiển thị biểu đồ
        thongke_bieuDoDoanhThu.setDescription(description);
        thongke_bieuDoDoanhThu.invalidate(); // refresh
        thongke_bieuDoDoanhThu.setDragEnabled(true);
        thongke_bieuDoDoanhThu.setScaleEnabled(true);
        thongke_bieuDoDoanhThu.setVisibleXRangeMaximum(4); // Số lượng cột tối đa hiển thị trên màn hình
        thongke_bieuDoDoanhThu.setHorizontalScrollBarEnabled(true);
        thongke_bieuDoDoanhThu.invalidate(); // Vẽ lại biểu đồ
        thongke_bieuDoDoanhThu.setGridBackgroundColor(Color.WHITE);

    }
    private void getDataTop10(String idCuaHang , String day){
        ApiService apiService = ApiRetrofit.getApiService();
        top10sanPhamList = new ArrayList<>();
        Call<List<Top10sanPham>> call = apiService.getTop10SP(idCuaHang,day);
        call.enqueue(new Callback<List<Top10sanPham>>() {
            @Override
            public void onResponse(Call<List<Top10sanPham>> call, Response<List<Top10sanPham>> response) {
                if (response.isSuccessful()) {
                    List<Top10sanPham> data = response.body();
                    top10sanPhamList.clear();
                    top10sanPhamList.addAll(data);
                    top10Adapter.setData(top10sanPhamList);
                    top10Adapter.notifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
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

    private void getTongDonHuy(String idCuaHang){
        ApiService apiService = ApiRetrofit.getApiService();
        Call<ThongKeDonHuy> call = apiService.getSoLuongTongTienDonHuy(idCuaHang);
        call.enqueue(new Callback<ThongKeDonHuy>() {
            @Override
            public void onResponse(Call<ThongKeDonHuy> call, Response<ThongKeDonHuy> response) {
                if (response.isSuccessful()) {
                    ThongKeDonHuy data = response.body();
                    tvSoLuongDaHuy.setText(""+data.getCount());
                    DecimalFormat decimalFormat = new DecimalFormat("#,##0");
                    String formattedNumber = decimalFormat.format(data.getTotalAmount());
                    tvTongTienDaHuy.setText(""+formattedNumber+"đ");
                } else {
                    Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<ThongKeDonHuy> call, Throwable t) {
                Log.e("brand", t.getMessage());
            }
        });
    }
}