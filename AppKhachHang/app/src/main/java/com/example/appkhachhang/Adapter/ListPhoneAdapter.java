package com.example.appkhachhang.Adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appkhachhang.Interface_Adapter.IItemListPhoneListener;
import com.example.appkhachhang.Model.ChiTietGioHang;
import com.example.appkhachhang.Model.DanhGia;
import com.example.appkhachhang.Model.ListPhone;
import com.example.appkhachhang.Model.Root;
import com.example.appkhachhang.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ListPhoneAdapter extends RecyclerView.Adapter<ListPhoneAdapter.ViewHolder> {
    Context mContext;
//    List<ListPhone> list;
    List<DanhGia> danhGiaList = new ArrayList<>();
    List<Root> list;
    IItemListPhoneListener listener;
    public ListPhoneAdapter(Context mContext) {
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
    public ListPhoneAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_danh_sach_dien_thoai, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListPhoneAdapter.ViewHolder holder, int position) {
//        ListPhone item = list.get(position);
//        String tenDienThoai = item.getMaDienThoai().getTenDienThoai();
//        if (tenDienThoai.length() > 10) {
//            holder.tvTenDienThoai.setText("Điện thoại " + tenDienThoai);
//        } else {
//            holder.tvTenDienThoai.setText("Điện thoại " + tenDienThoai + "\n");
//        }
//        holder.tvMau.setText(""+item.getMaMau().getTenMau());
//
//        holder.tvRam.setText(""+item.getMaRam().getRAM() +" GB");
//        holder.tvDungLuong.setText(""+item.getMaDungLuong().getBoNho() + " GB");
//        if (item.getMaDienThoai().getMaUuDai() == null){
//            holder.tvSale.setText("");
//            holder.tvSale.setBackgroundColor(Color.WHITE);
//        }else {
//            holder.tvSale.setText("Sale "+item.getMaDienThoai().getMaUuDai().getGiamGia() +"%");
//        }
//        holder.rbDiemDanhGia.setRating(5);
//        Drawable drawable = holder.rbDiemDanhGia.getProgressDrawable();
//        drawable.setColorFilter(Color.parseColor("#ffbd00"), PorterDuff.Mode.SRC_ATOP);
//        holder.imgCart.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        //gia tien
//        if (item.getMaDienThoai().getMaUuDai() == null){
//            holder.tvGiaTienGoc.setText("");
//            holder.tvGiaTienGoc.setVisibility(View.GONE);
//        }else {
//            holder.tvGiaTienGoc.setText("" + item.getGiaTien());
//        }
//        //
//        DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");
//        String tongTien = String.valueOf(item.getGiaTien());
//        try {
//            double tongTienNumber = Double.parseDouble(tongTien);
//            String formattedNumber = decimalFormat.format(tongTienNumber);
//            holder.tvGiaTienGoc.setPaintFlags(holder.tvGiaTienGoc.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
//            holder.tvGiaTienGoc.setText(formattedNumber+"₫");
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//        }
//        //
//        String tongTienGiam ;
//        if (item.getMaDienThoai().getMaUuDai() == null){
//            tongTienGiam = String.valueOf(item.getGiaTien());
//
//        }else{
//            tongTienGiam = String.valueOf(item.getGiaTien() * Integer.parseInt(item.getMaDienThoai().getMaUuDai().getGiamGia()) / 100);
//
//        }
//        try {
//            double tongTienGiamNumber = Double.parseDouble(tongTienGiam);
//            String formattedNumber = decimalFormat.format(tongTienGiamNumber);
//            holder.tvGiaTienGiam.setText(formattedNumber+"₫");
//        } catch (NumberFormatException e) {
//            e.printStackTrace();
//        }
//        if (item.getHinhAnh().equals("")){
//            holder.imgPhone.setImageResource(R.drawable.shape_custom_dialog);
//        }else {
//            Picasso.get().load(item.getHinhAnh()).into(holder.imgPhone);
//        }

        Root item = list.get(position);
        String tenDienThoai = item.getChiTietDienThoai().getMaDienThoai().getTenDienThoai();
        if (tenDienThoai.length() > 10) {
            holder.tvTenDienThoai.setText("Điện thoại " + tenDienThoai );
        } else {
            holder.tvTenDienThoai.setText("Điện thoại " + tenDienThoai +"\n");
        }
        holder.tvMau.setText(""+item.getChiTietDienThoai().getMaMau().getTenMau());

        holder.tvRam.setText(""+item.getChiTietDienThoai().getMaRam().getRAM() +" GB");
        holder.tvDungLuong.setText(""+item.getChiTietDienThoai().getMaDungLuong().getBoNho() + " GB");if (item.getChiTietDienThoai().getMaDienThoai().getMaUuDai() == null) {
            holder.tvSale.setText("");
            holder.tvSale.setBackgroundColor(Color.WHITE);
        } else {
            holder.tvSale.setText("Sale " + item.getChiTietDienThoai().getMaDienThoai().getMaUuDai().getGiamGia() + "%");
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
                Toast.makeText(mContext, toastMessage, Toast.LENGTH_SHORT).show();

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
        TextView tvSale , tvTenDienThoai, tvMau , tvRam , tvDungLuong , tvGiaTienGiam , tvGiaTienGoc;
        RatingBar rbDiemDanhGia;
        ImageView imgCart, imgPhone;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            bgSale = itemView.findViewById(R.id.itemListPhone_bgSale);
            tvTenDienThoai = itemView.findViewById(R.id.itemListPhone_tvTenDienThoai);
            tvSale = itemView.findViewById(R.id.itemListPhone_tvSale);
            tvMau = itemView.findViewById(R.id.itemListPhone_tvMau);
            tvRam = itemView.findViewById(R.id.itemListPhone_tvRam);
            tvDungLuong = itemView.findViewById(R.id.itemListPhone_tvDungLuong);
            tvGiaTienGiam = itemView.findViewById(R.id.itemListPhone_tvGiaTienGiam);
            tvGiaTienGoc = itemView.findViewById(R.id.itemListPhone_tvGiaTienGoc);
            rbDiemDanhGia = itemView.findViewById(R.id.itemListPhone_rbDiemDanhGia);
            imgCart = itemView.findViewById(R.id.itemListPhone_imgCart);
            imgPhone = itemView.findViewById(R.id.itemListPhone_imgDienThoai);
        }
    }
}
