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
import com.example.appcuahang.interface_adapter.interface_adapter.IItemBrandListenner;
import com.example.appcuahang.model.Brand;
import com.example.appcuahang.model.Phone;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.MyViewHolder>{
    Context mContext;
    List<Phone> list;
    public PhoneAdapter(Context mContext ) {
        this.mContext = mContext;
    }

    public void setData(List<Phone> list){ // thêm mới
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_phone,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Phone item = list.get(position);
        holder.mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
        holder.tvTenDT.setText("Tên điện thoại: "+item.getTenDienThoai());
        if (item.getHinhAnh() == null){
            holder.item_imgPhone.setImageResource(R.drawable.img_10);
        }else {
            Picasso.get().load(item.getHinhAnh()).into(holder.item_imgPhone);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static final class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvTenDT;
        ImageView item_imgPhone;
        LinearLayout mParent;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenDT = itemView.findViewById(R.id.itemPhone_tvTenDienThoai);
            item_imgPhone = itemView.findViewById(R.id.itemPhone_imgPhone);
            mParent = itemView.findViewById(R.id.mParent);
        }
    }

}
