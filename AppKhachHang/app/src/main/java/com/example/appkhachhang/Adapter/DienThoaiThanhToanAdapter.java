package com.example.appkhachhang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appkhachhang.Model.ChiTietDienThoai;
import com.example.appkhachhang.Model.ChiTietGioHang;
import com.example.appkhachhang.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DienThoaiThanhToanAdapter extends RecyclerView.Adapter<DienThoaiThanhToanAdapter.ViewHolder> {
    private List<ChiTietGioHang> list;
    private Context context;

    public DienThoaiThanhToanAdapter(List<ChiTietGioHang> list, Context context) {
        this.list = list;
        this.context = context;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public DienThoaiThanhToanAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sanphamchon, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DienThoaiThanhToanAdapter.ViewHolder holder, int position) {
        ChiTietGioHang item = list.get(position);
        String fullCoverImgUrl = item.getMaChiTietDienThoai().getMaDienThoai().getHinhAnh();
        if (fullCoverImgUrl != null && holder.imgSanPham != null) {
            Picasso.get().load(fullCoverImgUrl).into(holder.imgSanPham);
        }
        holder.tvTenSP.setText(item.getMaChiTietDienThoai().getMaDienThoai().getTenDienThoai());
        holder.tvGiaSP.setText(item.getMaChiTietDienThoai().getGiaTien().toString());
        holder.tvMauSac.setText(item.getMaChiTietDienThoai().getMaMau().getTenMau());
        holder.tvSoLuong.setText("x" +  item.getSoLuong());
        holder.tvThanhTien.setText("Thành tiền " + "(" + item.getSoLuong() +" sản phẩm): " + Integer.parseInt(item.getSoLuong().toString())*Integer.parseInt(item.getGiaTien().toString()));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgSanPham;
        TextView tvTenSP, tvGiaSP, tvMauSac, tvThanhTien, tvSoLuong;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenSP = itemView.findViewById(R.id.tv_tenSanPhamChon);
            tvGiaSP = itemView.findViewById(R.id.tvGiaSPChon);
            tvMauSac = itemView.findViewById(R.id.tv_mauSac);
            imgSanPham = itemView.findViewById(R.id.img_SanPhamChon);
            tvThanhTien = itemView.findViewById(R.id.tv_ThanhTien);
            tvSoLuong = itemView.findViewById(R.id.tvSoLuongChon);

        }
    }
}
