package com.example.appkhachhang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appkhachhang.Model.DanhGia;
import com.example.appkhachhang.Model.TopDienThoai;
import com.example.appkhachhang.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class TopDienThoaiAdapter extends RecyclerView.Adapter<TopDienThoaiAdapter.MyViewHolder>{
    Context mContext;
    List<TopDienThoai> list;
    List<DanhGia> danhGiaList = new ArrayList<>();


    public TopDienThoaiAdapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<TopDienThoai> list){
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_top10 , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        TopDienThoai item = list.get(position);
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        String formattedNumber = decimalFormat.format(item.getChiTietDienThoai().getGiaTien());
        holder.tvTenDienThoai.setText(""+item.getChiTietDienThoai().getMaDienThoai().getTenDienThoai());
        holder.tvGiaTien.setText(""+formattedNumber + " đ");
        if (item.getChiTietDienThoai().getHinhAnh() == null){
            holder.imgDienThoai.setImageResource(R.drawable.img_3);
        }else {
            Picasso.get().load(item.getChiTietDienThoai().getHinhAnh()).into(holder.imgDienThoai);
        }

//rating danh gia
//        int tongDiemDanhGia = 0;
//        danhGiaList = item.getDanhGias();
//        for (int i = 0; i < item.getDanhGias().size(); i++) {
//            tongDiemDanhGia += item.getDanhGias().get(i).getDiemDanhGia();
//        }
//        int soLuongDanhGia = item.getDanhGias().size();
//        float diemTrungBinh = 0;
//        if (soLuongDanhGia != 0) {
//            diemTrungBinh = (float) tongDiemDanhGia / soLuongDanhGia;
//        }
//        String toastMessage = "Tổng điểm đánh giá: " + tongDiemDanhGia + "\nĐiểm trung bình: " + diemTrungBinh;
//
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(mContext, toastMessage, Toast.LENGTH_SHORT).show();
//
//            }
//        });
        if (item.getSoLuongBan() > 0) {
            holder.tvSoLuongBan.setText("Đã bán " + item.getSoLuongBan());
        }else{
            holder.tvSoLuongBan.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static final class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvTenDienThoai;
        TextView tvGiaTien;
        TextView tvSoLuongBan , tvDanhGia ;
        ImageView imgDienThoai;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenDienThoai = itemView.findViewById(R.id.itemTop10_tvTen);
            tvGiaTien = itemView.findViewById(R.id.itemTop10_tvGiaTien);
            tvSoLuongBan = itemView.findViewById(R.id.itemTop10_tvSoLuongBan);
            tvDanhGia = itemView.findViewById(R.id.itemTop10_danhGia);
            imgDienThoai = itemView.findViewById(R.id.itemTop10_imgDienThoai);
        }
    }
}
