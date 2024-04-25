package com.example.appkhachhang.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appkhachhang.Interface.OnItemClickListenerSanPhamHot;
import com.example.appkhachhang.Model.DanhGia;
import com.example.appkhachhang.Model.SanPhamHot;
import com.example.appkhachhang.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

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
        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.slide_in_left);
        holder.itemView.startAnimation(animation);
        SanPhamHot sanPhamHot = list.get(position);
        List<DanhGia> iconList = sanPhamHot.getDanhGia();
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, ""+iconList.size(), Toast.LENGTH_SHORT).show();
//            }
//        });
        String fullCoverImgUrl = sanPhamHot.get_id().getHinhAnh();
        holder.tvGiaSanPham.setText(""+sanPhamHot.get_id().getGiaTien()+"đ");
        holder.tvTenSanPham.setText(sanPhamHot.get_id().getMaDienThoai().getTenDienThoai());
        if (sanPhamHot.get_id().getHinhAnh() != null){
            Picasso.get().load(fullCoverImgUrl).into(holder.imgSanPham);
        }else{
            holder.imgSanPham.setVisibility(View.GONE);
        }
        //gia tien - giam tien giam
        if (sanPhamHot.get_id().getMaDienThoai().getMaUuDai() == null){
//            holder.tv_giaTienGoc.setVisibility(View.GONE);
            holder.tv_giaTienGoc.setText("");
            holder.tv_giamGia.setText("");
        }else {
            holder.tv_giamGia.setText("Giảm "+sanPhamHot.get_id().getMaDienThoai().getMaUuDai().getGiamGia() + "%");
            holder.tv_giaTienGoc.setText("" + sanPhamHot.get_id().getGiaTien());
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");
            String tongTien = String.valueOf(sanPhamHot.get_id().getGiaTien());
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
        if (sanPhamHot.get_id().getMaDienThoai().getMaUuDai() == null){
            tongTienGiam = String.valueOf(sanPhamHot.get_id().getGiaTien());
        }else{
            tongTienGiam = String.valueOf(sanPhamHot.get_id().getGiaTien() - (sanPhamHot.get_id().getGiaTien() * (Double.parseDouble(sanPhamHot.get_id().getMaDienThoai().getMaUuDai().getGiamGia()) / 100)));
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
                onItemClickListenerSanPhamHot.onItemClickSPHot(sanPhamHot.get_id());
            }
        });
        holder.tvSoLuongDaBan.setVisibility(View.VISIBLE);
        holder.tvSoLuongDaBan.setText("Đã bán "+sanPhamHot.getSoLuong());
//        List<DanhGia> danhGiaList = sanPhamHot.getDanhGia();
//        if (danhGiaList != null) {
//            for (int i = 0; i < danhGiaList.size(); i++) {
//                DanhGia danhGia = danhGiaList.get(i);
//                // Xử lý thông tin đánh giá ở đây
//                Log.e("list danh giá ", String.valueOf(danhGia.getDiemDanhGia()));
//                holder.tv_danhGia.setText(""+danhGia.getDiemDanhGia());
//            }
//        } else {
//            Log.e("list danh giá ", "Danh sách đánh giá trống");
//            holder.tv_danhGia.setText(""+5);
//        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgSanPham , itemSPHot_imageBrand ,itemSPHot_imageCart;
        private TextView tvTenSanPham , tv_danhGia , tv_giaTienGoc , tv_giamGia ;
        private TextView tvGiaSanPham;
        private TextView tvSoLuongDaBan;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSanPham = itemView.findViewById(R.id.img_SanPham);
            tvTenSanPham = itemView.findViewById(R.id.tv_tenSanPham);
            tvGiaSanPham = itemView.findViewById(R.id.tv_giaSanPham);
            tv_danhGia = itemView.findViewById(R.id.tv_danhGia);
            tv_giaTienGoc = itemView.findViewById(R.id.tv_giaTienGoc);
            tv_giamGia = itemView.findViewById(R.id.tv_giamGia);
            tvSoLuongDaBan = itemView.findViewById(R.id.tv_Custom);
//            itemSPHot_imageCart = itemView.findViewById(R.id.itemSPHot_imageCart);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View view) {
//                    if (onItemClickListenerSanPhamHot!=null){
//                        int pos = getAdapterPosition();
//                        if (pos!=RecyclerView.NO_POSITION){
//                            onItemClickListenerSanPhamHot.onItemClickSPHot(pos);
//                        }
//                    }
//                }
//            });
        }
    }
}
