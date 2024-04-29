package com.example.appkhachhang.StoreDetailViewPager;

import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.appkhachhang.Adapter.DanhGiaAdapter;
import com.example.appkhachhang.Api.ApiRetrofit;
import com.example.appkhachhang.Api.ApiService;
import com.example.appkhachhang.Model.DanhGia;
import com.example.appkhachhang.Model.Root;
import com.example.appkhachhang.Model.Store;
import com.example.appkhachhang.R;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class ThongTinCuaHangFragment extends Fragment {

    TextView tvTiLeHuy, tvTiLeThanhCong ,tvSoLuongSanPham,tvSoLuongDanhGia,tvEmail,tvDanhGia, tvSoLuongDanhGia1,tvSlg5sao,tvSlg4sao,tvSlg3sao,tvSlg2sao,tvSlg1sao;
    Button btnGetAll,  btnGetHinhAnh , btnGet5sao , btnGet4sao , btnGet3sao , btnGet2sao , btnGet1sao;

    RecyclerView rc_DanhGia;
    List<DanhGia> list;
    DanhGiaAdapter adapter;
    LinearLayoutManager manager;
    Store cuaHang;
    SeekBar sb5sao,sb4sao,sb3sao,sb2sao;
    ProgressBar sb1sao;
    private boolean isButton1Clicked = false;
    private Button lastClickedButton = null;
    BarChart barChart;
    private List<String> xValues = Arrays.asList("Đơn Hàng Đã Giao","Đơn Hàng Hủy");


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_thong_tin_cua_hang, container, false);
        initView(view);
        initVariable();
        getDataBundle();
        return view;
    }

    private void initView(View view){
        tvTiLeHuy = view.findViewById(R.id.ttCuaHang_tvTiLeHuyDon);
        tvTiLeThanhCong = view.findViewById(R.id.ttCuaHang_tvTiLeThanhCong);
        tvSoLuongSanPham = view.findViewById(R.id.ttCuaHang_tvSoLuongSanPham);
        tvSoLuongDanhGia = view.findViewById(R.id.ttCuaHang_tvSoLuongDanhGia);
        tvEmail = view.findViewById(R.id.ttCuaHang_tvEmail);
        tvDanhGia = view.findViewById(R.id.ttCuaHang_tvDanhGia);
        tvSoLuongDanhGia1 = view.findViewById(R.id.ttCuaHang_tvSoLuongDanhGia1);
//        tvSlg5sao = view.findViewById(R.id.ttCuaHang_tvSlg5sao);
//        tvSlg4sao = view.findViewById(R.id.ttCuaHang_tvSlg4sao);
//        tvSlg3sao = view.findViewById(R.id.ttCuaHang_tvSlg3sao);
//        tvSlg2sao = view.findViewById(R.id.ttCuaHang_tvSlg2sao);
//        tvSlg1sao = view.findViewById(R.id.ttCuaHang_tvSlg1sao);
        btnGetAll = view.findViewById(R.id.ttCuaHang_btnGetAll);
        btnGetHinhAnh = view.findViewById(R.id.ttCuaHang_btnGetCoAnh);
        btnGet5sao = view.findViewById(R.id.ttCuaHang_btnGet5sao);
        btnGet4sao = view.findViewById(R.id.ttCuaHang_btnGet4sao);
        btnGet3sao = view.findViewById(R.id.ttCuaHang_btnGet3sao);
        btnGet2sao = view.findViewById(R.id.ttCuaHang_btnGet2sao);
        btnGet1sao = view.findViewById(R.id.ttCuaHang_btnGet1sao);
        rc_DanhGia = view.findViewById(R.id.ttCuaHang_rcDanhGia);
        //
//        sb5sao = view.findViewById(R.id.ttCuaHang_sb5sao);
//        sb4sao = view.findViewById(R.id.ttCuaHang_sb4sao);
//        sb3sao = view.findViewById(R.id.ttCuaHang_sb3sao);
//        sb2sao = view.findViewById(R.id.ttCuaHang_sb2sao);
//        sb1sao = view.findViewById(R.id.ttCuaHang_sb1sao);

        barChart = view.findViewById(R.id.ttCuaHang_barChart);

    }
    private void initVariable(){
        list = new ArrayList<>();
        manager = new LinearLayoutManager(getContext());
        rc_DanhGia.setLayoutManager(manager);
        adapter = new DanhGiaAdapter(getContext());
        adapter.setData(list);
        rc_DanhGia.setAdapter(adapter);

    }
    private void getDataBundle(){
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            cuaHang = (Store) bundle.getSerializable("cuaHang");
            getData(cuaHang.get_id());
            //set textview
            tvEmail.setText(""+cuaHang.getEmail());
            actionButton(cuaHang.get_id());
            getDataToSetText(cuaHang.get_id());
        }
    }
    private void getData(String id){
        ApiService apiService = ApiRetrofit.getApiService();
        Call<List<DanhGia>> ratingListCall = apiService.getDanhGiaTheoCuaHang(id);
        ratingListCall.enqueue(new Callback<List<DanhGia>>() {
            @Override
            public void onResponse(Call<List<DanhGia>> call, Response<List<DanhGia>> response) {
                if (response.isSuccessful()) {
                    List<DanhGia> data = response.body();
                    list.clear();
                    list.addAll(data);
                    adapter.notifyDataSetChanged();
                    tvSoLuongDanhGia1.setText("("+list.size() + " đánh giá)");

                    int tongDiemDanhGia = 0;
                    int[] countByRating = new int[6];
                    int countHinhAnh = 0;
                    for (int i = 0; i < list.size(); i++) {
                        tongDiemDanhGia += list.get(i).getDiemDanhGia();
                        int rating = list.get(i).getDiemDanhGia();
                        if (rating >= 0 && rating <= 5) {
                            countByRating[rating]++;
                        }

                        if (!list.get(i).getHinhAnh().equals("")){
                            countHinhAnh++;
                        }
                    }
                    //set btn
                    btnGet5sao.setText(String.valueOf("5 sao "+"(" + countByRating[5]) +")");
                    btnGet4sao.setText(String.valueOf("4 sao "+"(" + countByRating[4]) +")");
                    btnGet3sao.setText(String.valueOf("3 sao "+"(" + countByRating[3]) +")");
                    btnGet2sao.setText(String.valueOf("2 sao "+"(" + countByRating[2]) +")");
                    btnGet1sao.setText(String.valueOf("1 sao "+"(" + countByRating[1]) +")");
                    btnGetHinhAnh.setText(("Có ảnh "+"( " + countHinhAnh +" )"));
                    int soLuongDanhGia = list.size();
                    float diemTrungBinh = 0;
                    if (soLuongDanhGia != 0) {
                        diemTrungBinh = (float) tongDiemDanhGia / soLuongDanhGia;
                    }
                    barChart(list.size(),countByRating);
                    tvDanhGia.setText(""+(diemTrungBinh));
                    tvSoLuongDanhGia.setText(""+(diemTrungBinh) + "/5" + " (" + list.size() + "+)" );


                } else {
                    Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<DanhGia>> call, Throwable t) {
                Log.e("Rating", t.getMessage());
            }
        });
    }

    private void actionButton(String id){
        btnGetAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ApiService apiService = ApiRetrofit.getApiService();
                Call<List<DanhGia>> ratingListCall = apiService.getDanhGiaTheoCuaHang(id);
                ratingListCall.enqueue(new Callback<List<DanhGia>>() {
                    @Override
                    public void onResponse(Call<List<DanhGia>> call, Response<List<DanhGia>> response) {
                        if (response.isSuccessful()) {
                            List<DanhGia> data = response.body();
                            list.clear();
                            list.addAll(data);
                            adapter.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<DanhGia>> call, Throwable t) {
                        Log.e("Rating", t.getMessage());
                    }
                });
                setButtonColor(btnGetAll);
            }
        });
        btnGetHinhAnh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getDanhGiaHinhAnh(id);
                setButtonColor(btnGetHinhAnh);
            }
        });
        btnGet5sao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFilterSoLuotDanhGia(id,5);
                setButtonColor(btnGet5sao);
            }
        });
        btnGet4sao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFilterSoLuotDanhGia(id,4);
                setButtonColor(btnGet4sao);
            }
        });
        btnGet3sao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFilterSoLuotDanhGia(id,3);
                setButtonColor(btnGet3sao);
            }
        });
        btnGet2sao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFilterSoLuotDanhGia(id,2);
                setButtonColor(btnGet2sao);
            }
        });
        btnGet1sao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFilterSoLuotDanhGia(id,1);
                setButtonColor(btnGet1sao);
            }
        });
    }
    private void getFilterSoLuotDanhGia(String id, int diem){
        ApiService apiService = ApiRetrofit.getApiService();
        Call<List<DanhGia>> ratingListCall = apiService.getSoLuotDanhGiaTheoCuaHang(id,diem);
        ratingListCall.enqueue(new Callback<List<DanhGia>>() {
            @Override
            public void onResponse(Call<List<DanhGia>> call, Response<List<DanhGia>> response) {
                if (response.isSuccessful()) {
                    List<DanhGia> data = response.body();
                    list.clear();
                    list.addAll(data);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<DanhGia>> call, Throwable t) {

            }
        });
    }
    private void getDanhGiaHinhAnh(String id){
        ApiService apiService = ApiRetrofit.getApiService();
        Call<List<DanhGia>> ratingListCall = apiService.getDanhGiaHinhAnhTheoCuaHang(id);
        ratingListCall.enqueue(new Callback<List<DanhGia>>() {
            @Override
            public void onResponse(Call<List<DanhGia>> call, Response<List<DanhGia>> response) {
                if (response.isSuccessful()) {
                    List<DanhGia> data = response.body();
                    list.clear();
                    list.addAll(data);
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<DanhGia>> call, Throwable t) {

            }
        });
    }
    private void getDataToSetText(String id){
        ApiService apiService = ApiRetrofit.getApiService();
        Call<List<Root>> call1 = apiService.getAllDienThoai_CuaHang(id);
        call1.enqueue(new Callback<List<Root>>() {
            @Override
            public void onResponse(Call<List<Root>> call, Response<List<Root>> response) {
                if (response.isSuccessful()) {
                    List<Root> data = response.body();
                    tvSoLuongSanPham.setText(""+data.size()+"+");
                } else {
                    Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<Root>> call, Throwable t) {

            }
        });
    }
    private void setButtonColor(Button clickedButton) {
        if (lastClickedButton != null) {
            lastClickedButton.setTextColor(getResources().getColor(R.color.notCheked));
        }
        clickedButton.setTextColor(getResources().getColor(R.color.isChecked));
        lastClickedButton = clickedButton;
    }
    private void barChart(int tongTien, int[] countByRating) {
        List<String> labels = new ArrayList<>();
        ArrayList<BarEntry> entries = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            entries.add(new BarEntry((i+1), (float) countByRating[i+1]));
            labels.add((i) + "*");
            if (i == 4) {
                labels.add(5 + "*");
            }
        }

        YAxis leftAxis = barChart.getAxisLeft();
        leftAxis.setAxisMinimum(0f);
        leftAxis.setAxisMaximum(tongTien);
        leftAxis.setDrawAxisLine(false); // Disable drawing axis line
        leftAxis.setDrawGridLines(false); // Disable drawing grid lines
        leftAxis.setDrawLabels(false); // Disable drawing labels

        YAxis rightAxis = barChart.getAxisRight();
        rightAxis.setDrawLabels(false);
        rightAxis.setDrawAxisLine(false); // Disable drawing axis line
        rightAxis.setDrawGridLines(false); // Disable drawing grid lines

        BarDataSet dataSet = new BarDataSet(entries,"");
        dataSet.setColors(ColorTemplate.MATERIAL_COLORS);
        dataSet.setValueTextColor(Color.BLACK);
        dataSet.setValueTextSize(14f);
        dataSet.setDrawValues(true); // Ensure values are drawn on bars

        // Adjusting the width of bars
        float barWidth = 0.5f; // Adjust this value to your preference
        BarData barData = new BarData(dataSet);
        barData.setBarWidth(barWidth);

        barChart.setData(barData);

        // Set background color to transparent
        barChart.setBackgroundColor(Color.TRANSPARENT);

        barChart.getDescription().setEnabled(false);
        barChart.invalidate();

        // Set labels for the XAxis
        XAxis xAxis = barChart.getXAxis();
        xAxis.setTextSize(18f); // Điều chỉnh kích thước chữ ở đây
        xAxis.setValueFormatter(new IndexAxisValueFormatter(labels));
        xAxis.setGranularity(1f);
        xAxis.setSpaceMin(3f);
        xAxis.setSpaceMax(3f);
        xAxis.setGranularityEnabled(true);
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setDrawAxisLine(false); // Disable drawing axis line
        xAxis.setDrawGridLines(false); // Disable drawing grid lines

        barChart.animateY(2000);
    }
}