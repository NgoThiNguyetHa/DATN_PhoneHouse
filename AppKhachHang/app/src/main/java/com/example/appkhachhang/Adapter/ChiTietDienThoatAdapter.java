package com.example.appkhachhang.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appkhachhang.activity.DetailScreen;
import com.example.appkhachhang.Interface.OnItemClickListenerSanPham;
import com.example.appkhachhang.Model.ChiTietDienThoai;
import com.example.appkhachhang.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class ChiTietDienThoatAdapter extends RecyclerView.Adapter<ChiTietDienThoatAdapter.ViewHolder> {
    private Context context;
    private List<ChiTietDienThoai> list;

    private final OnItemClickListenerSanPham onItemClickListenerSanPham;

    public ChiTietDienThoatAdapter(Context context, List<ChiTietDienThoai> list, OnItemClickListenerSanPham onItemClickListenerSanPham) {
        this.context = context;
        this.list = list;
        this.onItemClickListenerSanPham = onItemClickListenerSanPham;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ChiTietDienThoatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_san_pham, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull ChiTietDienThoatAdapter.ViewHolder holder, int position) {
        ChiTietDienThoai chiTietDienThoai = list.get(position);
        String fullCoverImgUrl = chiTietDienThoai.getHinhAnh();
        Picasso.get().load(fullCoverImgUrl).into(holder.imgSanPham);
        holder.tvTenSanPham.setText(chiTietDienThoai.getMaDienThoai().getTenDienThoai());
        holder.tvGiaSanPham.setText(chiTietDienThoai.getGiaTien() + "đ");
        if (chiTietDienThoai.getMaDienThoai().getMaHangSX().getHinhAnh() != null){
            Picasso.get().load(chiTietDienThoai.getMaDienThoai().getMaHangSX().getHinhAnh()).into(holder.itemSPHot_imageBrand);
        }else{
            holder.itemSPHot_imageBrand.setVisibility(View.GONE);
        }
        //gia tien - giam tien giam
        //gia tien - giam tien giam
        if (chiTietDienThoai.getMaDienThoai().getMaUuDai() == null){
            holder.tv_giaTienGoc.setVisibility(View.GONE);
            holder.tv_giamGia.setText("");
        }else {
            holder.tv_giamGia.setText("Giảm "+chiTietDienThoai.getMaDienThoai().getMaUuDai().getGiamGia() + "%");
            holder.tv_giaTienGoc.setText("" + chiTietDienThoai.getGiaTien());
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");
            String tongTien = String.valueOf(chiTietDienThoai.getGiaTien());
            try {
                double tongTienNumber = Double.parseDouble(tongTien);
                String formattedNumber = decimalFormat.format(tongTienNumber);
                holder.tv_giaTienGoc.setPaintFlags(holder.tv_giaTienGoc.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                holder.tv_giaTienGoc.setText(formattedNumber+"₫");
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        String tongTienGiam;
        if (chiTietDienThoai.getMaDienThoai().getMaUuDai() == null){
            tongTienGiam = String.valueOf(chiTietDienThoai.getGiaTien());

        }else{
            tongTienGiam = String.valueOf(chiTietDienThoai.getGiaTien() * (Double.parseDouble(chiTietDienThoai.getMaDienThoai().getMaUuDai().getGiamGia()) / 100));
        }

        DecimalFormat decimalFormat1 = new DecimalFormat("#,##0");
        try {
            double tongTienGiamNumber = Double.parseDouble(tongTienGiam);
            String formattedNumber = decimalFormat1.format(tongTienGiamNumber);
            holder.tvGiaSanPham.setText(formattedNumber+"₫");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailScreen.class);
                SharedPreferences sharedPreferences = context.getSharedPreferences("chiTiet", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(chiTietDienThoai);
                editor.putString("chiTietDienThoai", json);
                editor.apply();
                context.startActivity(intent);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListenerSanPham.onItemClickSP(chiTietDienThoai);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgSanPham, itemSPHot_imageBrand ,itemSPHot_imageCart;
        private TextView tvTenSanPham , tv_danhGia , tv_giaTienGoc , tv_giamGia;
        private TextView tvGiaSanPham;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSanPham = itemView.findViewById(R.id.img_SanPham);
            tvTenSanPham = itemView.findViewById(R.id.tv_tenSanPham);
            tvGiaSanPham = itemView.findViewById(R.id.tv_giaSanPham);
            tv_danhGia = itemView.findViewById(R.id.tv_danhGia);
            tv_giaTienGoc = itemView.findViewById(R.id.tv_giaTienGoc);
            tv_giamGia = itemView.findViewById(R.id.tv_giamGia);
            itemSPHot_imageBrand = itemView.findViewById(R.id.itemSPHot_imageBrand);
            itemSPHot_imageCart = itemView.findViewById(R.id.itemSPHot_imageCart);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                     if (onItemClickListenerSanPham!=null){
//                         int pos = getAdapterPosition();
//                         if (pos!=RecyclerView.NO_POSITION){
//                             onItemClickListenerSanPham.onItemClickSP(pos);
//                         }
//                     }
//                }
//            });
        }
    }
}
