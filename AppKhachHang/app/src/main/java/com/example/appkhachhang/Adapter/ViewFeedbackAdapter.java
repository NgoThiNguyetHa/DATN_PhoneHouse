package com.example.appkhachhang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appkhachhang.Interface.OnItemClickListenerDanhGia;
import com.example.appkhachhang.Model.DanhGia;
import com.example.appkhachhang.R;
import com.squareup.picasso.Picasso;


import java.util.List;

public class ViewFeedbackAdapter extends RecyclerView.Adapter<ViewFeedbackAdapter.MyViewHolder> {
    Context mContext;
    List<DanhGia> list;
    private final OnItemClickListenerDanhGia onItemClickListenerDanhGia;

    public ViewFeedbackAdapter(Context mContext, List<DanhGia> list, OnItemClickListenerDanhGia onItemClickListenerDanhGia) {
        this.mContext = mContext;
        this.list = list;
        this.onItemClickListenerDanhGia = onItemClickListenerDanhGia;
    }

    @NonNull
    @Override
    public ViewFeedbackAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_view_feedback, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewFeedbackAdapter.MyViewHolder holder, int position) {
        DanhGia danhGia = list.get(position);
        String tenKhachHang  = danhGia.getIdKhachHang().getUsername();
        String noiDung = danhGia.getNoiDung();
        int diemDanhgia = danhGia.getDiemDanhGia();
        String ngayTao = danhGia.getNgayTao();
        String tenDienThoai = danhGia.getIdChiTietDienThoai().getMaDienThoai().getTenDienThoai();
        String anhDienThoai = danhGia.getIdChiTietDienThoai().getMaDienThoai().getHinhAnh();
        String anhDanhGia = danhGia.getHinhAnh();

        holder.tvViewTenKhachHang.setText(tenKhachHang);
        holder.tvViewNoiDungDG.setText(noiDung);
        holder.tvViewTenDT.setText(tenDienThoai);
        holder.tvViewNgayDanhGia.setText(ngayTao);
        Picasso.get().load(anhDienThoai).into(holder.imgViewAnhDT);
        Picasso.get().load(anhDanhGia).into(holder.img_viewAnhDG);

        if(diemDanhgia==1){
            holder.img_sao_xam_1.setImageResource(R.drawable.rating_sao_vang);
            holder.img_sao_xam_2.setImageResource(R.drawable.rating_star_xam);
            holder.img_sao_xam_3.setImageResource(R.drawable.rating_star_xam);
            holder.img_sao_xam_4.setImageResource(R.drawable.rating_star_xam);
            holder.img_sao_xam_5.setImageResource(R.drawable.rating_star_xam);
        }
        else if(diemDanhgia==2){
            holder.img_sao_xam_1.setImageResource(R.drawable.rating_sao_vang);
            holder.img_sao_xam_2.setImageResource(R.drawable.rating_sao_vang);
            holder.img_sao_xam_3.setImageResource(R.drawable.rating_star_xam);
            holder.img_sao_xam_4.setImageResource(R.drawable.rating_star_xam);
            holder.img_sao_xam_5.setImageResource(R.drawable.rating_star_xam);
        }
        else if(diemDanhgia==3){
            holder.img_sao_xam_1.setImageResource(R.drawable.rating_sao_vang);
            holder.img_sao_xam_2.setImageResource(R.drawable.rating_sao_vang);
            holder.img_sao_xam_3.setImageResource(R.drawable.rating_sao_vang);
            holder.img_sao_xam_4.setImageResource(R.drawable.rating_star_xam);
            holder.img_sao_xam_5.setImageResource(R.drawable.rating_star_xam);
        }
        else if(diemDanhgia==4){
            holder.img_sao_xam_1.setImageResource(R.drawable.rating_sao_vang);
            holder.img_sao_xam_2.setImageResource(R.drawable.rating_sao_vang);
            holder.img_sao_xam_3.setImageResource(R.drawable.rating_sao_vang);
            holder.img_sao_xam_4.setImageResource(R.drawable.rating_sao_vang);
            holder.img_sao_xam_5.setImageResource(R.drawable.rating_star_xam);
        }
        else if(diemDanhgia==5){
            holder.img_sao_xam_1.setImageResource(R.drawable.rating_sao_vang);
            holder.img_sao_xam_2.setImageResource(R.drawable.rating_sao_vang);
            holder.img_sao_xam_3.setImageResource(R.drawable.rating_sao_vang);
            holder.img_sao_xam_4.setImageResource(R.drawable.rating_sao_vang);
            holder.img_sao_xam_5.setImageResource(R.drawable.rating_sao_vang);
        }
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tvViewTenKhachHang, tvViewNgayDanhGia, tvViewNoiDungDG, tvViewTenDT;
        ImageView img_sao_xam_1, img_sao_xam_2, img_sao_xam_3, img_sao_xam_4, img_sao_xam_5, img_viewAnhDG, imgViewAnhDT;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvViewTenKhachHang = itemView.findViewById(R.id.tvViewTenKhachHang);
            tvViewNgayDanhGia = itemView.findViewById(R.id.tvViewNgayDanhGia);
            tvViewTenDT = itemView.findViewById(R.id.tvViewTenDT);
            tvViewNoiDungDG = itemView.findViewById(R.id.tvViewNoiDungDG);
            img_sao_xam_1 = itemView.findViewById(R.id.img_sao_xam_1);
            img_sao_xam_2 = itemView.findViewById(R.id.img_sao_xam_2);
            img_sao_xam_3 = itemView.findViewById(R.id.img_sao_xam_3);
            img_sao_xam_4 = itemView.findViewById(R.id.img_sao_xam_4);
            img_sao_xam_5 = itemView.findViewById(R.id.img_sao_xam_5);
            img_viewAnhDG = itemView.findViewById(R.id.img_viewAnhDG);
            imgViewAnhDT = itemView.findViewById(R.id.imgViewAnhDT);
        }
    }
}
