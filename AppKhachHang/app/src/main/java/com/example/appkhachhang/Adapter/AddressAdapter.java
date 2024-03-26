package com.example.appkhachhang.Adapter;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appkhachhang.Interface_Adapter.IItemAddressListenner;
import com.example.appkhachhang.Model.AddressDelivery;
import com.example.appkhachhang.R;

import java.util.List;
public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.MyViewHolder> {
    Context mContext;
    List<AddressDelivery> list;
    private IItemAddressListenner listenner;
    public AddressAdapter(Context mContext, IItemAddressListenner listenner) {
        this.mContext = mContext;
        this.listenner = listenner;
    }
    public void setData(List<AddressDelivery> list){ // thêm mới
        this.list = list;
    }
    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_address, parent, false));
    }
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        AddressDelivery addressDelivery = list.get(position);
        holder.nameTextView.setText("" + addressDelivery.getTenNguoiNhan());
        holder.addressTextView.setText("Địa chỉ: " + addressDelivery.getDiaChi());
        holder.phoneTextView.setText("" + addressDelivery.getSdt());
        holder.itemDiaChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenner.editAddress(addressDelivery);
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static final class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nameTextView;
        TextView addressTextView;
        TextView phoneTextView;
        CardView itemDiaChi;
        public MyViewHolder(View itemView) {
            super(itemView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            addressTextView = itemView.findViewById(R.id.addressTextView);
            phoneTextView = itemView.findViewById(R.id.phoneTextView);
            itemDiaChi = itemView.findViewById(R.id.itemDiaChi);
        }
    }
}
