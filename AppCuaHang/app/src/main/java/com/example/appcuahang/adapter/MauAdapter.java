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
import com.example.appcuahang.interface_adapter.IItemBrandListenner;
import com.example.appcuahang.interface_adapter.IItemMauListenner;
import com.example.appcuahang.model.Brand;
import com.example.appcuahang.model.Mau;

import java.util.List;

public class MauAdapter  extends RecyclerView.Adapter<MauAdapter.MyViewHolder>{
    Context mContext;
    List<Mau> list;
    private IItemMauListenner listenner;

    public MauAdapter(Context mContext , IItemMauListenner listener) { // thêm listener
        this.mContext = mContext;
        this.listenner = listener;
    }
    public void setData(List<Mau> list){ // thêm mới
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_mau , parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Mau mau = list.get(position);
        holder.tvMau.setText(""+mau.getTenMau());
        holder.tvGiaTien.setText(""+mau.getGiaTien());
        holder.mMau.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenner.editMau(mau);
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public static final class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvMau;
        TextView tvGiaTien;
        LinearLayout mMau;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvMau = itemView.findViewById(R.id.item_tvMau);
            tvGiaTien = itemView.findViewById(R.id.item_tvGiaTien);
            mMau = itemView.findViewById(R.id.mMau);
        }
    }
}
