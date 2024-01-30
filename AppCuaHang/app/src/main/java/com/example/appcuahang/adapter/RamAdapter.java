package com.example.appcuahang.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcuahang.R;
import com.example.appcuahang.model.Ram;

import java.util.List;

public class RamAdapter extends RecyclerView.Adapter<RamAdapter.MyViewHolder>{
    Context mContext;
    List<Ram> list;

    public RamAdapter(Context mContext, List<Ram> list) {
        this.mContext = mContext;
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_ram ,parent , false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Ram ram = list.get(position);
        holder.tvRam.setText(""+ram.getRAM());
        holder.tvGiaTien.setText(""+ram.getGiaTien());

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static final class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvRam;
        TextView tvGiaTien;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRam = itemView.findViewById(R.id.item_tvRam);
            tvGiaTien = itemView.findViewById(R.id.item_tvGiaTien);
        }
    }
}
