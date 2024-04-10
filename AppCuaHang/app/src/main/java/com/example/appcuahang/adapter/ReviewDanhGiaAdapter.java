package com.example.appcuahang.adapter;

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

import com.example.appcuahang.R;
import com.example.appcuahang.interface_adapter.IItemDanhGiaListenner;
import com.example.appcuahang.interface_adapter.IItemDetailPhoneListenner;
import com.example.appcuahang.interface_adapter.IItemPhoneListenner;
import com.example.appcuahang.model.Rating;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ReviewDanhGiaAdapter extends RecyclerView.Adapter<ReviewDanhGiaAdapter.MyViewHolder>{
    Context mContext;
    List<Rating> list;
    private IItemDanhGiaListenner listener;


    public ReviewDanhGiaAdapter(Context mContext , IItemDanhGiaListenner listener) {
        this.mContext = mContext;
        this.listener = listener;
    }
    public void setData(List<Rating> list){ // thêm mới
        this.list = list;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_quan_ly_danh_gia , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Rating item = list.get(position);
        holder.tvTenUser.setText(""+item.getIdKhachHang().getUsername());
        holder.tvNgayTao.setText("Thời gian đăng: "+item.getNgayTao());
        holder.tvNoiDung.setText(""+item.getNoiDung());
        int width = 5;
        int height = 5;
        LinearLayout.LayoutParams parms = new LinearLayout.LayoutParams(width,height);
        if (item.getHinhAnh().isEmpty()){
            holder.imgPhone.setLayoutParams(parms);
            holder.imgPhone.setImageResource(R.drawable.shape_custom_dialog);
        }else {
            Picasso.get().load(item.getHinhAnh()).into(holder.imgPhone);
        }
        holder.ratingBar.setRating(item.getDiemDanhGia());
        Drawable drawable = holder.ratingBar.getProgressDrawable();
        drawable.setColorFilter(Color.parseColor("#ffbd00"), PorterDuff.Mode.SRC_ATOP);
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.deleteDanhGia(item.get_id());
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public static final class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvTenUser , tvNgayTao , tvNoiDung ;
        ImageView imgUser , imgPhone , imgDelete;
        RatingBar ratingBar;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenUser = itemView.findViewById(R.id.itemQLDanhGia_tvUser);
            tvNgayTao = itemView.findViewById(R.id.itemQLDanhGia_tvNgayTao);
            tvNoiDung = itemView.findViewById(R.id.itemQLDanhGia_tvNoiDung);
            imgUser = itemView.findViewById(R.id.itemQLDanhGia_imgUser);
            imgPhone = itemView.findViewById(R.id.itemQLDanhGia_imageView);
            ratingBar = itemView.findViewById(R.id.itemQLDanhGia_rbDiemDanhGia);
            imgDelete = itemView.findViewById(R.id.itemQLDanhGia_imgDelete);
        }
    }
}
