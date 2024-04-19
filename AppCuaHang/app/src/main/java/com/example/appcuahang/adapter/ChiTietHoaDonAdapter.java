package com.example.appcuahang.adapter;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcuahang.R;
import com.example.appcuahang.model.ChiTietHoaDon;
import com.example.appcuahang.model.DetailPhone;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.List;
import java.util.Locale;

public class ChiTietHoaDonAdapter extends RecyclerView.Adapter<ChiTietHoaDonAdapter.MyViewHolder> {
    List<ChiTietHoaDon> list;
    Context mContext;

    public void setData(List<ChiTietHoaDon> list){ // thêm mới
        this.list = list;
    }

    public ChiTietHoaDonAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_chi_tiet_hoa_don , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        ChiTietHoaDon item = list.get(position);
        Log.d("zzzz", "onBindViewHolder: "+ item.getMaChiTietDienThoai().getMaDienThoai().getTenDienThoai()+" "+ item.getMaChiTietDienThoai().getMaRam().getRAM()+"GB/"+item.getMaChiTietDienThoai().getMaDungLuong().getBoNho()+"GB");
        holder.tvTenDienThoai.setText(item.getMaChiTietDienThoai().getMaDienThoai().getTenDienThoai()+" "+ item.getMaChiTietDienThoai().getMaRam().getRAM()+"GB/"+item.getMaChiTietDienThoai().getMaDungLuong().getBoNho()+"GB");
        holder.tvMau.setText(item.getMaChiTietDienThoai().getMaMau().getTenMau());
        DecimalFormat decimalFormat = new DecimalFormat("#,##0.##");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        symbols.setGroupingSeparator('.');
        symbols.setDecimalSeparator(',');
        decimalFormat.setDecimalFormatSymbols(symbols);

        try {
            double tongTienNumber = Double.parseDouble(String.valueOf(item.getMaChiTietDienThoai().getGiaTien()));
            String formattedNumber = decimalFormat.format(tongTienNumber);
            holder.tvGiaTien.setText(formattedNumber + " đ");
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        holder.tvSoLuong.setText("x"+item.getSoLuong());
        if (item.getMaChiTietDienThoai().getHinhAnh() == null){
            holder.imgChiTietSP.setImageResource(R.drawable.img_10);
        }else {
            Picasso.get().load(item.getMaChiTietDienThoai().getHinhAnh()).into(holder.imgChiTietSP);
        }
        if (position == (list.size()-1)){
            holder.viewKeNgang.setVisibility(View.GONE);
        }
    }

    public int getItemCount() {
        return list.size();
    }

    public static final class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvSoLuong, tvGiaTien, tvMau, tvTenDienThoai;
        ImageView imgChiTietSP;
        View viewKeNgang;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMau = itemView.findViewById(R.id.tvMau);
            tvTenDienThoai = itemView.findViewById(R.id.tvTenDienThoai);
            imgChiTietSP = itemView.findViewById(R.id.imgChiTietSP);
            tvSoLuong= itemView.findViewById(R.id.tvSoLuong);
            tvGiaTien = itemView.findViewById(R.id.tvGiaTien);
            viewKeNgang = itemView.findViewById(R.id.viewKeNgang);
        }
    }
}
