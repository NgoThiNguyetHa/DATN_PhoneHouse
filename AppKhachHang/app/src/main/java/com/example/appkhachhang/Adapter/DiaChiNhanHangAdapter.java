package com.example.appkhachhang.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appkhachhang.Model.AddressDelivery;
import com.example.appkhachhang.Model.DiaChiNhanHang;
import com.example.appkhachhang.R;

import java.util.List;

public class DiaChiNhanHangAdapter extends RecyclerView.Adapter<DiaChiNhanHangAdapter.ViewHolder> {
    Context mContext;
    List<AddressDelivery> list;
    private int mSelectedPosition = -1;

    private OnItemCheckedChangeListener mListener;
    public DiaChiNhanHangAdapter(Context mContext, List<AddressDelivery> list) {
        this.mContext = mContext;
        this.list = list;
        notifyDataSetChanged();
    }

    public interface OnItemCheckedChangeListener {
        void onItemCheckedChanged(AddressDelivery addressDelivery);
    }

    public void setOnItemCheckedChangeListener(OnItemCheckedChangeListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public DiaChiNhanHangAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_diachinhanhang, parent, false);
        return new DiaChiNhanHangAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull DiaChiNhanHangAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        AddressDelivery addressDelivery = list.get(position);
        holder.tvTen.setText(addressDelivery.getTenNguoiNhan());
        holder.tvDiaChi.setText(addressDelivery.getDiaChi());
        holder.tvSdt.setText(addressDelivery.getSdt());
        holder.rdo_diaChi.setChecked(position == mSelectedPosition);
        holder.rdo_diaChi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mSelectedPosition = position;
                notifyDataSetChanged();
                if (mListener!=null){
                    mListener.onItemCheckedChanged(addressDelivery);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RadioButton rdo_diaChi;
        TextView tvTen, tvSdt, tvDiaChi;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTen = itemView.findViewById(R.id.tvHoTenKhachHang);
            tvSdt = itemView.findViewById(R.id.tvSoDienThoai);
            tvDiaChi = itemView.findViewById(R.id.tvDiaChiNhanHang);
        }
    }
    public int getSelectedPosition() {
        return mSelectedPosition;
    }
}
