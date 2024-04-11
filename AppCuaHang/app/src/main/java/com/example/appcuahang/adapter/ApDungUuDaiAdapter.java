package com.example.appcuahang.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcuahang.R;
import com.example.appcuahang.interface_adapter.IItemUuDaiListenner;
import com.example.appcuahang.model.UuDai;

import java.util.List;

public class ApDungUuDaiAdapter extends RecyclerView.Adapter<ApDungUuDaiAdapter.MyViewHolder> {
    Context mContext;
    List<UuDai> list;
    private IItemUuDaiListenner listenner;
    private int selectedItemPosition = -1;

    public ApDungUuDaiAdapter(Context mContext, IItemUuDaiListenner listenner) {
        this.mContext = mContext;
        this.listenner = listenner;
    }

    public void setData(List<UuDai> list) {
        this.list = list;
    }


    @NonNull
    @Override
    public ApDungUuDaiAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_apdung_uudai, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ApDungUuDaiAdapter.MyViewHolder holder, int position) {
        UuDai uuDai = list.get(position);
        holder.tvGiamGia.setText("Giảm "+uuDai.getGiamGia() + "%");
        holder.tvThoiGian.setText("" + uuDai.getThoiGian());
        holder.tvTrangThai.setText(uuDai.getTrangThai());

        // Kiểm tra trạng thái và đặt màu sắc cho các phần tử
        if (uuDai.getTrangThai().equals("Hoạt động")) {
           holder.tvTrangThai.setBackgroundResource(R.drawable.bg_hoat_dong); // Màu xanh hoạt động
        } else {
            holder.tvTrangThai.setBackgroundResource(R.drawable.bg_delete_yes);; // Màu đỏ không hoạt động
        }

        holder.item_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenner.editUuDai(uuDai);
                listenner.selectUuDai(uuDai._id);
                holder.mdUD.setBackgroundResource(R.color.item_uu_dai);
                holder.tvGiamGia.setTextColor(Color.WHITE);
                holder.tvThoiGian.setTextColor(Color.WHITE);
                holder.tvTrangThai.setTextColor(Color.WHITE);
                holder.textViewThoiGian.setTextColor(Color.WHITE);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static final class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvGiamGia, tvThoiGian, tvTrangThai , textViewThoiGian;
        LinearLayout mdUD;
        CardView item_frame;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGiamGia = itemView.findViewById(R.id.item_tvGiamGia);
            tvThoiGian = itemView.findViewById(R.id.item_tvThoiGian);
            tvTrangThai = itemView.findViewById(R.id.item_tvTrangThai);
            mdUD = itemView.findViewById(R.id.mUD);
            item_frame = itemView.findViewById(R.id.item_frame);
            textViewThoiGian = itemView.findViewById(R.id.item_textViewThoiGian);
        }
    }
}