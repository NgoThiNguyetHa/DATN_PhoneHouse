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
import com.example.appcuahang.interface_adapter.IItemRamListenner;
import com.example.appcuahang.model.Mau;
import com.example.appcuahang.model.Ram;

import java.util.List;

public class RamAdapter extends RecyclerView.Adapter<RamAdapter.MyViewHolder>{
    Context mContext;
    List<Ram> list;
    private IItemRamListenner listenner;

    public RamAdapter(Context mContext, IItemRamListenner listenner) {
        this.mContext = mContext;
        this.listenner = listenner;
    }
    public void setData(List<Ram> list){ // thêm mới
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
        holder.tvRam.setText(ram.getRAM() +" GB");
        holder.mRam.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenner.editRam(ram);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
    public static final class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvRam;
        LinearLayout mRam;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvRam = itemView.findViewById(R.id.item_tvRam);
            mRam = itemView.findViewById(R.id.mRam);
        }
    }
}
