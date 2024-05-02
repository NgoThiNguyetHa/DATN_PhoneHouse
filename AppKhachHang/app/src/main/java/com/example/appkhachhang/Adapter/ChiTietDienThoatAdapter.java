package com.example.appkhachhang.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appkhachhang.Interface_Adapter.IItemListPhoneListener;
import com.example.appkhachhang.Model.DanhGia;
import com.example.appkhachhang.Model.Root;
import com.example.appkhachhang.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class ChiTietDienThoatAdapter extends RecyclerView.Adapter<ChiTietDienThoatAdapter.ViewHolder> {
    private Context context;
    private List<Root> list;
    List<DanhGia> danhGiaList;

    private final IItemListPhoneListener onItemClickListenerSanPham;

    public ChiTietDienThoatAdapter(Context context, List<Root> list, IItemListPhoneListener onItemClickListenerSanPham) {
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
        Root chiTietDienThoai = list.get(position);
        String fullCoverImgUrl = chiTietDienThoai.getChiTietDienThoai().getHinhAnh();
        Picasso.get().load(fullCoverImgUrl).into(holder.imgSanPham);
        holder.tvTenSanPham.setText(chiTietDienThoai.getChiTietDienThoai().getMaDienThoai().getTenDienThoai());
        holder.tvGiaSanPham.setText(chiTietDienThoai.getChiTietDienThoai().getGiaTien() + "đ");
        //gia tien - giam tien giam
        //gia tien - giam tien giam
        if (chiTietDienThoai.getChiTietDienThoai().getMaDienThoai().getMaUuDai() == null){
//            holder.tv_giaTienGoc.setVisibility(View.GONE);
            holder.tv_giamGia.setText("");
            holder.tv_giaTienGoc.setText("");
            holder.lnGiamGia.setVisibility(View.GONE);
        }else {
            holder.tv_giamGia.setText(""+chiTietDienThoai.getChiTietDienThoai().getMaDienThoai().getMaUuDai().getGiamGia() + "%");
            holder.tv_giaTienGoc.setText("" + chiTietDienThoai.getChiTietDienThoai().getGiaTien());
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");
            String tongTien = String.valueOf(chiTietDienThoai.getChiTietDienThoai().getGiaTien());
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
        if (chiTietDienThoai.getChiTietDienThoai().getMaDienThoai().getMaUuDai() == null){
            tongTienGiam = String.valueOf(chiTietDienThoai.getChiTietDienThoai().getGiaTien());

        }else{
            tongTienGiam = String.valueOf(chiTietDienThoai.getChiTietDienThoai().getGiaTien() - (chiTietDienThoai.getChiTietDienThoai().getGiaTien() * (Double.parseDouble(chiTietDienThoai.getChiTietDienThoai().getMaDienThoai().getMaUuDai().getGiamGia()) / 100)));
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
//                Intent intent = new Intent(context, DetailScreen.class);
//                SharedPreferences sharedPreferences = context.getSharedPreferences("chiTiet", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//                Gson gson = new Gson();
//                String json = gson.toJson(chiTietDienThoai);
//                editor.putString("chiTietDienThoai", json);
//                editor.apply();
//                context.startActivity(intent);
                onItemClickListenerSanPham.onClickDetail(chiTietDienThoai);
            }
        });
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListenerSanPham.onClickDetail(chiTietDienThoai);
            }
        });
        int tongDiemDanhGia = 0;
        danhGiaList = chiTietDienThoai.getDanhGias();
        for (int i = 0; i < chiTietDienThoai.getDanhGias().size(); i++) {
            tongDiemDanhGia += chiTietDienThoai.getDanhGias().get(i).getDiemDanhGia();
        }
        int soLuongDanhGia = chiTietDienThoai.getDanhGias().size();
        float diemTrungBinh = 0;
        if (soLuongDanhGia != 0) {
            diemTrungBinh = (float) tongDiemDanhGia / soLuongDanhGia;
        }
        holder.rbRating.setRating(diemTrungBinh);
        Drawable drawable = holder.rbRating.getProgressDrawable();
        drawable.setColorFilter(Color.parseColor("#ffbd00"), PorterDuff.Mode.SRC_ATOP);
        holder.tv_diaChi.setVisibility(View.VISIBLE);
        holder.tv_diaChi.setText(""+chiTietDienThoai.getChiTietDienThoai().getMaDienThoai().getMaCuaHang().getDiaChi());
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private ImageView imgSanPham, itemSPHot_imageBrand ,itemSPHot_imageCart;
        private TextView tvTenSanPham , tv_danhGia , tv_giaTienGoc , tv_giamGia;
        private TextView tvGiaSanPham , tv_diaChi;
        RatingBar rbRating;
        LinearLayout lnGiamGia;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgSanPham = itemView.findViewById(R.id.img_SanPham);
            tvTenSanPham = itemView.findViewById(R.id.tv_tenSanPham);
            tvGiaSanPham = itemView.findViewById(R.id.tv_giaSanPham);
            tv_giaTienGoc = itemView.findViewById(R.id.tv_giaTienGoc);
            tv_giamGia = itemView.findViewById(R.id.tv_giamGia);
            rbRating = itemView.findViewById(R.id.item_rbDiemDanhGia);
            tv_diaChi = itemView.findViewById(R.id.tv_Custom);
            lnGiamGia = itemView.findViewById(R.id.bg_giamGia);
//
        }
    }
}
