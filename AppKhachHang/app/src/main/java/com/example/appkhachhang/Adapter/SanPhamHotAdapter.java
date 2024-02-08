package com.example.appkhachhang.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appkhachhang.DetailScreen;
import com.example.appkhachhang.Interface.OnItemClickListenerSanPhamHot;
import com.example.appkhachhang.Model.SanPham;
import com.example.appkhachhang.Model.SanPhamHot;
import com.example.appkhachhang.R;
import com.squareup.picasso.Picasso;

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
        SanPhamHot sanPhamHot = list.get(position);
        String fullCoverImgUrl = sanPhamHot.getSanPham().getAnh();
        Picasso.get().load(fullCoverImgUrl).into(holder.imgSanPham);
        holder.tvGiaSanPham.setText(sanPhamHot.getSanPham().getGiaTien().toString()+"đ");
        holder.tvTenSanPham.setText(sanPhamHot.getSanPham().getTenDienThoai());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailScreen.class);
                intent.putExtra("tenDienThoai", list.get(position).getSanPham().getTenDienThoai());
                intent.putExtra("soLuong", list.get(position).getSoLuong().toString());
                intent.putExtra("giaTien", list.get(position).getSanPham().getGiaTien().toString());
                intent.putExtra("ram", list.get(position).getSanPham().getMaRam().getRAM());
                intent.putExtra("mau", list.get(position).getSanPham().getMaMau().getTenMau());
                intent.putExtra("anh", list.get(position).getSanPham().getAnh());
                intent.putExtra("dungLuong", list.get(position).getSanPham().getMaDungLuong().getBoNho());
                intent.putExtra("hangSX", list.get(position).getSanPham().getMaHangSX().getTenHang());
                intent.putExtra("uuDai", list.get(position).getSanPham().getMaUuDai().getGiamGia());
                intent.putExtra("moTaThem", "Tên điện thoại: " + list.get(position).getSanPham().getMaChiTiet().getDienThoai()
                        +"\n" + "Kích thước: " + list.get(position).getSanPham().getMaChiTiet().getKichThuoc() +"\n"
                        +"Màn hình: " + list.get(position).getSanPham().getMaChiTiet().getManHinh() + "\n"
                        +"Camera: " + list.get(position).getSanPham().getMaChiTiet().getCamera() + "\n"
                        +"Pin: " + list.get(position).getSanPham().getMaChiTiet().getPin() + "\n"
                        +"Hệ điều hành: " + list.get(position).getSanPham().getMaChiTiet().getHeDieuHanh() + "\n"
                        +"CPU: " + list.get(position).getSanPham().getMaChiTiet().getCpu()+"\n"
                        +"Độ phân giải: " + list.get(position).getSanPham().getMaChiTiet().getDoPhanGiai()+"\n"
                        +"Năm sản xuất: " + list.get(position).getSanPham().getMaChiTiet().getNamSanXuat()+"\n"
                        +"Ngoài ra, còn có những ưu điểm vượt trội như: " + list.get(position).getSanPham().getMaChiTiet().getMoTaThem());
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
