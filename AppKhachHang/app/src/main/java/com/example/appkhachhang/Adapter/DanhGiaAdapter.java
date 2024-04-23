package com.example.appkhachhang.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appkhachhang.Model.DanhGia;
import com.example.appkhachhang.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DanhGiaAdapter extends RecyclerView.Adapter<DanhGiaAdapter.MyViewHolder>{
    Context mContext;
    List<DanhGia> list;

    public DanhGiaAdapter(Context mContext) {
        this.mContext = mContext;
    }
    public void setData(List<DanhGia> list){ // thêm mới
        this.list = list;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_danh_gia , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DanhGia item = list.get(position);

        holder.tvTenUser.setText(""+item.getIdKhachHang().getUsername());
        holder.tvNgayTao.setText(""+item.getNgayTao());
        holder.tvNoiDung.setText(""+item.getNoiDung());
        int width = 10;
        int height = 10;
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
        if (item.getHinhAnh().isEmpty()){
            holder.imgPhone.setVisibility(View.GONE);
        }else {
            Picasso.get().load(item.getHinhAnh()).into(holder.imgPhone);
        }
        holder.ratingBar.setRating(item.getDiemDanhGia());
        Drawable drawable = holder.ratingBar.getProgressDrawable();
        drawable.setColorFilter(Color.parseColor("#ffbd00"), PorterDuff.Mode.SRC_ATOP);
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public static final class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvTenUser , tvNgayTao , tvNoiDung ;
        ImageView imgUser , imgPhone;
        LinearLayout mParent;
        RatingBar ratingBar;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenUser = itemView.findViewById(R.id.itemDanhGia_tvTenUser);
            tvNgayTao = itemView.findViewById(R.id.itemDanhGia_tvNgayTao);
            tvNoiDung = itemView.findViewById(R.id.itemDanhGia_tvNoiDung);
            imgUser = itemView.findViewById(R.id.itemDanhGia_imgUser);
            imgPhone = itemView.findViewById(R.id.itemDanhGia_imgPhone);
            ratingBar = itemView.findViewById(R.id.itemDanhGia_rating);
        }
    }
}
