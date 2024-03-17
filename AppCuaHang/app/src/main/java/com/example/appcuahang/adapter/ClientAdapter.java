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
import com.example.appcuahang.interface_adapter.interface_adapter.IItemClientListenner;
import com.example.appcuahang.model.Client;

import java.util.List;

public class ClientAdapter extends RecyclerView.Adapter<ClientAdapter.MyViewHolder> {
    Context mContext;
    List<Client> list;
    private IItemClientListenner listenner;
    
    public ClientAdapter(Context mContext, IItemClientListenner listenner){
        this.mContext = mContext;
        this.listenner = listenner;
    }
    
    public void setData(List<Client> list){
        this.list = list;
    }


    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_client, parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Client client = list.get(position);
        holder.tvKhachHang.setText(client.getUsername());


        holder.mKH.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenner.showDetail(String.valueOf(client));
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static final class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvKhachHang;
        LinearLayout mKH;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvKhachHang = itemView.findViewById(R.id.item_tvKhanhHang);
            mKH = itemView.findViewById(R.id.mKH);
        }
    }
}
