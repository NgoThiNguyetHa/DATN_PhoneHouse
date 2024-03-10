package com.example.appcuahang.adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcuahang.R;
import com.example.appcuahang.interface_adapter.IItemBrandListenner;
import com.example.appcuahang.interface_adapter.IItemPhoneListenner;
import com.example.appcuahang.model.Phone;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PhoneAdapter extends RecyclerView.Adapter<PhoneAdapter.MyViewHolder>{
    Context mContext;
    List<Phone> list;
    private IItemPhoneListenner listener; // mới
    public PhoneAdapter(Context mContext , IItemPhoneListenner listener ) {
        this.mContext = mContext;
        this.listener = listener;
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
                dialog(item,Gravity.BOTTOM);
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

    private void dialog(Phone phone, int gravity){
        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
        View view = LayoutInflater.from(mContext).inflate(R.layout.dialog_menu, null);
        builder.setView(view);
        Dialog dialog = builder.create();
        dialog.show();
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams windowAttributes = window.getAttributes();
        windowAttributes.gravity = gravity;
        window.setAttributes(windowAttributes);
        TextView tvSua , tvXemChiTiet , tvThemChiTiet;
        tvSua = view.findViewById(R.id.menuDienThoai_tvSua);
        tvXemChiTiet = view.findViewById(R.id.menuDienThoai_tvXemChiTiet);
        tvThemChiTiet = view.findViewById(R.id.menuDienThoai_tvThemChiTiet);
        tvSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.editPhone(phone);
                dialog.dismiss();
            }

        });
        tvXemChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.detailPhone(phone);
                dialog.dismiss();
            }
        });
        tvThemChiTiet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listener.addDetail(phone.get_id(), phone.getTenDienThoai());
                dialog.dismiss();
            }
        });
    }

}
