package com.example.appkhachhang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appkhachhang.Model.HangSanXuat;
import com.example.appkhachhang.Model.SanPham;
import com.example.appkhachhang.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class HangSanXuatAdapter extends RecyclerView.Adapter<HangSanXuatAdapter.ViewHolder> {
    private Context context;
    private List<HangSanXuat> list;

    public HangSanXuatAdapter(Context context, List<HangSanXuat> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public HangSanXuatAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_hang_san_xuat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull HangSanXuatAdapter.ViewHolder holder, int position) {
        HangSanXuat hangSanXuat = list.get(position);
        String fullCoverImgUrl = hangSanXuat.getAnh();
        Picasso.get().load(fullCoverImgUrl).into(holder.imgHang);
        holder.tvTenHang.setText(hangSanXuat.getTenHang());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgHang;
        private TextView tvTenHang;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgHang = itemView.findViewById(R.id.img_Hang);
            tvTenHang = itemView.findViewById(R.id.tv_tenHang);
        }
    }
}
