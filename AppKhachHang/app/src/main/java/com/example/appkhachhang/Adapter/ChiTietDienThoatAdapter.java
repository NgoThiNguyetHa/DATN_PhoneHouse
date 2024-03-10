package com.example.appkhachhang.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
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
                intent.putExtra("soLuong", chiTietDienThoai.getSoLuong());
                intent.putExtra("giaTien", chiTietDienThoai.getGiaTien());
                intent.putExtra("tenDienThoai", chiTietDienThoai.getMaDienThoai().getTenDienThoai());
                intent.putExtra("kichThuoc", chiTietDienThoai.getMaDienThoai().getKichThuoc());
                intent.putExtra("congNgheManHinh", chiTietDienThoai.getMaDienThoai().getCongNgheManHinh());
                intent.putExtra("camera", chiTietDienThoai.getMaDienThoai().getCamera());
                intent.putExtra("cpu", chiTietDienThoai.getMaDienThoai().getCpu());
                intent.putExtra("pin", chiTietDienThoai.getMaDienThoai().getPin());
                intent.putExtra("heDieuHanh", chiTietDienThoai.getMaDienThoai().getHeDieuHanh());
                intent.putExtra("doPhanGiai", chiTietDienThoai.getMaDienThoai().getDoPhanGiai());
                intent.putExtra("namSanXuat", chiTietDienThoai.getMaDienThoai().getNamSanXuat());
                intent.putExtra("thoiGianBaoHanh", chiTietDienThoai.getMaDienThoai().getThoiGianBaoHanh());
                intent.putExtra("moTaThem", chiTietDienThoai.getMaDienThoai().getMoTaThem());
                intent.putExtra("hinhAnh", chiTietDienThoai.getMaDienThoai().getHinhAnh());
                intent.putExtra("hangSanXuat", chiTietDienThoai.getMaDienThoai().getMaHangSX().getTenHang());
                intent.putExtra("uuDai", chiTietDienThoai.getMaDienThoai().getMaUuDai().getGiamGia());
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
