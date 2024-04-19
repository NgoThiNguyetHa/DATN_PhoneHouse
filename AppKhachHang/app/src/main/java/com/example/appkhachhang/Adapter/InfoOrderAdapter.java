package com.example.appkhachhang.Adapter;

import android.content.Context;
import android.graphics.Paint;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appkhachhang.Interface.OnItemClickListenerDanhGia;
import com.example.appkhachhang.Model.ChiTietDienThoai;
import com.example.appkhachhang.Model.ChiTietHoaDon;
import com.example.appkhachhang.Model.ThongTinDonHang;
import com.example.appkhachhang.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.List;

public class InfoOrderAdapter extends RecyclerView.Adapter<InfoOrderAdapter.MyViewHolder> {
    Context mContext;
    List<ThongTinDonHang> list;

    public InfoOrderAdapter(Context mContext, List<ThongTinDonHang> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_thong_tin_don_hang,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull InfoOrderAdapter.MyViewHolder holder, int position) {
        ThongTinDonHang chiTietHoaDon = list.get(position);
        if (chiTietHoaDon.getMaChiTietDienThoai().getMaDienThoai().getHinhAnh() == null){
            holder.imgDienThoai.setImageResource(R.drawable.img_3);
        }else {
            Picasso.get().load(chiTietHoaDon.getMaChiTietDienThoai().getMaDienThoai().getHinhAnh()).into(holder.imgDienThoai);
        }
        holder.tvTenDienThoai.setText("Điện thoại "+chiTietHoaDon.getMaChiTietDienThoai().getMaDienThoai().getTenDienThoai());
        holder.tvMau.setText(""+chiTietHoaDon.getMaChiTietDienThoai().getMaMau().getTenMau());
        holder.tvRamDungLuong.setText("Cấu hình: "+chiTietHoaDon.getMaChiTietDienThoai().getMaRam().getRAM()+"gb"+" / "+chiTietHoaDon.getMaChiTietDienThoai().getMaDungLuong().getBoNho()+"gb");
        holder.tvSoLuong.setText("x"+chiTietHoaDon.getSoLuong());
        if (chiTietHoaDon.getMaChiTietDienThoai().getMaDienThoai().getMaUuDai() == null){
            holder.tvGiaTienGiam.setText("");
            holder.tvGiaTienGiam.setVisibility(View.GONE);
        }else {
            holder.tvGiaTienGiam.setText("" + chiTietHoaDon.getMaChiTietDienThoai().getMaDienThoai().getMaUuDai().getGiamGia());
        }
        //
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");
        String tongTien = String.valueOf(chiTietHoaDon.getMaChiTietDienThoai().getGiaTien());
        try {
            double tongTienNumber = Double.parseDouble(tongTien);
            String formattedNumber = decimalFormat.format(tongTienNumber);
            holder.tvGiaTienGiam.setPaintFlags(holder.tvGiaTienGiam.getPaintFlags()| Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tvGiaTienGiam.setText(formattedNumber+"₫");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        //
        String tongTienGiam ;
        if (chiTietHoaDon.getMaChiTietDienThoai().getMaDienThoai().getMaUuDai() == null){
            tongTienGiam = String.valueOf(chiTietHoaDon.getMaChiTietDienThoai().getGiaTien());

        }else{
            tongTienGiam = String.valueOf(chiTietHoaDon.getMaChiTietDienThoai().getGiaTien() * Integer.parseInt(chiTietHoaDon.getMaChiTietDienThoai().getMaDienThoai().getMaUuDai().getGiamGia()) / 100);

        }
        try {
            double tongTienGiamNumber = Double.parseDouble(tongTienGiam);
            String formattedNumber = decimalFormat.format(tongTienGiamNumber);
            holder.tvGiaTien.setText(formattedNumber+"₫");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        //
        holder.tvBaoHanh.setText("Bảo hành "+chiTietHoaDon.getMaChiTietDienThoai().getMaDienThoai().getThoiGianBaoHanh());
    }



    @Override
    public int getItemCount() {
        return list.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder{

        TextView tvTenDienThoai, tvMau,tvRamDungLuong,tvSoLuong, tvGiaTien,tvGiaTienGiam, tvBaoHanh;
        ImageView imgDienThoai;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);


            tvTenDienThoai = itemView.findViewById(R.id.item_detailOrder_tvTenDienThoai);
            tvMau = itemView.findViewById(R.id.item_detailOrder_tvMau);
            tvRamDungLuong = itemView.findViewById(R.id.item_detailOrder_tvRamDungLuong);
            tvSoLuong = itemView.findViewById(R.id.item_detailOrder_tvSoLuong);
            imgDienThoai = itemView.findViewById(R.id.imgDienThoai);
            tvGiaTien = itemView.findViewById(R.id.item_detailOrder_tvGiaTien);
            imgDienThoai = itemView.findViewById(R.id.item_detailOrder_imgDienThoai);
            tvGiaTienGiam = itemView.findViewById(R.id.item_detailOrder_tvGiaTienGiam);
            tvBaoHanh = itemView.findViewById(R.id.item_detailOrder_tvBaoHanh);
        }
    }
}
