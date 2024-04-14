package com.example.appcuahang.fragment;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcuahang.R;
import com.example.appcuahang.adapter.ChiTietHoaDonAdapter;
import com.example.appcuahang.api.ApiRetrofit;
import com.example.appcuahang.api.ApiService;
import com.example.appcuahang.model.ChiTietHoaDon;
import com.example.appcuahang.model.DetailPhone;
import com.example.appcuahang.model.HoaDon;
import com.example.appcuahang.model.Phone;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ChiTietHoaDonFragment extends Fragment {
    TextView tvMaHoaDon, tvThoiGianDat, tvKhachHang, tvSoDienThoai, tvDiaChiNhanHang, tvPhuongThucThanhToan, tvTongTien, tvSoLuongSanPham;
    RecyclerView rycChiTietHoaDon;
    Button btnTrangThai;
    HoaDon hoaDon;
    List<ChiTietHoaDon> list;
    ChiTietHoaDonAdapter adapter;
    Integer soLuong = 0;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chi_tiet_hoa_don, container, false);
        ((Activity) getContext()).setTitle("Chi Tiết Hóa Đơn");
        initView(view);
        initVariable();
        action();
        return view;
    }

    private void initVariable() {
        list = new ArrayList<>();
        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        rycChiTietHoaDon.setLayoutManager(manager);
        adapter = new ChiTietHoaDonAdapter(getContext());
        adapter.setData(list);
        rycChiTietHoaDon.setAdapter(adapter);
    }

    private void action() {
        Bundle bundle = getActivity().getIntent().getExtras();
        if (bundle != null) {
            hoaDon = (HoaDon) bundle.getSerializable("detailBill");
            tvMaHoaDon.setText("" + hoaDon.get_id());
            tvThoiGianDat.setText(hoaDon.getNgayTao());
            tvKhachHang.setText(hoaDon.getMaDiaChiNhanHang().getTenNguoiNhan());
            tvSoDienThoai.setText(hoaDon.getMaDiaChiNhanHang().getSdt());
            tvDiaChiNhanHang.setText(hoaDon.getMaDiaChiNhanHang().getDiaChi());
            tvPhuongThucThanhToan.setText(hoaDon.getPhuongThucThanhToan());

            DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            symbols.setGroupingSeparator('.');
            symbols.setDecimalSeparator(',');
            decimalFormat.setDecimalFormatSymbols(symbols);

            try {
                double tongTienNumber = Double.parseDouble(String.valueOf(hoaDon.getTongTien()));
                String formattedNumber = decimalFormat.format(tongTienNumber);
                tvTongTien.setText(formattedNumber);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
            btnTrangThai.setText(hoaDon.getTrangThaiNhanHang());
            getData(hoaDon.get_id());



        }
    }

    private void getData(String id) {
        ApiService apiService = ApiRetrofit.getApiService();
        Call<List<ChiTietHoaDon>> call = apiService.getChiTietHoaDon(id);
        call.enqueue(new Callback<List<ChiTietHoaDon>>() {
            @Override
            public void onResponse(Call<List<ChiTietHoaDon>> call, Response<List<ChiTietHoaDon>> response) {
                if (response.isSuccessful()) {
                    List<ChiTietHoaDon> data = response.body();
                    list.clear();
                    list.addAll(data);
                    for (ChiTietHoaDon item: list){
                        soLuong += Integer.parseInt(String.valueOf(item.getSoLuong()));
                        Log.d("zzzzz", "action: "+ soLuong);
                    }
                    tvSoLuongSanPham.setText(""+soLuong);
                    adapter.notifyDataSetChanged();
                } else {
                    Toast.makeText(getContext(), "Không có dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<ChiTietHoaDon>> call, Throwable t) {
            }
        });
    }

    private void initView(View view) {
        rycChiTietHoaDon = view.findViewById(R.id.rc_chiTietHoaDon);
        tvMaHoaDon = view.findViewById(R.id.tvMaHoaDon);
        tvThoiGianDat = view.findViewById(R.id.tvThoiGianDat);
        tvKhachHang = view.findViewById(R.id.tvKhachHang);
        tvSoDienThoai = view.findViewById(R.id.tvSoDienThoai);
        tvDiaChiNhanHang = view.findViewById(R.id.tvDiaChiNhanHang);
        tvPhuongThucThanhToan = view.findViewById(R.id.tvPhuongThucThanhToan);
        tvSoLuongSanPham = view.findViewById(R.id.tvSoLuongSanPham);
        tvTongTien = view.findViewById(R.id.tvTongTien);
        btnTrangThai = view.findViewById(R.id.btnTrangThai);
    }
}
