package com.example.appkhachhang.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appkhachhang.DetailScreen;
import com.example.appkhachhang.Interface.OnItemClickListenerSanPham;
import com.example.appkhachhang.Model.SanPham;
import com.example.appkhachhang.R;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SanPhamAdapter extends RecyclerView.Adapter<SanPhamAdapter.ViewHolder> {
    private Context context;
    private List<SanPham> list;

    private final OnItemClickListenerSanPham onItemClickListenerSanPham;

    public SanPhamAdapter(Context context, List<SanPham> list, OnItemClickListenerSanPham onItemClickListenerSanPham) {
        this.context = context;
        this.list = list;
        this.onItemClickListenerSanPham = onItemClickListenerSanPham;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SanPhamAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_san_pham, parent, false);
        return new ViewHolder(view);
    }

    @SuppressLint("RecyclerView")
    @Override
    public void onBindViewHolder(@NonNull SanPhamAdapter.ViewHolder holder, int position) {
        SanPham sanPham = list.get(position);
        String fullCoverImgUrl = sanPham.getAnh();
        Picasso.get().load(fullCoverImgUrl).into(holder.imgSanPham);
        holder.tvGiaSanPham.setText(sanPham.getGiaTien().toString()+"đ");
        holder.tvTenSanPham.setText(sanPham.getTenDienThoai());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, DetailScreen.class);
                intent.putExtra("tenDienThoai", list.get(position).getTenDienThoai());
                intent.putExtra("soLuong", list.get(position).getSoLuong().toString());
                intent.putExtra("giaTien", list.get(position).getGiaTien().toString());
                intent.putExtra("ram", list.get(position).getMaRam().getRAM());
                intent.putExtra("mau", list.get(position).getMaMau().getTenMau());
                intent.putExtra("anh", list.get(position).getAnh());
                intent.putExtra("dungLuong", list.get(position).getMaDungLuong().getBoNho());
                intent.putExtra("hangSX", list.get(position).getMaHangSX().getTenHang());
                intent.putExtra("uuDai", list.get(position).getMaUuDai().getGiamGia());
                intent.putExtra("moTaThem", "Tên điện thoại: " + list.get(position).getMaChiTiet().getDienThoai()
                +"\n" + "Kích thước: " + list.get(position).getMaChiTiet().getKichThuoc() +"\n"
                +"Màn hình: " + list.get(position).getMaChiTiet().getManHinh() + "\n"
                +"Camera: " + list.get(position).getMaChiTiet().getCamera() + "\n"
                +"Pin: " + list.get(position).getMaChiTiet().getPin() + "\n"
                +"Hệ điều hành: " + list.get(position).getMaChiTiet().getHeDieuHanh() + "\n"
                +"CPU: " + list.get(position).getMaChiTiet().getCpu()+"\n"
                +"Độ phân giải: " + list.get(position).getMaChiTiet().getDoPhanGiai()+"\n"
                +"Năm sản xuất: " + list.get(position).getMaChiTiet().getNamSanXuat()+"\n"
                +"Ngoài ra, còn có những ưu điểm vượt trội như: " + list.get(position).getMaChiTiet().getMoTaThem());
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
