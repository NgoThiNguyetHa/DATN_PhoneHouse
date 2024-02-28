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
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.squareup.picasso.Picasso;

import java.util.List;

public class BrandAdapter extends RecyclerView.Adapter<BrandAdapter.MyViewHolder>{
    Context mContext;
    List<Brand> list;

    private IItemBrandListenner listener;

    private FirebaseStorage storage;
    private StorageReference storageRef;

    public BrandAdapter(Context mContext , IItemBrandListenner listener) {
        this.mContext = mContext;
        this.listener = listener;
    }

    public void setData(List<Brand> list){
        this.list = list;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_brand,parent,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Brand brand = list.get(position);
        holder.tvBrand.setText(""+brand.getTenHang());
        holder.mParent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.editBrand(brand);
            }
        });
        if (brand.getHinhAnh() == null){
            holder.item_imgBrand.setImageResource(R.drawable.img_10);
        }else {
            Picasso.get().load(brand.getHinhAnh()).into(holder.item_imgBrand);
        }

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static final class MyViewHolder extends RecyclerView.ViewHolder{
        TextView tvBrand;
        ImageView item_imgBrand;
        LinearLayout mParent;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvBrand = itemView.findViewById(R.id.item_tvBrand);
            item_imgBrand = itemView.findViewById(R.id.item_imgBrand);
            mParent = itemView.findViewById(R.id.mParent);
        }
    }

}
