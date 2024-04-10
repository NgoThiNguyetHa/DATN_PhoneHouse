package com.example.appkhachhang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appkhachhang.Interface.OnItemClickListenerDanhGia;
import com.example.appkhachhang.Model.ChiTietDienThoai;
import com.example.appkhachhang.Model.ChiTietHoaDon;
import com.example.appkhachhang.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HistoryBuyAdapter extends RecyclerView.Adapter<HistoryBuyAdapter.MyViewHolder> {
    Context mContext;
    List<ChiTietHoaDon> list;

    private final OnItemClickListenerDanhGia onItemClickListenerDanhGia;
    public HistoryBuyAdapter(Context mContext, List<ChiTietHoaDon> list, OnItemClickListenerDanhGia onItemClickListenerDanhGia) {
        this.mContext = mContext;
        this.list = list;
        this.onItemClickListenerDanhGia = onItemClickListenerDanhGia;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_history_buy,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull HistoryBuyAdapter.MyViewHolder holder, int position) {
        ChiTietHoaDon chiTietHoaDon = list.get(position);
        ChiTietDienThoai chiTietDienThoai = chiTietHoaDon.getMaChiTietDienThoai();
        String tongTien = chiTietHoaDon.getGiaTien();
        String tenDienThoai = chiTietDienThoai.getMaDienThoai().getTenDienThoai();
        String mauDT = chiTietDienThoai.getMaMau().getTenMau();
        String anh = chiTietDienThoai.getMaDienThoai().getHinhAnh();
        String soLuong = String.valueOf(chiTietHoaDon.getSoLuong());

        holder.bill_item_tvSoLuong.setText("x" + soLuong);
        holder.bill_item_tvDienThoai.setText(tenDienThoai);
        holder.bill_item_tvMau.setText("" +mauDT);
        holder.bill_item_tvTongTien.setText("Tổng tiền: "+ tongTien);
        Picasso.get().load(anh).into(holder.imgDienThoai);

        holder.btnDanhGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListenerDanhGia.onItemClickDanhGia(chiTietHoaDon);

            }
        });
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView bill_item_tvDienThoai, bill_item_tvMau,bill_item_tvSoLuong,bill_item_tvTongTien;
        ImageView imgDienThoai;
        Button btnDanhGia;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            bill_item_tvDienThoai = itemView.findViewById(R.id.bill_item_tvDienThoai);
            bill_item_tvMau = itemView.findViewById(R.id.bill_item_tvMau);
            bill_item_tvSoLuong = itemView.findViewById(R.id.bill_item_tvSoLuong);
            bill_item_tvTongTien = itemView.findViewById(R.id.bill_item_tvTongTien);
            imgDienThoai = itemView.findViewById(R.id.imgDienThoai);
            btnDanhGia = itemView.findViewById(R.id.btnDanhGia);



        }
    }
}
