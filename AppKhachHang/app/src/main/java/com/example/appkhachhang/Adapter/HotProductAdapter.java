package com.example.appkhachhang.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appkhachhang.DetailScreen;
import com.example.appkhachhang.Interface.OnItemClickListenerSanPhamHot;
import com.example.appkhachhang.Interface_Adapter.IItemListPhoneListener;
import com.example.appkhachhang.Model.SanPhamHot;
import com.example.appkhachhang.R;
import com.google.gson.Gson;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class HotProductAdapter extends RecyclerView.Adapter<HotProductAdapter.ViewHolder> {
    private Context context;
    private List<SanPhamHot> list;
    private final OnItemClickListenerSanPhamHot onItemClickListenerSanPhamHot;


    public HotProductAdapter(Context context, List<SanPhamHot> list, OnItemClickListenerSanPhamHot onItemClickListenerSanPhamHot) {
        this.context = context;
        this.list = list;
        this.onItemClickListenerSanPhamHot = onItemClickListenerSanPhamHot;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public HotProductAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_danh_sach_dien_thoai_hot, parent, false);
        return new ViewHolder(view);
    }
    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull HotProductAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        SanPhamHot sanPhamHot = list.get(position);
        String fullCoverImgUrl = sanPhamHot.get_id().getHinhAnh();
        Picasso.get().load(fullCoverImgUrl).into(holder.imgSanPham);
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
        holder.tvRam.setText(sanPhamHot.get_id().getMaRam().getRAM()+"GB");
        holder.tvMau.setText(sanPhamHot.get_id().getMaMau().getTenMau());
        holder.tvDungLuong.setText(sanPhamHot.get_id().getMaDungLuong().getBoNho()+"GB");
        if (sanPhamHot.get_id().getMaDienThoai().getMaUuDai()==null){
            holder.tvSale.setText("");
            holder.tvSale.setBackgroundColor(Color.WHITE);
        } else {
            holder.tvSale.setText("Sale " + sanPhamHot.get_id().getMaDienThoai().getMaUuDai().getGiamGia() + "%");
        }
        if (sanPhamHot.get_id().getMaDienThoai().getMaUuDai() == null){
            holder.tvGisSPGoc.setText("");
            holder.tvGisSPGoc.setVisibility(View.GONE);
        }else {
            holder.tvGisSPGoc.setText("" + sanPhamHot.get_id().getGiaTien());
        }
        //
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");
        String tongTien = String.valueOf(sanPhamHot.get_id().getGiaTien());
        try {
            double tongTienNumber = Double.parseDouble(tongTien);
            String formattedNumber = decimalFormat.format(tongTienNumber);
            holder.tvGisSPGoc.setPaintFlags(holder.tvGisSPGoc.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tvGisSPGoc.setText(formattedNumber+"₫");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        //
        String tongTienGiam;
        if (sanPhamHot.get_id().getMaDienThoai().getMaUuDai() == null){
            tongTienGiam = String.valueOf(sanPhamHot.get_id().getGiaTien());
        }else{
            tongTienGiam = String.valueOf(sanPhamHot.get_id().getGiaTien() * Double.parseDouble(sanPhamHot.get_id().getMaDienThoai().getMaUuDai().getGiamGia()) / 100);
        }
        DecimalFormat decimalFormat1 = new DecimalFormat("#,##0");
        try {
            double tongTienGiamNumber = Double.parseDouble(tongTienGiam);
            String formattedNumber = decimalFormat1.format(tongTienGiamNumber);
            holder.tvGiaSanPham.setText(formattedNumber+"₫");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgSanPham, imgCart;
        private TextView tvTenSanPham, tvMau, tvRam, tvDungLuong, tvSale;
        private TextView tvGiaSanPham, tvGisSPGoc;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSanPham = itemView.findViewById(R.id.itemListPhoneHot_imgDienThoai);
            tvTenSanPham = itemView.findViewById(R.id.itemListPhoneHot_tvTenDienThoai);
            tvGiaSanPham = itemView.findViewById(R.id.itemListPhoneHot_tvGiaTienGiam);
            tvGisSPGoc = itemView.findViewById(R.id.itemListPhoneHot_tvGiaTienGoc);
//            imgCart = itemView.findViewById(R.id.itemListPhoneHot_imgCart);
            tvMau = itemView.findViewById(R.id.itemListPhoneHot_tvMau);
            tvRam = itemView.findViewById(R.id.itemListPhoneHot_tvRam);
            tvDungLuong = itemView.findViewById(R.id.itemListPhoneHot_tvDungLuong);
            tvSale = itemView.findViewById(R.id.itemListPhoneHot_tvSale);
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
