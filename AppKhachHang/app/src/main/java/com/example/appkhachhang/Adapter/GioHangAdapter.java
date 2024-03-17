package com.example.appkhachhang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appkhachhang.Interface.OnItemClickListenerHang;
import com.example.appkhachhang.Model.ChiTietGioHang;
import com.example.appkhachhang.Model.GioHang;
import com.example.appkhachhang.Model.HangSanXuat;
import com.example.appkhachhang.Model.SanPham;
import com.example.appkhachhang.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.ViewHolder> {
    private Context context;
    private List<ChiTietGioHang> list;
    public GioHangAdapter(Context context, List<ChiTietGioHang> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public GioHangAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gio_hang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GioHangAdapter.ViewHolder holder, int position) {
//        ChiTietGioHang item = list.get(position);
//        SanPham sanPham = item.getMaDienThoai();
//        holder.tvTenSanPham.setText("Tên sản phẩm: "+sanPham.getTenDienThoai());
//        holder.tvSoLuong.setText("Số lượng: "+item.getSoLuong());
//        holder.tvGiaTien.setText("Giá tiền: "+sanPham.getGiaTien());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTenSanPham , tvGiaTien , tvSoLuong;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenSanPham = itemView.findViewById(R.id.itemGioHang_tvSanPham);
            tvGiaTien = itemView.findViewById(R.id.itemGioHang_tvGiaTien);
            tvSoLuong = itemView.findViewById(R.id.itemGioHang_tvSoLuong);
        }
    }
}
