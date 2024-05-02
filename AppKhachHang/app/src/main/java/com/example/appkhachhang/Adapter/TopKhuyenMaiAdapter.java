package com.example.appkhachhang.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
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
import java.util.ArrayList;
import java.util.List;

public class TopKhuyenMaiAdapter extends RecyclerView.Adapter<TopKhuyenMaiAdapter.ViewHolder> {
    Context mContext;
//    List<ListPhone> list;
    List<DanhGia> danhGiaList = new ArrayList<>();
    List<Root> list;
    IItemListPhoneListener listener;
    public TopKhuyenMaiAdapter(Context mContext) {
        this.mContext = mContext;
    }
//    public void setData(List<ListPhone> list) {
//        this.list = list;
//    }
    public void setOnClickListener(IItemListPhoneListener listener){
        this.listener = listener;
    }
    public void setData(List<Root> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public TopKhuyenMaiAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_top_khuyen_mai, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TopKhuyenMaiAdapter.ViewHolder holder, int position) {
        Animation animation = AnimationUtils.loadAnimation(holder.itemView.getContext(), android.R.anim.slide_in_left);
        holder.itemView.startAnimation(animation);

        Root item = list.get(position);
        String tenDienThoai = item.getChiTietDienThoai().getMaDienThoai().getTenDienThoai();
        holder.tvTenDienThoai.setText("Điện thoại " + tenDienThoai);
        if (item.getChiTietDienThoai().getMaDienThoai().getMaUuDai() == null) {
            holder.tvSale.setText("");
            holder.tvSale.setBackgroundColor(Color.WHITE);
        } else {
            holder.tvSale.setText("" + item.getChiTietDienThoai().getMaDienThoai().getMaUuDai().getGiamGia() + "%");
        }
        //rating danh gia
        int tongDiemDanhGia = 0;
        danhGiaList = item.getDanhGias();
        for (int i = 0; i < item.getDanhGias().size(); i++) {
            tongDiemDanhGia += item.getDanhGias().get(i).getDiemDanhGia();
        }
        int soLuongDanhGia = item.getDanhGias().size();
        float diemTrungBinh = 0;
        if (soLuongDanhGia != 0) {
            diemTrungBinh = (float) tongDiemDanhGia / soLuongDanhGia;
        }
        String toastMessage = "Tổng điểm đánh giá: " + tongDiemDanhGia + "\nĐiểm trung bình: " + diemTrungBinh;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Toast.makeText(mContext, toastMessage, Toast.LENGTH_SHORT).show();

            }
        });
        holder.rbDiemDanhGia.setRating(diemTrungBinh);
        Drawable drawable = holder.rbDiemDanhGia.getProgressDrawable();
        drawable.setColorFilter(Color.parseColor("#ffbd00"), PorterDuff.Mode.SRC_ATOP);

        if (item.getChiTietDienThoai().getMaDienThoai().getMaUuDai() == null){
            holder.tvGiaTienGoc.setVisibility(View.GONE);
        }else {
            holder.tvGiaTienGoc.setText("" + item.getChiTietDienThoai().getGiaTien());
            DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");
            String tongTien = String.valueOf(item.getChiTietDienThoai().getGiaTien());
            try {
                double tongTienNumber = Double.parseDouble(tongTien);
                String formattedNumber = decimalFormat.format(tongTienNumber);
                holder.tvGiaTienGoc.setPaintFlags(holder.tvGiaTienGoc.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
                holder.tvGiaTienGoc.setText(formattedNumber+"₫");
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
        String tongTienGiam;
        if (item.getChiTietDienThoai().getMaDienThoai().getMaUuDai() == null){
            tongTienGiam = String.valueOf(item.getChiTietDienThoai().getGiaTien());

        }else{
            tongTienGiam = String.valueOf(item.getChiTietDienThoai().getGiaTien() - (item.getChiTietDienThoai().getGiaTien() * (Double.parseDouble(item.getChiTietDienThoai().getMaDienThoai().getMaUuDai().getGiamGia()) / 100)));
        }

        DecimalFormat decimalFormat1 = new DecimalFormat("#,##0");
        try {
            double tongTienGiamNumber = Double.parseDouble(tongTienGiam);
            String formattedNumber = decimalFormat1.format(tongTienGiamNumber);
            holder.tvGiaTienGiam.setText(formattedNumber+"₫");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }


        if (item.getChiTietDienThoai().getHinhAnh().equals("")){
            holder.imgPhone.setVisibility(View.GONE);
        }else {
            Picasso.get().load(item.getChiTietDienThoai().getHinhAnh()).into(holder.imgPhone);
        }
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.onClickDetail(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        LinearLayout bgSale;
        TextView tvSale , tvTenDienThoai, tvGiaTienGiam , tvGiaTienGoc;
        RatingBar rbDiemDanhGia;
        ImageView imgCart, imgPhone;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bgSale = itemView.findViewById(R.id.itemListPhone_bgSale);
            tvTenDienThoai = itemView.findViewById(R.id.itemListPhone_tvTenDienThoai);
            tvSale = itemView.findViewById(R.id.itemListPhone_tvSale);
            tvGiaTienGiam = itemView.findViewById(R.id.itemListPhone_tvGiaTienGiam);
            tvGiaTienGoc = itemView.findViewById(R.id.itemListPhone_tvGiaTienGoc);
            rbDiemDanhGia = itemView.findViewById(R.id.itemListPhone_rbDiemDanhGia);
            imgPhone = itemView.findViewById(R.id.itemListPhone_imgDienThoai);
        }
    }
}
