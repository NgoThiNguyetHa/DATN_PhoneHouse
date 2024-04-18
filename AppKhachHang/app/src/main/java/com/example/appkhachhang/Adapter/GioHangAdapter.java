package com.example.appkhachhang.Adapter;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appkhachhang.Api.ApiRetrofit;
import com.example.appkhachhang.Api.ApiService;
import com.example.appkhachhang.Api.ChiTietSanPham_API;
import com.example.appkhachhang.Interface.OnClickListenerGioHang;
import com.example.appkhachhang.Interface.OnItemClickListenerHang;
import com.example.appkhachhang.Model.ChiTietDienThoai;
import com.example.appkhachhang.Model.ChiTietGioHang;
import com.example.appkhachhang.Model.GioHang;
import com.example.appkhachhang.Model.HangSanXuat;
import com.example.appkhachhang.Model.SanPham;
import com.example.appkhachhang.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GioHangAdapter extends RecyclerView.Adapter<GioHangAdapter.ViewHolder> {
    private Context context;
    private List<ChiTietGioHang> list, listChon;

    private OnClickListenerGioHang itemClickListener;

    public GioHangAdapter(Context context, List<ChiTietGioHang> list, OnClickListenerGioHang listenerGioHang) {
        this.context = context;
        this.list = list;
        this.itemClickListener = listenerGioHang;
        notifyDataSetChanged();
    }



    @NonNull
    @Override
    public GioHangAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_gio_hang, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GioHangAdapter.ViewHolder holder, int position) {
        ChiTietGioHang item = list.get(position);
        holder.tvTenSanPham.setText(item.getMaChiTietDienThoai().getMaDienThoai().getTenDienThoai());
        holder.tvGiaTien.setText(""+item.getMaChiTietDienThoai().getGiaTien());
        holder.tvSoLuong.setText(""+item.getSoLuong());
        String fullCoverImgUrl = item.getMaChiTietDienThoai().getMaDienThoai().getHinhAnh();
        Picasso.get().load(fullCoverImgUrl).into(holder.imgGioHang);

        holder.chkSP.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                itemClickListener.onItemClick(item, b);
            }
        });
        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Bạn có muốn xóa sản phẩm này?");
                builder.setPositiveButton("Đồng ý", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ApiRetrofit.getApiService().deleteGioHang(item.get_id()).enqueue(new Callback<ChiTietGioHang>() {
                            @Override
                            public void onResponse(Call<ChiTietGioHang> call, Response<ChiTietGioHang> response) {
                                list.remove(item);
                                notifyDataSetChanged();
                            }

                            @Override
                            public void onFailure(Call<ChiTietGioHang> call, Throwable t) {

                            }
                        });
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                builder.create().show();
            }
        });


        holder.tvSubtract.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.getSoLuong() > 1){
                    item.setSoLuong(item.getSoLuong()-1);
                    holder.tvSoLuong.setText(""+item.getSoLuong());
                }
            }
        });

        holder.tvAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (item.getSoLuong()< item.getMaChiTietDienThoai().getSoLuong()){
                    item.setSoLuong(item.getSoLuong()+1);
                    holder.tvSoLuong.setText(""+item.getSoLuong());
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tvTenSanPham , tvGiaTien , tvSoLuong, tvAdd, tvSubtract;
        private ImageView imgGioHang, imgDelete;
        private CheckBox chkSP;


        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTenSanPham = itemView.findViewById(R.id.itemGioHang_tvSanPham);
            tvGiaTien = itemView.findViewById(R.id.itemGioHang_tvGiaTien);
            tvSoLuong = itemView.findViewById(R.id.itemGioHang_tvSoLuong);
            imgGioHang = itemView.findViewById(R.id.img_ChiTietGioHang);
            tvAdd = itemView.findViewById(R.id.tv_add);
            tvSubtract = itemView.findViewById(R.id.tv_subtract);
            chkSP = itemView.findViewById(R.id.chk_sp);
            imgDelete = itemView.findViewById(R.id.itemGioHang_imgDelete);
        }
    }

}
