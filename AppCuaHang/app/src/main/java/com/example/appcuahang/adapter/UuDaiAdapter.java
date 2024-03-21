package com.example.appcuahang.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appcuahang.R;
import com.example.appcuahang.interface_adapter.interface_adapter.IItemUuDaiListenner;
import com.example.appcuahang.model.DungLuong;
import com.example.appcuahang.model.UuDai;

import java.util.List;

public class UuDaiAdapter extends RecyclerView.Adapter<UuDaiAdapter.MyViewHolder> {
    Context mContext;
    List<UuDai> list;
    private IItemUuDaiListenner listenner;

    public UuDaiAdapter(Context mContext, IItemUuDaiListenner listenner) {
        this.mContext = mContext;
        this.listenner = listenner;
    }

    public void setData(List<UuDai> list) {
        this.list = list;
    }


    @NonNull
    @Override
    public UuDaiAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_uudai, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull UuDaiAdapter.MyViewHolder holder, int position) {
        UuDai uuDai = list.get(position);
        holder.tvGiamGia.setText(uuDai.getGiamGia() + "%");
        holder.tvThoiGian.setText("Hạn sử dụng: " + uuDai.getThoiGian());
        holder.tvTrangThai.setText(uuDai.getTrangThai());

        // Kiểm tra trạng thái và đặt màu sắc cho các phần tử
        if (uuDai.getTrangThai().equals("Hoạt động")) {
           holder.tvTrangThai.setTextColor(Color.GREEN); // Màu xanh hoạt động
        } else {
            holder.tvTrangThai.setTextColor(Color.RED);; // Màu đỏ không hoạt động
        }

        holder.item_frame.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listenner.editUuDai(uuDai);
                Toast.makeText(mContext, "on Click item", Toast.LENGTH_SHORT).show();
            }
        });
        holder.tvGiamGia.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listenner.editUuDai(uuDai);
                Toast.makeText(mContext, "on Click tv", Toast.LENGTH_SHORT).show();

            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static final class MyViewHolder extends RecyclerView.ViewHolder {
        TextView tvGiamGia, tvThoiGian, tvTrangThai;
        LinearLayout mdUD;
        CardView item_frame;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            tvGiamGia = itemView.findViewById(R.id.item_tvGiamGia);
            tvThoiGian = itemView.findViewById(R.id.item_tvThoiGian);
            tvTrangThai = itemView.findViewById(R.id.item_tvTrangThai);
            mdUD = itemView.findViewById(R.id.mUD);
            item_frame = itemView.findViewById(R.id.item_frame);
        }
    }
}