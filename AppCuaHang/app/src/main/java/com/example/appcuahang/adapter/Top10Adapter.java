package com.example.appcuahang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcuahang.R;
import com.example.appcuahang.model.Brand;
import com.example.appcuahang.model.Mau;
import com.example.appcuahang.model.Top10sanPham;

import java.text.DecimalFormat;
import java.util.List;

public class Top10Adapter extends RecyclerView.Adapter<Top10Adapter.MyViewHolder>{
    Context mContext;
    List<Top10sanPham> list;

    public Top10Adapter(Context mContext) {
        this.mContext = mContext;
    }

    public void setData(List<Top10sanPham> list){
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_top10 , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Top10sanPham item = list.get(position);
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        String formattedNumber = decimalFormat.format(item.getGiaTien());
        holder.tvTenDienThoai.setText(""+item.getTenDienThoai());
        holder.tvGiaTien.setText("Giá tiền: "+formattedNumber + " đ");
        holder.tvSoLuong.setText("Số lượng: "+item.getSoLuong());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static final class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvTenDienThoai;
        TextView tvGiaTien;
        TextView tvSoLuong;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenDienThoai = itemView.findViewById(R.id.itemTop10_tvTen);
            tvGiaTien = itemView.findViewById(R.id.itemTop10_tvGiaTien);
            tvSoLuong = itemView.findViewById(R.id.itemTop10_tvSoLuong);
        }
    }
}
