package com.example.appcuahang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcuahang.R;
import com.example.appcuahang.interface_adapter.interface_adapter.IItemDungLuongListenner;
import com.example.appcuahang.model.DungLuong;

import java.util.List;

public class DungLuongAdapter extends RecyclerView.Adapter<DungLuongAdapter.MyViewHolder>{
    Context mContext;
    List<DungLuong> list;
    private IItemDungLuongListenner listenner;


    public DungLuongAdapter(Context mContext, IItemDungLuongListenner listenner) {
        this.mContext = mContext;
        this.listenner = listenner;
    }
    public void setData(List<DungLuong> list){
        this.list = list;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_dungluong ,parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        DungLuong dungLuong = list.get(position);
        holder.tvDungLuong.setText(dungLuong.getBoNho()+ " GB");
        holder.tvGiaTien.setText(""+dungLuong.getGiaTien());

        holder.mDL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenner.editDungLuong(dungLuong);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static final class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvDungLuong;
        TextView tvGiaTien;
        LinearLayout mDL;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvDungLuong = itemView.findViewById(R.id.item_tvBoNho);
            tvGiaTien = itemView.findViewById(R.id.item_tvGiaTien);
            mDL = itemView.findViewById(R.id.mDL);
        }
    }
}
