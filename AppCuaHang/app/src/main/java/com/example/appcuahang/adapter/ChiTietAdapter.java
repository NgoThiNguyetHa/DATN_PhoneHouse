package com.example.appcuahang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcuahang.R;
import com.example.appcuahang.interface_adapter.IItemDetailPhoneListenner;
import com.example.appcuahang.interface_adapter.IItemMauListenner;
import com.example.appcuahang.interface_adapter.IItemPhoneListenner;
import com.example.appcuahang.model.DetailPhone;
import com.example.appcuahang.model.Mau;

import java.text.DecimalFormat;
import java.util.List;

public class ChiTietAdapter extends RecyclerView.Adapter<ChiTietAdapter.MyViewHolder>{
    Context mContext;
    List<DetailPhone> list;
    private IItemDetailPhoneListenner listener;

    public ChiTietAdapter(Context mContext) {
        this.mContext = mContext;
    }
    public void setData(List<DetailPhone> list){ // thêm mới
        this.list = list;
    }
    public void setOnClick(IItemDetailPhoneListenner listener){
        this.listener = listener;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_chi_tiet , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DetailPhone item = list.get(position);
        holder.tvRam.setText(""+item.getMaRam());
        holder.tvDungLuong.setText(""+item.getMaDungLuong()+" GB");
        holder.tvMau.setText(""+item.getMaMau());
        holder.tvSoLuong.setText("Số lượng: "+item.getSoLuong());
        DecimalFormat decimalFormat = new DecimalFormat("#,##0");
        String formattedNumber = decimalFormat.format(item.getGiaTien());
        holder.tvGiaTien.setText(""+formattedNumber+"đ");
        holder.mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.editDetail(item);
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public static final class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvMau , tvRam , tvDungLuong , tvGiaTien, tvSoLuong;
        LinearLayout mParent;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMau = itemView.findViewById(R.id.itemChiTiet_tvMau);
            tvRam = itemView.findViewById(R.id.itemChiTiet_tvRam);
            tvDungLuong = itemView.findViewById(R.id.itemChiTiet_tvDungLuong);
            tvSoLuong= itemView.findViewById(R.id.itemChiTiet_tvSoLuong);
            tvGiaTien = itemView.findViewById(R.id.itemChiTiet_tvGiaTien);
            mParent = itemView.findViewById(R.id.itemChiTiet_mParent);
        }
    }
}
