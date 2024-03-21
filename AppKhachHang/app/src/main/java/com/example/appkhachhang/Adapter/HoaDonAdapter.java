package com.example.appkhachhang.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.appkhachhang.Model.AddressDelivery;
import com.example.appkhachhang.Model.HoaDon;
import com.example.appkhachhang.Model.Store;
import com.example.appkhachhang.Model.User;
import com.example.appkhachhang.R;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class HoaDonAdapter extends RecyclerView.Adapter<HoaDonAdapter.MyViewHolder> {

    Context mContext;
    List<HoaDon> list;

    public HoaDonAdapter(Context mContext, List<HoaDon> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_billoder,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        HoaDon hoaDon = list.get(position);
        User khachHang = hoaDon.getMaKhachHang();
        AddressDelivery diaChiNhanHang = hoaDon.getMaDiaChiNhanHang();
        Store store = hoaDon.getMaCuaHang();
        if (diaChiNhanHang == null){
            holder.tvKhachHang.setText(""+khachHang.getUsername());
            holder.tvDCNhanHang.setText(""+store.getDiaChi());
        }else{
            holder.tvKhachHang.setText(""+diaChiNhanHang.getTenNguoiNhan());
            holder.tvDCNhanHang.setText(""+diaChiNhanHang.getDiaChi());
        }
        if (hoaDon.getTrangThaiNhanHang().equals("Đang xử lý")){
            holder.tvTrangThai.setTextColor(Color.GREEN);
        }else if (hoaDon.getTrangThaiNhanHang().equals("Đã giao")){
            holder.tvTrangThai.setTextColor(Color.BLUE);
        }else{
            holder.tvTrangThai.setTextColor(Color.RED);
        }
        holder.tvTrangThai.setText(""+hoaDon.getTrangThaiNhanHang());

        holder.tvPhuongThuc.setText("Thanh toán: "+hoaDon.getPhuongThucThanhToan());
        holder.tvNgayDat.setText("Ngày đặt: "+hoaDon.getNgayTao());
        holder.tvTongTien.setText(""+hoaDon.getTongTien() +" đ");

        SimpleDateFormat sdfInput = new SimpleDateFormat("dd-MM-yyyy", Locale.getDefault());
        SimpleDateFormat sdfOutput = new SimpleDateFormat("dd\n'thg' MM", Locale.getDefault());
        try {
            Date ngayTaoDate = sdfInput.parse(hoaDon.getNgayTao());
            String ngayTaoFormatted = sdfOutput.format(ngayTaoDate);
            holder.tvCardViewNgay.setText(""+ngayTaoFormatted);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static final class MyViewHolder extends RecyclerView.ViewHolder{
    TextView tvKhachHang, tvDCNhanHang , tvTrangThai , tvPhuongThuc , tvNgayDat , tvTongTien , tvCardViewNgay;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvKhachHang = itemView.findViewById(R.id.bill_item_tvKhachHang);
            tvDCNhanHang = itemView.findViewById(R.id.bill_item_tvDiaChi);
            tvTrangThai = itemView.findViewById(R.id.bill_item_tvTrangThai);
            tvPhuongThuc = itemView.findViewById(R.id.bill_item_tvPhuongThuc);
            tvNgayDat = itemView.findViewById(R.id.bill_item_tvNgayDat);
            tvTongTien = itemView.findViewById(R.id.bill_item_tvTongTien);
            tvCardViewNgay = itemView.findViewById(R.id.bill_item_tvCardViewNgay);
        }
    }
}
