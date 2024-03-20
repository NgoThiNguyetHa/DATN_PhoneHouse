package com.example.appcuahang.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcuahang.R;
import com.example.appcuahang.model.AddressDelivery;
import com.example.appcuahang.model.HoaDon;
import com.example.appcuahang.model.Client;
import com.example.appcuahang.model.Store;

import java.text.DecimalFormat;
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
        Client khachHang = hoaDon.getMaKhachHang();
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
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        String tongTien = hoaDon.getTongTien(); // Giả sử tổng tiền là một chuỗi số
        try {
            double tongTienNumber = Double.parseDouble(tongTien);
            String formattedNumber = decimalFormat.format(tongTienNumber);
            holder.tvTongTien.setText(""+formattedNumber +" đ");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

        SimpleDateFormat sdfInput = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
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
