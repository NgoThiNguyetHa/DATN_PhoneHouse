package com.example.appkhachhang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.appkhachhang.Model.AddressDelivery;
import com.example.appkhachhang.R;

import java.util.List;

public class AdapterSpinnerDiaChi extends ArrayAdapter<AddressDelivery> {
    TextView tvHoTen, tvDiaChi, tvSdt;
    public AdapterSpinnerDiaChi(@NonNull Context context, int resource, List<AddressDelivery> list) {
        super(context, resource, list);
    }

        @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_selected_diachi, parent, false);
        tvHoTen = convertView.findViewById(R.id.tvHoTenKhachHangSpn);
        tvDiaChi = convertView.findViewById(R.id.tvDiaChiNhanHangSpn);
        tvSdt = convertView.findViewById(R.id.tvSoDienThoaiSpn);
        AddressDelivery addressDelivery = this.getItem(position);
        if (addressDelivery!=null){
            tvHoTen.setText("Họ tên: "+addressDelivery.getTenNguoiNhan());
            tvDiaChi.setText("Địa chỉ: "+addressDelivery.getDiaChi());
            tvSdt.setText("Sđt: "+addressDelivery.getSdt());
        }
        return convertView;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_diachinhanhang, parent, false);
        tvHoTen = convertView.findViewById(R.id.tvHoTenKhachHang);
        tvDiaChi = convertView.findViewById(R.id.tvDiaChiNhanHang);
        tvSdt = convertView.findViewById(R.id.tvSoDienThoai);
        AddressDelivery addressDelivery = this.getItem(position);
        if (addressDelivery!=null){
            tvHoTen.setText("Họ tên: "+addressDelivery.getTenNguoiNhan());
            tvDiaChi.setText("Địa chỉ: "+addressDelivery.getDiaChi());
            tvSdt.setText("Sđt: "+addressDelivery.getSdt());
        }
        return convertView;
    }
}
