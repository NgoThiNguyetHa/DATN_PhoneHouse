package com.example.appkhachhang.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appkhachhang.DetailScreen;
import com.example.appkhachhang.Interface.OnItemClickListenerSanPham;
import com.example.appkhachhang.Model.ChiTietDienThoai;
import com.example.appkhachhang.Model.SanPham;
import com.example.appkhachhang.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
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
        String fullCoverImgUrl = chiTietDienThoai.getMaDienThoai().getHinhAnh();
        Picasso.get().load(fullCoverImgUrl).into(holder.imgSanPham);
        holder.tvTenSanPham.setText(chiTietDienThoai.getMaDienThoai().getTenDienThoai());
        holder.tvGiaSanPham.setText(chiTietDienThoai.getGiaTien().toString() + "đ");
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
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgSanPham;
        private TextView tvTenSanPham;
        private TextView tvGiaSanPham;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSanPham = itemView.findViewById(R.id.img_SanPham);
            tvTenSanPham = itemView.findViewById(R.id.tv_tenSanPham);
            tvGiaSanPham = itemView.findViewById(R.id.tv_giaSanPham);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                     if (onItemClickListenerSanPham!=null){
                         int pos = getAdapterPosition();
                         if (pos!=RecyclerView.NO_POSITION){
                             onItemClickListenerSanPham.onItemClickSP(pos);
                         }
                     }
                }
            });
        }
    }
}
