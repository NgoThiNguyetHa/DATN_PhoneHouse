package com.example.appkhachhang.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appkhachhang.Api.SanPham_API;
import com.example.appkhachhang.DetailScreen;
import com.example.appkhachhang.Interface.OnItemClickListenerSanPhamHot;
import com.example.appkhachhang.Model.SanPham;
import com.example.appkhachhang.Model.SanPhamHot;
import com.example.appkhachhang.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SanPhamHotAdapter extends RecyclerView.Adapter<SanPhamHotAdapter.ViewHolder> {
    private Context context;
    private List<SanPhamHot> list;
    private final OnItemClickListenerSanPhamHot onItemClickListenerSanPhamHot;

    public SanPhamHotAdapter(Context context, List<SanPhamHot> list, OnItemClickListenerSanPhamHot onItemClickListenerSanPhamHot) {
        this.context = context;
        this.list = list;
        this.onItemClickListenerSanPhamHot = onItemClickListenerSanPhamHot;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SanPhamHotAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_san_pham, parent, false);
        return new ViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull SanPhamHotAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        SanPhamHot sanPhamHot = list.get(position);
        String fullCoverImgUrl = sanPhamHot.get_id().getMaDienThoai().getHinhAnh();
        Picasso.get().load(fullCoverImgUrl).into(holder.imgSanPham);
        holder.tvGiaSanPham.setText(sanPhamHot.get_id().getGiaTien().toString()+"đ");
        holder.tvTenSanPham.setText(sanPhamHot.get_id().getMaDienThoai().getTenDienThoai());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailScreen.class);
                SharedPreferences sharedPreferences = context.getSharedPreferences("chiTiet", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                Gson gson = new Gson();
                String json = gson.toJson(sanPhamHot.get_id());
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
                    if (onItemClickListenerSanPhamHot!=null){
                        int pos = getAdapterPosition();
                        if (pos!=RecyclerView.NO_POSITION){
                            onItemClickListenerSanPhamHot.onItemClickSPHot(pos);
                        }
                    }
                }
            });
        }
    }
}
