package com.example.appkhachhang.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appkhachhang.Api.SanPham_API;
import com.example.appkhachhang.DetailScreen;
import com.example.appkhachhang.Interface.OnItemClickListenerSanPhamHot;
import com.example.appkhachhang.Model.SanPham;
import com.example.appkhachhang.Model.SanPhamHot;
import com.example.appkhachhang.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        SanPhamHot sanPhamHot = list.get(position);
        String fullCoverImgUrl = sanPhamHot.get_id().getMaDienThoai().getHinhAnh();
        Picasso.get().load(fullCoverImgUrl).into(holder.imgSanPham);
        holder.tvGiaSanPham.setText(sanPhamHot.get_id().getGiaTien().toString()+"đ");
        holder.tvTenSanPham.setText(sanPhamHot.get_id().getMaDienThoai().getTenDienThoai());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailScreen.class);
                intent.putExtra("soLuongBanRa", sanPhamHot.getSoLuong().toString());
                intent.putExtra("soLuong", sanPhamHot.get_id().getSoLuong().toString());
                intent.putExtra("giaTien", sanPhamHot.get_id().getGiaTien().toString());
                intent.putExtra("tenDienThoai", sanPhamHot.get_id().getMaDienThoai().getTenDienThoai());
                intent.putExtra("kichThuoc", sanPhamHot.get_id().getMaDienThoai().getKichThuoc());
                intent.putExtra("congNgheManHinh", sanPhamHot.get_id().getMaDienThoai().getCongNgheManHinh());
                intent.putExtra("camera", sanPhamHot.get_id().getMaDienThoai().getCamera());
                intent.putExtra("cpu", sanPhamHot.get_id().getMaDienThoai().getCpu());
                intent.putExtra("pin", sanPhamHot.get_id().getMaDienThoai().getPin());
                intent.putExtra("heDieuHanh", sanPhamHot.get_id().getMaDienThoai().getHeDieuHanh());
                intent.putExtra("doPhanGiai", sanPhamHot.get_id().getMaDienThoai().getDoPhanGiai());
                intent.putExtra("namSanXuat", sanPhamHot.get_id().getMaDienThoai().getNamSanXuat());
                intent.putExtra("thoiGianBaoHanh", sanPhamHot.get_id().getMaDienThoai().getThoiGianBaoHanh());
                intent.putExtra("moTaThem", sanPhamHot.get_id().getMaDienThoai().getMoTaThem());
                intent.putExtra("hinhAnh", sanPhamHot.get_id().getMaDienThoai().getHinhAnh());
                intent.putExtra("hangSanXuat", sanPhamHot.get_id().getMaDienThoai().getMaHangSX().getTenHang());
                intent.putExtra("uuDai", sanPhamHot.get_id().getMaDienThoai().getMaUuDai().getGiamGia());
                intent.putExtra("mau", sanPhamHot.get_id().getMaMau().getTenMau());
                intent.putExtra("ram", sanPhamHot.get_id().getMaRam().getRAM());
                intent.putExtra("dungLuong", sanPhamHot.get_id().getMaDungLuong().getBoNho());
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
