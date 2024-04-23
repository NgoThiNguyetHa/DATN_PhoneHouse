package com.example.appkhachhang.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appkhachhang.Interface_Adapter.IItemListPhoneListener;
import com.example.appkhachhang.Interface_Adapter.IItemStoreListener;
import com.example.appkhachhang.Model.Root;
import com.example.appkhachhang.Model.Store;
import com.example.appkhachhang.R;

import java.util.List;

public class StoreAdapter extends RecyclerView.Adapter<StoreAdapter.ViewHolder>{
    Context mContext;
    List<Store> list;
    IItemStoreListener listener;

    public StoreAdapter(Context mContext) {
        this.mContext = mContext;
    }
    public void setOnClickListener(IItemStoreListener listener){
        this.listener = listener;
    }
    public void setData(List<Store> list) {
        this.list = list;
    }

    @NonNull
    @Override
    public StoreAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_store, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull StoreAdapter.ViewHolder holder, int position) {
        Store item = list.get(position);
        holder.tvTenCuaHang.setText(item.getUsername());
        holder.tvDiaChi.setText(item.getDiaChi());
        holder.ln_Store.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.getSanPhamTheoCuaHang(item);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        TextView tvTenCuaHang, tvDiaChi;
        CardView ln_Store;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenCuaHang = itemView.findViewById(R.id.tvTenCuaHang);
            tvDiaChi = itemView.findViewById(R.id.tvDiaChi);
            ln_Store = itemView.findViewById(R.id.ln_Store);
        }
    }
}
